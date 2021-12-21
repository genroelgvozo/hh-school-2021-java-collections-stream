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
    if ( persons.size() == 0 ) {
      return Collections.emptyList();
    }
    //persons.remove(0); поскольку всё равно вскрывать поток, в потоке и вырежем первый элемент,
    // в потоке его вырезать быстрее, поэтому эту строчку удалим, кроме того она внимание отвлекает
    // и делает программу менее ясной
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) { // при сборе в множество уникальность будет соблюдена автоматически
    return new HashSet<String>(getNames(persons));// idea рекомендовала делать так
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getFirstName(),person.getSecondName(),person.getMiddleName())
                 .filter(Objects::nonNull)
                 .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // собираем исходную коллекцию в требуемый map, не забывая указать, что дубликатов быть не должно
    // поскольку на это был акцент в исходном методе
    return persons.stream().collect(Collectors.toMap(
                                                      Person::getId,
                                                      person -> convertPersonToString(person),
                                                      (existingValue,newValue) -> existingValue
                                                    ));

  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // чтобы ускорить работу метода до O(N) нужно использовать множества, потому что время поиска ну них O(1)
    // например так
    Set<Person> personSet = new HashSet<>(persons2);
    return persons1.stream().anyMatch(personSet::contains);
  }

  //...
  public long countEven(Stream<Integer> numbers) {// если значение у функции возвращается, нет смысла для него
    // использовать свойство класса, это по смыслу разные значения
    // forEach(...) нагляднее заменить на .count()
    return numbers.filter(num -> num % 2 == 0).count();
  }


  @Override
  public boolean check() {
    System.out.println("Нифига не слабо!!!");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
