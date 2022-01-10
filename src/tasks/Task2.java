package tasks;

import common.Person;
import common.Task;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

  // Тут в целом таска найти Stream.concat, избегая лишних коллекций и копирования (хотя там временная память все равно тратися в момент sorted)
  // Асимптотика - O(n log n)
  private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                      Collection<Person> persons2,
                                                      int limit) {

    return Stream.concat(persons1.stream(), persons2.stream())
        .sorted(Comparator.comparing(Person::getCreatedAt))
        // тут один человек заметил хорошую особенность, что Comparator.comparing упадет, если поле null
        // Вообще редко когда приходится сортировать в памяти, обычно все это на базе. Иногда приходится
        // но часто мы сортируем по полям, которые в силу природы не могут быть null
        // Но замечание хорошее, и если надо вам сортировать с null-ами то Comparator.nullsLast(Comparator.comparing(Person::getCreatedAt)) или nullFirst, смотря как надо
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
