package com.tanveer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField userName;
    @FXML
    private TextField password;

    public boolean proccessLogin() throws Exception{
        return AccessDatabase.getInstance().login(userName.getText(),password.getText());
    }
}
