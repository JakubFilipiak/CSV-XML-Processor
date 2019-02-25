package domain;

import lombok.Data;

import java.util.List;

/**
 * Created by Jakub Filipiak on 23.02.2019.
 */
@Data
public class Contacts {

    private long id;
    private List<String > emails;
    private List<String> phones;
    private List<String > jabbers;
    private List<String> otherContacts;

    private final int unknownContactType = 0;
    private final int emailContactType = 1;
    private final int phoneContactType = 2;
    private final int jabberContactType = 3;
}
