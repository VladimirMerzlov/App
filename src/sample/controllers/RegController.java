package sample.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.DB;

public class RegController {

    @FXML
    public ResourceBundle resources;

    @FXML
    public URL location;

    @FXML
    public TextField login_reg;

    @FXML
    public TextField email_reg;

    @FXML
    public PasswordField pass_reg;

    @FXML
    public CheckBox confidentials;

    @FXML
    public Button btn_reg;

    @FXML
    public TextField login_auth;

    @FXML
    public PasswordField pass_auth;

    @FXML
    public Button btn_auth;

    private DB db = new DB();

    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> {

            login_reg.setStyle("-fx-border-color: #fafafa");
            email_reg.setStyle("-fx-border-color: #fafafa");
            pass_reg.setStyle("-fx-border-color: #fafafa");
            btn_reg.setText("Зарегестрироваться");

            if (login_reg.getCharacters().length() <= 3) {
                login_reg.setStyle("-fx-border-color: red");
                return;
            } else if (email_reg.getCharacters().length() <= 5) {
                email_reg.setStyle("-fx-border-color: red");
                return;
            } else if (pass_reg.getCharacters().length() <= 7) {
                pass_reg.setStyle("-fx-border-color: red");
                return;
            } else if (!confidentials.isSelected()) {
                btn_reg.setText("Поставьте галочку");
                return;

            }

            try {
                boolean isAuth = db.regUser(login_reg.getCharacters().toString(), email_reg.getCharacters().toString(), pass_reg.getCharacters().toString());
                if (isAuth) {
                    login_reg.setText("");
                    email_reg.setText("");
                    pass_reg.setText("");
                    btn_reg.setText("Готово");
                }else{
                    btn_reg.setText("Введите новый логин");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}
