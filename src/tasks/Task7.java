package tasks;

import common.Company;
import common.Task;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 implements Task {

  private Set<String> vacancyNames(Collection<Company> companies) {
    // черновая версия на коллекциях
                  /*Set<String> vacancyNamesSet = new HashSet<>();

                  Iterator<Company> companyIt = companies.iterator();

                  while(companyIt.hasNext()){
                    Company tmpCompany = companyIt.next();

                    Iterator<Vacancy> vacancyIt = tmpCompany.getVacancies().iterator();

                    while(vacancyIt.hasNext()){
                      vacancyNamesSet.add(vacancyIt.next().getTitle());
                    }
                  }
                  return vacancyNamesSet;*/
    // рабочая версия на потоках
    // раскручиваем множество вакансий компании в цепочку, добавляя в множество, тем самым обеспечивая уникальность
    return companies.stream().flatMap(company -> company.getVacancies().stream()
                                                        .map(vacancy -> vacancy.getTitle())
                                      ).collect(Collectors.toSet());
  }

  @Override
  public boolean check() {
    Vacancy vacancy1 = new Vacancy(1, "vacancy 1");
    Vacancy vacancy2 = new Vacancy(2, "vacancy 2");
    Vacancy vacancy3 = new Vacancy(3, "vacancy 1");
    Company company1 = new Company(1, "company 1", Set.of(vacancy1, vacancy2));
    Company company2 = new Company(2, "company 2", Set.of(vacancy3));
    return vacancyNames(Set.of(company1, company2)).equals(Set.of("vacancy 1", "vacancy 2"));
  }
}
