package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    return persons.stream().map(Person::getFirstName).skip(1).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    //По мне так всё-таки очевидней
    return Optional.ofNullable(person.getSecondName()).orElse("")
        + Optional.ofNullable(person.getFirstName()).orElse("")
        + Optional.ofNullable(person.getMiddleName()).orElse("");
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(Person::getId, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains);
  }

  //Получить четные числа
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    List<Person> persons = List.of(
        new Person(1, "Person 1", time),
        new Person(2, "Person 2", time.plusSeconds(1)),
        new Person(3, "Person 3", time.minusSeconds(2)),
        new Person(4, "Person 4", time.plusSeconds(3)),
        new Person(5, "Person 2", time.plusSeconds(4))
    );

    List<Person> persons1 = List.of(new Person(4, "Person 4", time.plusSeconds(3)));
    List<String> names = List.of("Person 2", "Person 3", "Person 4", "Person 2");
    Set<String> differentNames = Set.of("Person 2", "Person 3", "Person 4");
    Map<Integer, String> personNames = Map.of(4, "Person 4");

    boolean codeSmellsGood = hasSamePersons(persons, persons1)
        && getNames(persons).equals(names)
        && getDifferentNames(persons).equals(differentNames)
        && convertPersonToString(persons.get(0)).equals("Person 1")
        && getPersonNames(persons1).equals(personNames)
        && countEven(Stream.of(1, 5, 10, 20)) == (long) 2;

    boolean reviewerDrunk = !getNames(Collections.emptyList()).equals(Collections.emptyList());

    return !reviewerDrunk && codeSmellsGood;
  }
}
