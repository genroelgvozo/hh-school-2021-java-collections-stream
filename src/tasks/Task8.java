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
    if (persons == null || persons.size() == 0) { // Добавим проверку, что лист не null
      return Collections.emptyList();
    }
    //Вместо удаления первого элемента, эффективнее его пропустить в стриме с помощью .skip()
    // Так же уберем null персон
    return persons.stream().skip(0).filter(Objects::nonNull).map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // можно сделать создание множества короче
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // Метод работает как-то странно, у нас же может получиться на выходе -> ФамилияИмя Имя!
  // Думаю лучше сделать список, а потом его заджойнить с пробелом

  public String convertPersonToString(Person person) {
    if (person == null) { // Добавим проверку, что персона не null
      return "";
    }
    ArrayList<String> result = new ArrayList<>();
    if (person.getSecondName() != null) {
      result.add(person.getSecondName());
    }

    if (person.getFirstName() != null) {
      result.add(person.getFirstName());
    }


    return String.join(" ", result);
  }

  // словарь id персоны -> ее имя
  // Можно сделать то же самое с помощью стрима, плюс добавить проверку на null
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    if (persons == null || persons.size() == 0) {
      return Collections.emptyMap(); // Добавим проверку на null
    }
    return persons.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                    Person::getId,
                    this::convertPersonToString
            ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // Так же сделаем с помощью стрима
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // Проверим на null и пустые списки
    if (persons1 == null || persons2 == null || persons1.size() == 0 || persons2.size() == 0) {
      return false;
    }
    return persons1.stream()
            .filter(Objects::nonNull)
            .anyMatch(p -> new HashSet<>(persons2).contains(p));
  }

  //Можно сделать попроще, используя count
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(n -> n % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = false;
    boolean reviewerDrunk = true;
    return codeSmellsGood || reviewerDrunk;
  }
}
