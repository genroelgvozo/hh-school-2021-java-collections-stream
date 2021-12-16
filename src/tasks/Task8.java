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
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1) // Пропустим один элемент, чтобы не обрабатывать фальшивую персону
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // Использование stream излишне
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  static public String convertPersonToString(Person person) {
    String result = "";
    if (person.getFirstName() != null) { // перепишем if, чтобы избавиться от неверных вариантов
      result = person.getFirstName();
    }
    if (person.getSecondName() != null) {
      if (result.isEmpty())
        result = person.getSecondName();
      else
        result += " " + person.getSecondName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Возспользуемся stream и только что поправленной функцией
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, Task8::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> persons1AsSet = new HashSet<>(persons1); // с HashSet быдстрее
    for (var person: persons2)
      if (persons1AsSet.contains(person))
        return true;
    return false;
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); // Сократили с помощью метода count
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = (new Random()).nextBoolean();
    return codeSmellsGood || reviewerDrunk;
  }
}
