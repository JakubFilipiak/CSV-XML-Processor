package service;

import domain.Contacts;
import domain.DBAccessData;
import domain.Person;
import domain.repository.PersonRepository;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 24.02.2019.
 */
public class XMLHandler extends DefaultHandler {

    PersonRepository personRepository = new PersonRepository();

    private String tmpValue;

    private Person person;
    private Contacts contacts;
    private List<String> emails = new ArrayList<>();
    private List<String> phones = new ArrayList<>();
    private List<String> jabbers = new ArrayList<>();
    private List<String> otherContacts = new ArrayList<>();

    private final String personsTag = "persons";
    private final String personTag = "person";
    private final String nameTag = "name";
    private final String surnameTag = "surname";
    private final String ageTag = "age";
    private final String cityTag = "city";
    private final String contactsTag = "contacts";
    private final String emailTag = "email";
    private final String phoneTag = "phone";
    private final String jabberTag = "jabber";

    DBAccessData dbAccessData;

    public XMLHandler(DBAccessData dbAccessData) {
        this.dbAccessData = dbAccessData;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (qName.equalsIgnoreCase(personTag)) {
            person = new Person();
        }
        if (qName.equalsIgnoreCase(contactsTag)) {
            contacts = new Contacts();
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {

        tmpValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        switch (qName) {
            case nameTag:
                person.setName(tmpValue);
                break;
            case surnameTag:
                person.setSurname(tmpValue);
                break;
            case ageTag:
                person.setAge(Integer.parseInt(tmpValue));
                break;
            case cityTag:

                break;
            case contactsTag:
                person.setContacts(contacts);
                break;
            case phoneTag:
                phones.add(tmpValue);
                break;
            case emailTag:
                emails.add(tmpValue);
                break;
            case jabberTag:
                jabbers.add(tmpValue);
                break;
            case personTag:
                person.getContacts().setPhones(phones);
                person.getContacts().setEmails(emails);
                person.getContacts().setJabbers(jabbers);
                person.getContacts().setOtherContacts(otherContacts);

                personRepository.savePerson(person, dbAccessData);

                phones.clear();
                emails.clear();
                jabbers.clear();
                otherContacts.clear();
                break;
            case personsTag:

                break;
            default:
                otherContacts.add(tmpValue);
                break;
        }
    }
}
