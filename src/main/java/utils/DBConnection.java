package utils;

import domain.DBAccessData;
import domain.Person;

import java.sql.*;

/**
 * Created by Jakub Filipiak on 24.02.2019.
 */
public class DBConnection {

    private String url;
    private String user;
    private String password;
    private String driver = "org.postgresql.Driver";
    private Connection connection = null;
    private Statement statement = null;

    public boolean establishConnectionAndCreateTables(DBAccessData dbAccessData) {

        this.user = dbAccessData.getUser();
        this.password = dbAccessData.getPassword();
        this.url = dbAccessData.getUrl();

            try {
            Class.forName(driver);
            connection = DriverManager.getConnection("jdbc:postgresql://" + url, user, password);

            if (connection != null) {
                createTables(connection, statement);
                connection.close();
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createTables(Connection connection, Statement statement) throws SQLException {

        String sqlCreateCustomersTable =
                "CREATE TABLE IF NOT EXISTS CUSTOMERS"
                        + "(ID BIGINT not NULL,"
                        + " NAME VARCHAR(255) not NULL,"
                        + " SURNAME VARCHAR(255) not NULL,"
                        + " Age INTEGER)";
        String sqlCreateContactsTable =
                "CREATE TABLE IF NOT EXISTS CONTACTS"
                        + "(ID BIGINT not NULL,"
                        + " ID_CUSTOMER BIGINT not NULL,"
                        + " TYPE INTEGER not NULL,"
                        + " CONTACT VARCHAR(255) not NULL)";

        statement = connection.createStatement();

        statement.executeUpdate(sqlCreateCustomersTable);
        statement.executeUpdate(sqlCreateContactsTable);

        //System.out.println("Tabele utworzone");
    }

    public boolean establishConnection(DBAccessData dbAccessData) {

        this.user = dbAccessData.getUser();
        this.password = dbAccessData.getPassword();
        this.url = dbAccessData.getUrl();

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection("jdbc:postgresql://" + url, user, password);

            if (connection != null) {
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getLastCustomerId() {

        Long lastCustomerId = 0L;
        try {
            statement = connection.createStatement();
            String sqlSelectMaxCustomerId = "SELECT MAX(ID) FROM CUSTOMERS";
            ResultSet resultSet = statement.executeQuery(sqlSelectMaxCustomerId);

            while (resultSet.next()) {
                lastCustomerId = resultSet.getLong("MAX");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastCustomerId;
    }

    public long getLastContactId() {

        Long lastContactId = 0L;
        try {
            statement = connection.createStatement();
            String sqlSelectMaxCustomerId = "SELECT MAX(ID) FROM CONTACTS";
            ResultSet resultSet = statement.executeQuery(sqlSelectMaxCustomerId);

            while (resultSet.next()) {
                lastContactId = resultSet.getLong("MAX");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastContactId;
    }

    public void insertPerson(Person person) {

        try {
            PreparedStatement preparedStatement;
            if (person.getAge() > -1) {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO CUSTOMERS VALUES (?, ?, ?, ?)");
                preparedStatement.setLong(1, person.getId());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getSurname());
                preparedStatement.setInt(4, person.getAge());
            } else {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO CUSTOMERS VALUES (?, ?, ?)");
                preparedStatement.setLong(1, person.getId());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getSurname());
            }
            preparedStatement.executeUpdate();

            long contactId = person.getContacts().getId();

            if (!person.getContacts().getOtherContacts().isEmpty()) {
                for (String otherContact : person.getContacts().getOtherContacts()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO CONTACTS VALUES (?, ?, ?, ?)");
                    preparedStatement.setLong(1, contactId);
                    preparedStatement.setLong(2, person.getId());
                    preparedStatement.setInt(3, person.getContacts().getUnknownContactType());
                    preparedStatement.setString(4, otherContact);
                    preparedStatement.executeUpdate();
                    contactId++;
                }
            }
            if (!person.getContacts().getEmails().isEmpty()) {
                for (String email : person.getContacts().getEmails()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO CONTACTS VALUES (?, ?, ?, ?)");
                    preparedStatement.setLong(1, contactId);
                    preparedStatement.setLong(2, person.getId());
                    preparedStatement.setInt(3, person.getContacts().getEmailContactType());
                    preparedStatement.setString(4, email);
                    preparedStatement.executeUpdate();
                    contactId++;
                }
            }
            if (!person.getContacts().getPhones().isEmpty()) {
                for (String phone : person.getContacts().getPhones()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO CONTACTS VALUES (?, ?, ?, ?)");
                    preparedStatement.setLong(1, contactId);
                    preparedStatement.setLong(2, person.getId());
                    preparedStatement.setInt(3, person.getContacts().getPhoneContactType());
                    preparedStatement.setString(4, phone);
                    preparedStatement.executeUpdate();
                    contactId++;
                }
            }
            if (!person.getContacts().getJabbers().isEmpty()) {
                for (String jabber : person.getContacts().getJabbers()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO CONTACTS VALUES (?, ?, ?, ?)");
                    preparedStatement.setLong(1, contactId);
                    preparedStatement.setLong(2, person.getId());
                    preparedStatement.setInt(3, person.getContacts().getJabberContactType());
                    preparedStatement.setString(4, jabber);
                    preparedStatement.executeUpdate();
                    contactId++;
                }
            }

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
