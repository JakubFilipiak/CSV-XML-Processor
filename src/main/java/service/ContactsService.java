package service;

import domain.Contacts;
import domain.DBAccessData;
import domain.Person;
import domain.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jakub Filipiak on 24.02.2019.
 */
public class ContactsService {

    PersonRepository personRepository = new PersonRepository();

    public void sortContacts(Person person, List<String> unsortedContacts, DBAccessData dbAccessData) {

        List<String> emails = new ArrayList<>();
        List<String> phones = new ArrayList<>();
        List<String> jabbers = new ArrayList<>();
        List<String> otherContacts = new ArrayList<>();

        Contacts contacts = new Contacts();

        Pattern emailPattern = Pattern.compile(".+@.+...");
        Matcher emailMatcher;
        Pattern phonePattern = Pattern.compile("[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}");
        Matcher phoneMatcher;
        Pattern jabberPattern = Pattern.compile("jbr:.+");
        Matcher jabberMatcher;

        for (String contact : unsortedContacts) {

            emailMatcher = emailPattern.matcher(contact);
            phoneMatcher = phonePattern.matcher(contact);
            jabberMatcher = jabberPattern.matcher(contact);

            if (emailMatcher.matches()) {
                emails.add(contact);
            } else if (phoneMatcher.matches()) {
                phones.add(contact);
            } else if (jabberMatcher.matches()) {
                jabbers.add(contact);
            } else {
                otherContacts.add(contact);
            }
        }

        person.setContacts(contacts);

        person.getContacts().setPhones(phones);
        person.getContacts().setEmails(emails);
        person.getContacts().setJabbers(jabbers);
        person.getContacts().setOtherContacts(otherContacts);

        personRepository.savePerson(person, dbAccessData);
    }
}
