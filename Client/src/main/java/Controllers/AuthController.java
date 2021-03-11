package Controllers;

import Client.User;
import Database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private PasswordField passField;

    @FXML
    private TextField lastName;

    @FXML
    private Button backButton;

    @FXML
    private TextField firstName;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginSignUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSignUp.setOnAction(event -> {
            initNewUser();
            openNewScene("/Input.fxml");
        });
    }

    public void initNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        try {
            String firstname = firstName.getText();
            String lastname = lastName.getText();
            String username = loginField.getText();
            String password = passField.getText();
            User user = new User(firstname, lastname, username, password);
            dbHandler.init(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) {
        backButton.setOnAction(event -> {
            openNewScene("/Input.fxml");
        });
    }
    public void openNewScene(String window) {
        loginSignUp.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.showAndWait();
    }
}
