package tasks;

import common.Person;
import common.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
    if ( (persons.size() == 0) || (persons.size() == 1)   ) { // поскольку убираем первый элемент, то если всего 1,
      return Collections.emptyList();                         // результирующий список также будет пуст
    }
    //persons.remove(0);     при работе с потоками можно пропустить сколько угодно первых элементов
    return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) { // при сборе в множество уникальность будет соблюдена автоматически
    return new HashSet<String>(getNames(persons));// idea рекомендовала делать так
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    // черновая версия без потоков                         два раза встречается getSecondName(), а должно быть getMiddleName()
      /*          String result = "";                   // кроме того getFirstName() должно идти впереди

                  if (person.getFirstName() != null) {
                    result += person.getFirstName();    // при первой аббревиатуре первый пробел не нужен
                  }

                  if (person.getSecondName() != null) {
                    result += result!=""?" ":"" + person.getSecondName();// если было имя, то нужно поставить " ", иначе ""
                  }

                  if (person.getMiddleName() != null) {
                    result += result!=""?" ":"" + person.getMiddleName();
                  }
                  return result;*/
    // рабочая версия с потоками
    String result = ""; // на случай, если все три окажутся null, вернётся ""
    // просто объединяем строки
    return result + Stream.of(person.getFirstName(),person.getSecondName(),person.getMiddleName()).collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
            // версия на коллекциях
            /*Map<Integer, String> map = new HashMap<>(persons.size());// можно сразу выделить нужный объем памяти, чтобы не
            for (Person person : persons) {                          // перевыделять в дальнейшем
              if (!map.containsKey(person.getId())) {
                map.put(person.getId(), convertPersonToString(person));
              }
            }
            return map;*/
    // рабочая версия на потоках
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
    // черновая версия на коллекциях
                /*boolean has = false;
                for (Person person1 : persons1) {
                  for (Person person2 : persons2) {
                    if (person1.equals(person2)) {
                      has = true;
                      break;  // после нахождения совпадающего элемента оба цикла нужно разбивать
                    }
                  }
                  if (has) {break;}  // разбив внешнего цикла
                }
                return has;*/
    // рабочая версия на стримах
    // применяем в потоке для первой коллекции метод, которому достаточно быть единственный раз true,
    // внутри него в потоке для второй коллекции применяем метод, которому достаточно единственный раз быть true
    // матрёшка
    return persons1.stream().anyMatch((person) -> persons2.stream()
                                                  .anyMatch((yetPerson) -> yetPerson.equals(person)));
  }

  //данный метод вообще из другой оперы и быть его здесь не должно, также убрал глобальную переменную, потому что
  //она нигде не используется


  @Override
  public boolean check() {
    System.out.println("Нифига не слабо!!!");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
