package Controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Client.Network;
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

public class InputController implements Initializable {
    private Network network;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passField;

    @FXML
    private Button authButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginSignUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = new Network();
        //
        authButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String passText = passField.getText().trim();
            if (!loginText.equals("") && !passText.equals("")) {
                try {
                    loginUser(loginText, passText);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else System.out.println("Login and password is empty");
        });
        loginSignUp.setOnAction(event -> {
            openNewScene("/Auth.fxml");
        });
    }

    private void loginUser(String loginText, String passText) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(passText);
        ResultSet result = databaseHandler.getUser(user);
        int count = 0;
        while (result.next()) {
            count++;
        }
        if (count >= 1) {
            openNewScene("/Cloud.fxml");
        }
    }

    public void sendMsg(ActionEvent actionEvent) {
        network.sendMessage();
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

