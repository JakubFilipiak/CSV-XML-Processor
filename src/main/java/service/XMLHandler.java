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

    private final String PERSONS_TAG = "persons";
    private final String PERSON_TAG = "person";
    private final String NAME_TAG = "name";
    private final String SURNAME_TAG = "surname";
    private final String AGE_TAG = "age";
    private final String CITY_TAG = "city";
    private final String CONTACTS_TAG = "contacts";
    private final String EMAIL_TAG = "email";
    private final String PHONE_TAG = "phone";
    private final String JABBER_TAG = "jabber";

    DBAccessData dbAccessData;

    public XMLHandler(DBAccessData dbAccessData) {
        this.dbAccessData = dbAccessData;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (qName.equalsIgnoreCase(PERSON_TAG)) {
            person = new Person();
        }
        if (qName.equalsIgnoreCase(CONTACTS_TAG)) {
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
            case NAME_TAG:
                person.setName(tmpValue);
                break;
            case SURNAME_TAG:
                person.setSurname(tmpValue);
                break;
            case AGE_TAG:
                person.setAge(Integer.parseInt(tmpValue));
                break;
            case CITY_TAG:

                break;
            case CONTACTS_TAG:
                person.setContacts(contacts);
                break;
            case PHONE_TAG:
                phones.add(tmpValue);
                break;
            case EMAIL_TAG:
                emails.add(tmpValue);
                break;
            case JABBER_TAG:
                jabbers.add(tmpValue);
                break;
            case PERSON_TAG:
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
            case PERSONS_TAG:

                break;
            default:
                otherContacts.add(tmpValue);
                break;
        }
    }
}
