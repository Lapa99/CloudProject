package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class MainController {

    @FXML
    private TextArea textAreaClient;

    @FXML
    private TextField commandLine;

    @FXML
    private TextArea textAreaCloud;

    @FXML
    private Button sendButton;

    @FXML
    void sendMsg(ActionEvent event) {

    }

    public void sendMsg(javafx.event.ActionEvent actionEvent) {
    }
}