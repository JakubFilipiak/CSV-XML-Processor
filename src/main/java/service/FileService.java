package service;

import domain.DBAccessData;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jakub Filipiak on 23.02.2019.
 */
public class FileService {

    private CSVService csvService = new CSVService();
    private XMLService xmlService = new XMLService();

    public void processFile(File file, DBAccessData dbAccessData) throws IOException {

        boolean csvExtension = file.getName().toLowerCase().endsWith(".csv");
        boolean xmlExtension = file.getName().toLowerCase().endsWith(".xml");

        if (csvExtension) {
            csvService.readCsv(file, dbAccessData);
        } else if (xmlExtension) {
            xmlService.readXml(file, dbAccessData);
        }
    }
}
