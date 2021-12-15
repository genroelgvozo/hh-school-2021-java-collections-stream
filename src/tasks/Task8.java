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

/*  Комментарии

1 - Метод getNames: применяем StreamAPI метод skip
2 - Метод getDifferentNames: distinct лишний, так как идея пишет, что Set итак содержит уникальное множество
    + можно вызвать через конструктор HashSet
3 - Метод convertPersonToString: 2 раза вызывается getSecondName вместо getMiddleName
    + можно переписать с помощью метода Objects.toString, одним из параметров которого является значение по умолчанию
4 - Метод getPersonNames: применяем StreamAPI
5 - Метод hasSamePersons: применяем Collections.disjoint, который возвратит тру если нет пересечений множеств
6 - Метод countEven: вместо метода forEach следует использовать метод count
7 - Переменная count Класса Task8  не нужна
8 - метод check() всегда завалит выполнение 8 задания, так как в нем - рецензент всегда трезв а код пахнет дурно
*/

public class Task8 implements Task {

    //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
    public List<String> getNames(List<Person> persons) {
        return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
    }

    //ну и различные имена тоже хочется
    public Set<String> getDifferentNames(List<Person> persons) {
        return new HashSet<>(getNames(persons));
    }

    //Для фронтов выдадим полное имя, а то сами не могут
    public String convertPersonToString(Person person) {
        return Objects.toString(person.getSecondName(), "")
                + Objects.toString(person.getFirstName(), "")
                + Objects.toString(person.getMiddleName(), "");
    }

    // словарь id персоны -> ее имя
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        return persons.stream().collect(Collectors.toMap(Person::getId, this::convertPersonToString));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        return !Collections.disjoint(persons1, persons2);
    }

    //...
    public long countEven(Stream<Integer> numbers) {
        return numbers.filter(num -> num % 2 == 0).count();
    }

    @Override
    public boolean check() {
        System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
        boolean codeSmellsGood = false;
        boolean reviewerDrunk = false;
        return true;
    }
}
