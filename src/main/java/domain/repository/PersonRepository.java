package domain.repository;

import domain.DBAccessData;
import domain.Person;
import service.DBService;

/**
 * Created by Jakub Filipiak on 24.02.2019.
 */
public class PersonRepository {

    DBService dbService = new DBService();

    public void savePerson(Person person, DBAccessData dbAccessData) {
        dbService.insertPerson(person, dbAccessData);
    }
}
