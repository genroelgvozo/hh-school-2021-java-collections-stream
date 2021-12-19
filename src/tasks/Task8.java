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

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public static List<String> getNames(List<Person> persons) {
    return persons.stream()
            .skip(1)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public static Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public static String convertPersonToString(Person person) {
    StringBuffer result = new StringBuffer();
    if (person.getSecondName() != null) {
      result.append(person.getSecondName());
    }

    if (person.getFirstName() != null) {
      result.append(" ");
      result.append(person.getFirstName());
    }

    if (person.getMiddleName() != null) {
      result.append(" ");
      result.append(person.getMiddleName());
    }
    return result.toString();
  }

  // словарь id персоны -> ее имя
  public static Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(Person::getId, Task8::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public static boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> uniquePersons1 = new HashSet<>(persons1);
    Set<Person> uniquePersons2 = new HashSet<>(persons2);
    if(uniquePersons1.size() < uniquePersons2.size()) {
      for(Person person : uniquePersons1){
        if(uniquePersons2.contains(person)){
          return true;
        }
      }
    } else {
      for(Person person : uniquePersons2){
        if(uniquePersons1.contains(person)){
          return true;
        }
      }
    }
    return false;
  }

  //...
  public static long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false; // может быть
    return codeSmellsGood || reviewerDrunk;
  }
}
