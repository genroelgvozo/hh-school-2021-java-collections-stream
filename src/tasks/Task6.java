package tasks;

import common.Area;
import common.Person;
import common.Task;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
    Map<Integer, Area> areaMap = areas.stream()
        .collect(Collectors.toMap(Area::getId, Function.identity()));
    // Тут многие делали areaNameMap - это ок, если сразу в стриме ниже складывать для краткости
    // Но если хочется формирование строки вынести в метод (а это правильно, оно может переиспользоваться, да и менять его тогда более очевидно где, это своебразный конвертер в view представление
    // то туда надо передавать только Person и Area. Пусть метод сам решает что ему брать (может ФИО захочет, а у Арейки какое-нибудь краткое название или еще что)
    return persons.stream()
        .flatMap(person -> personAreaIds.getOrDefault(person.getId(), Collections.emptySet()).stream()
            // вроде на это не обращал внимание, но достаточно важно сделать default
            // так как связи с арейками могут быть не у всех персон, и тогда мы просто упадем
            .map(areaMap::get)
            .map(area -> convertToDescription(person, area))
        )
        .collect(Collectors.toSet());
  }

  public static String convertToDescription(Person person, Area area) {
    return String.format("%s - %s", person.getFirstName(), area.getName());
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
