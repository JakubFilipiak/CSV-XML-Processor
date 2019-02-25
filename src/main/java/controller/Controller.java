package controller;

import domain.DBAccessData;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import service.DBService;
import service.FileService;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jakub Filipiak on 23.02.2019.
 */
public class Controller {

    private FileService fileService = new FileService();
    private DBService dbService = new DBService();
    private DBAccessData dbAccessData = new DBAccessData();

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField urlTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button connectButton;
    @FXML
    private Button selectFileButton;
    @FXML
    private Button processFileButton;
    @FXML
    private Label statusLabel;

    private File file;

    private void collectDBAccessData() {
        dbAccessData.setUser(usernameTextField.getText());
        dbAccessData.setPassword(passwordTextField.getText());
        dbAccessData.setUrl(urlTextField.getText());
    }

    @FXML
    public void connectToDB() {

        collectDBAccessData();
        boolean connection = dbService.connectToDB(dbAccessData);
        if (connection) {
            statusLabel.setText("Status: Connected! Please select a file to be " +
                    "processed.");
            connectButton.setText("Connected!");
            connectButton.setDisable(true);
            selectFileButton.setDisable(false);
            usernameTextField.setDisable(true);
            passwordTextField.setDisable(true);
            urlTextField.setDisable(true);
        } else {
            statusLabel.setText("Status: Connection failed. Please check the " +
                    "access data and try again.");
        }

    }

    @FXML
    public void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            statusLabel.setText("Status: File chosen! After clicking Process " +
                    "Button, wait for the finished status.");
            processFileButton.setDisable(false);
        }
    }

    @FXML
    public void processFile() throws IOException {

        fileService.processFile(file, dbAccessData);
        statusLabel.setText("Processing finished! You can select another file or " +
                "just close the application." +
                ".");
    }
}
