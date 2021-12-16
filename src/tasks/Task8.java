package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
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
  public List<String> getNamesExcludingFirst(List<Person> persons) {

    //Не портим исходную коллекцию. Даем более понятное имя методу.
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .toList();
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {

    //Set и так не содержит повторов
    return new HashSet<>(getNamesExcludingFirst(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String getPersonFullName(Person person) {

    //Даем более понятное имя методу. Был баг с лишним пробелом.
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNamesMap(Collection<Person> persons) {

    //Даем более понятное имя методу. Фильтруем возможные повторы объектов.
    return persons.stream()
            .distinct()
            .collect(Collectors.toMap(Person::getId, this::getPersonFullName));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasCommonElements(Collection<Person> persons1, Collection<Person> persons2) {

    //Даем более понятное имя методу.
    //Избавляемся от О(n^2) по сложности, тратя линейно память
    //Напрашивающийся Collections.disjoint() - по сути то же самае, что и было (цикл в цикле).
    Set<Person> personSet = new HashSet<>(persons1);
    return persons2.stream().anyMatch(personSet::contains);
  }

  //...
  public long countEven(Stream<Integer> numbers) {

    //Заменили на стрим
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true; //Чуточку, да лучше, чем было =D
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
