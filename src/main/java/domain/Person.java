package domain;

import lombok.Data;

/**
 * Created by Jakub Filipiak on 23.02.2019.
 */
@Data
public class Person {

    private long id;
    private String name;
    private String surname;
    private int age = -1;

    private Contacts contacts;
}
