package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

  // !!! Редактируйте этот метод !!!
  private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                      Collection<Person> persons2,
                                                      int limit) {
    // версия на коллекциях - черновая
                    /*List<Person> listPerson= new ArrayList<Person>(persons1.size() + persons2.size());

                    Iterator<Person> it = persons1.iterator();
                    while(it.hasNext()){listPerson.add(it.next());}
                    it = persons2.iterator();
                    while(it.hasNext()){listPerson.add(it.next());}

                    listPerson.sort(new Comparator<Person>() {
                      @Override
                      public int compare(Person o1, Person o2) {
                        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
                      }
                    });

                    return listPerson.stream().limit(limit).collect(Collectors.toList());*/
    // версия на стримах - основная
    // методом concat объединим коллекции, полученный поток отсортируем, передав методу компаратор по времени создания
    // из полученного после сортировки потока отберём первые limit элементов
    return Stream.concat(persons1.stream(),persons2.stream())
            .sorted(new Comparator<Person>() {
              @Override
              public int compare(Person o1, Person o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
              }
            })
            .limit(limit)
            .collect(Collectors.toList());
  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    Collection<Person> persons1 = Set.of(
        new Person(1, "Person 1", time),
        new Person(2, "Person 2", time.plusSeconds(1))
    );
    Collection<Person> persons2 = Set.of(
        new Person(3, "Person 3", time.minusSeconds(1)),
        new Person(4, "Person 4", time.plusSeconds(2))
    );
    return combineAndSortWithLimit(persons1, persons2, 3).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(List.of(3, 1, 2))
        && combineAndSortWithLimit(persons1, persons2, 5).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(List.of(3, 1, 2, 4));
  }
}
