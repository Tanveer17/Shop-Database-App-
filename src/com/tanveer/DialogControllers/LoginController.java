package com.tanveer.DialogControllers;

import com.tanveer.AccessDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    public boolean proccessLogin() throws Exception{
        return AccessDatabase.getInstance().login(userName.getText(),password.getText());
    }
}
