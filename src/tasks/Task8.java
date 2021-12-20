package tasks;

import common.Person;
import common.Task;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    //Использую skip()
    return persons.stream()
    .map(Person::getFirstName)
    .skip(1)
    .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    //Удалил .distinct(), set не может иметь повторений
    return getNames(persons).stream()
    .collect(Collectors.toSet());
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {

    //Удалил один из SecondName, был явно лишний

    String result = "";

    if (person.getFirstName() != null) {
      result += person.getFirstName();
    }

    if (person.getSecondName() != null) {
      result += " " + person.getSecondName();
    }
       
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Воспользуемся стримом
    return persons.stream()
    .collect(Collectors.toMap(Person::getId, x -> convertPersonToString(x)));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
   // Наверное можно проще
    return persons1.equals(persons2);
   // Эту функцию вообще нужно удалить...
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    //Считаем четные числа без дополнительной переменной с помощью count()
    return numbers
    .filter(num -> num % 2 == 0)
    .count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true; //Нужно что то одно поменять...
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
