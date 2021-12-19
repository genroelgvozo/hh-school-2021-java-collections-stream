package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

  private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                      Collection<Person> persons2,
                                                      int limit) {
    List<Person> sortedPersons1 = persons1.stream()
            .sorted(Comparator.comparing(Person::getCreatedAt))
            .limit(limit)
            .collect(Collectors.toUnmodifiableList());
    List<Person> sortedPersons2 = persons2.stream()
            .sorted(Comparator.comparing(Person::getCreatedAt))
            .limit(limit)
            .collect(Collectors.toUnmodifiableList());

    ArrayList<Person> result = new ArrayList<Person>(limit);
    int i = 0;
    int j = 0;

    while (i < sortedPersons1.size() && j < sortedPersons2.size() && result.size() < limit){
      result.add(sortedPersons1.get(i).getCreatedAt().compareTo(sortedPersons2.get(j).getCreatedAt()) <= 0 ?
              sortedPersons1.get(i++) :  sortedPersons2.get(j++));
    }

    while (i < sortedPersons1.size() && result.size() < limit){
      result.add(sortedPersons1.get(i++));
    }

    while (j < sortedPersons2.size() && result.size() < limit){
      result.add(sortedPersons2.get(j++));
    }

    return result;
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
