package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {
    // черновой вариант на коллекциях
                     /*Set<String> personDescriptions = new HashSet<String>();

                    Iterator<Person> personIt = persons.iterator();

                    while(personIt.hasNext()) {
                      Person tmpPerson = personIt.next();

                      Iterator<Integer> regionSet = personAreaIds.get(tmpPerson.getId()).iterator();

                      while(regionSet.hasNext()){// перебор множества регионов
                        Integer tmpRegion = regionSet.next();

                        Iterator<Area> areaIt = areas.iterator();
                        while(areaIt.hasNext()){ // поиск названия региона
                          Area areaTmp = areaIt.next();

                          if(areaTmp.getId() == tmpRegion) {
                            personDescriptions.add(tmpPerson.getFirstName() + " - " + areaTmp.getName() );
                            break;
                          }
                        }
                      }
                    }
                    return personDescriptions;*/
    // рабочий вариант на потоках
    // преобразуем коллекцию регионов в map, чтобы можно было по id региона получить его имя
    Map<Integer,String> regionalMap = areas.stream().collect(Collectors.toMap(Area::getId,Area::getName));
    // раскрутим каждое множество регионов в цепочку, сопоставив имени региона имя персоны
    // результат преобразуем в требуемое множество
    return persons.stream().flatMap(person -> personAreaIds.get(person.getId()).stream()
                                              .map(id -> person.getFirstName() + " - "+ regionalMap.get(id))
                                    ).collect(Collectors.toSet());
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
