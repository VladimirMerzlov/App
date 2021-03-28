package sample.controllers;

import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

            String pass = md5String(pass_reg.getCharacters().toString());

            try {
                boolean isReg = db.regUser(login_reg.getCharacters().toString(), email_reg.getCharacters().toString(), pass);
                if (isReg) {
                    login_reg.setText("");
                    email_reg.setText("");
                    pass_reg.setText("");
                    btn_reg.setText("Готово");
                } else {
                    btn_reg.setText("Введите новый логин");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        });


        btn_auth.setOnAction(event -> {

            login_auth.setStyle("-fx-border-color: #fafafa");
            pass_auth.setStyle("-fx-border-color: #fafafa");

            if (login_auth.getCharacters().length() <= 3) {
                login_auth.setStyle("-fx-border-color: red");
                return;
            } else if (pass_auth.getCharacters().length() <= 7) {
                pass_auth.setStyle("-fx-border-color: red");
                return;
            }

            String pass = md5String(pass_auth.getCharacters().toString());

            try {
                boolean isAuth = db.authUser(login_auth.getCharacters().toString(), pass);
                if (isAuth) {
                    login_auth.setText("");
                    pass_auth.setText("");
                    btn_auth.setText("Готово");
                } else {
                    btn_auth.setText("Не найден");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        });
    }

    public static String md5String(String pass) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;

        }
        return md5Hex;
    }
}
