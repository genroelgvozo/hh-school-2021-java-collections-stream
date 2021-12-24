package tasks;

import common.Person;
import common.Task;

import java.sql.ClientInfoStatus;
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
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    //Если и делать изменения, то лучше в копии
    List<Person> copyPersons = persons.stream().collect(Collectors.toList());
    copyPersons.remove(0);
    return copyPersons.stream().map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    //в сет уже хранятся только уникальные значения, следовательно, дубликатов не будет
    return getNames(persons).stream().collect(Collectors.toSet());
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    String result = "";
    //если и проверять на пустоту, то лучше функцией isEmpty, так как может попасть и пустая строка
    if (person.getSecondName().isEmpty()) {
      result += person.getSecondName();
    }

    if (person.getFirstName().isEmpty()) {
      result += " " + person.getFirstName();
    }

    if (person.getSecondName().isEmpty()) {
      result += " " + person.getSecondName();
    }
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(1);
    persons.forEach( (person -> {
              map.put(person.getId(), convertPersonToString(person));
            })
    );
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    //проверять каждый с каждым долго, проще посчитать
    // количество до и после. добавим все записи в set, а так как
    // там дублирование записей отсутствует, то будут только уникальные
    Set<Person> allpersons =new HashSet<>();
    allpersons.addAll(persons1);
    allpersons.addAll(persons2);
    //если уникальных записей меньше суммы размеров изначальных списков, то есть дубли
    return persons1.size()+persons2.size() > allpersons.size();
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).collect(Collectors.toList()).size();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = false;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
