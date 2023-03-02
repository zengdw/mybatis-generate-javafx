package com.zengdw.mybatis.controller;

import com.zengdw.mybatis.config.Context;
import com.zengdw.mybatis.config.DatabaseConfig;
import com.zengdw.mybatis.utils.StageUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/24 15:36
 */
public class LoginController implements Initializable {
    @FXML
    private Text errorMsg;
    @FXML
    private RadioButton oracle;
    @FXML
    private RadioButton mysql;
    @FXML
    private TextField dataUrl;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        oracle.setToggleGroup(group);
        mysql.setToggleGroup(group);

        errorMsg.setVisible(false);

        dataUrl.setText(DatabaseConfig.instance().getUrl());
        username.setText(DatabaseConfig.instance().getUserName());
        password.setText(DatabaseConfig.instance().getPassword());

        removeClass(dataUrl);
        removeClass(username);
        removeClass(password);
    }

    @FXML
    private void loginAction() {
        if (doLogin()) {
            Stage stage = new Stage();
            Stage loginStage = Context.getStage("login");
            try {
                StageUtil.initStage(stage, "Table Select", "/fxml/tableSelect.fxml", "tableList");
            } catch (IOException e) {
                errorMsg.setText(e.getCause().getMessage());
                errorMsg.setVisible(true);
                loginStage.sizeToScene();
                throw new RuntimeException(e);
            }
            loginStage.close();
        }
    }

    private boolean doLogin() {
        boolean isNotBlank = checkBlank(dataUrl);
        isNotBlank = isNotBlank && checkBlank(username);
        isNotBlank = isNotBlank && checkBlank(password);
        if (!isNotBlank) {
            return false;
        }
        DatabaseConfig.instance()
                .setDataType(oracle.isSelected() ? "Oracle" : "Mysql")
                .setUserName(username.getText())
                .setPassword(password.getText())
                .setUrl(dataUrl.getText());
        return true;
    }

    private boolean checkBlank(TextField textField) {
        if (StringUtils.isBlank(textField.getText())) {
            textField.getStyleClass().add("border-red");
            return false;
        }
        return true;
    }

    private void removeClass(TextField textField) {
        textField.focusedProperty().addListener(var -> textField.getStyleClass().remove("border-red"));
    }

    @FXML
    private void enterPressed(KeyEvent keyEvent) {
        if ("ENTER".equals(keyEvent.getCode().name())) {
            loginAction();
        }
    }
}
