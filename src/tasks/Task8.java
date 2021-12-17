package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.function.Function;
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
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    // persons.remove(0); не круто удалять - круто пропускать
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    // distinct - явно лишний, множество и так хранит неповторяющиеся значения
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
    // String result = "";
    // if (person.getSecondName() != null) {
    //   result += person.getSecondName();
    // }

    // if (person.getFirstName() != null) {
    //   result += " " + person.getFirstName();
    // }

    // if (person.getSecondName() != null) {
    //   result += " " + person.getSecondName();
    // } если второй раз то же самое написать - все равно будет null
    // return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(1);
    for (Person person : persons) {
      if (!map.containsKey(person.getId())) {
        map.put(person.getId(), convertPersonToString(person));
      }
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Map<Integer, Person> mp2 = persons2.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
    for (Person person1 : persons1) {
      if (mp2.containsKey(person1.getId()) && person1.equals(mp2.get(person1.getId()))) {
        return true;
      }
    }
    return false;
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = true;
    return true;
  }
}
