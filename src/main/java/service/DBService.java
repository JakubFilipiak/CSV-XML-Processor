package service;

import domain.DBAccessData;
import domain.Person;
import utils.DBConnection;

/**
 * Created by Jakub Filipiak on 24.02.2019.
 */
public class DBService {

    private DBConnection dbConnection = new DBConnection();

    public boolean connectToDB(DBAccessData dbAccessData) {
        return dbConnection.establishConnectionAndCreateTables(dbAccessData);
    }

    public void insertPerson(Person person, DBAccessData dbAccessData) {

        dbConnection.establishConnection(dbAccessData);

        long lastCustomerId = dbConnection.getLastCustomerId();
        long lastContactId = dbConnection.getLastContactId();

        person.setId(lastCustomerId + 1);
        person.getContacts().setId(lastContactId + 1);

        dbConnection.insertPerson(person);
        dbConnection.closeConnection();
    }
}
