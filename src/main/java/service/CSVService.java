package service;

import domain.DBAccessData;
import domain.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 23.02.2019.
 */
public class CSVService {

    ContactsService contactsService = new ContactsService();

    public void readCsv(File csvFile, DBAccessData dbAccessData) throws IOException {

        Person person;
        List<String> unsortedContacts = new ArrayList<>();

        InputStream bufferedInputStream =
                new BufferedInputStream(new FileInputStream(csvFile));
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(bufferedInputStream));

        String line;

        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {

            person = new Person();
            unsortedContacts.clear();

            String fields[] = line.split(",");
            int i = 0;

            for (String field : fields) {

                switch (i) {
                    case 0:
                        person.setName(field);
                        break;
                    case 1:
                        person.setSurname(field);
                        break;
                    case 2:
                        if (!field.isEmpty()) {
                            person.setAge(Integer.parseInt(field));
                        }
                        break;
                    case 3:

                        break;
                    default:
                        unsortedContacts.add(field);
                        break;
                }
                i++;
            }
            contactsService.sortContacts(person, unsortedContacts, dbAccessData);
        }
    }
}
