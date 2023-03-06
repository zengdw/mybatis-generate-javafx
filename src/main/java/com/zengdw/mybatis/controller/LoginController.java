package com.zengdw.mybatis.controller;

import com.zengdw.mybatis.config.Context;
import com.zengdw.mybatis.config.DatabaseTypeEnum;
import com.zengdw.mybatis.config.GeneratorProperties;
import com.zengdw.mybatis.utils.StageUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private VBox vbox;
    @FXML
    private HBox databaseHbox;
    @FXML
    private TextField database;
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
        vbox.getChildren().remove(databaseHbox);

        ToggleGroup group = new ToggleGroup();
        oracle.setToggleGroup(group);
        mysql.setToggleGroup(group);

        group.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
            RadioButton radioButton = (RadioButton) newValue;
            String text = radioButton.getText();
            if ("Mysql".equals(text)) {
                vbox.getChildren().add(2, databaseHbox);
                database.getStyleClass().remove("border-red");
            } else {
                vbox.getChildren().remove(databaseHbox);
            }
        });

        dataUrl.setText(GeneratorProperties.of().getUrl());
        username.setText(GeneratorProperties.of().getUserName());
        password.setText(GeneratorProperties.of().getPassword());

        removeClass(dataUrl);
        removeClass(username);
        removeClass(password);
        removeClass(database);
    }

    @FXML
    private void loginAction() throws IOException {
        if (doLogin()) {
            StageUtil.initStage(new Stage(), "Table Select", "/fxml/tableSelect.fxml", "tableList");
            Context.getStage("login").close();
        }
    }

    private boolean doLogin() {
        boolean isNotBlank = checkBlank(dataUrl);
        isNotBlank = isNotBlank && checkBlank(username);
        isNotBlank = isNotBlank && checkBlank(password);
        if (mysql.isSelected()) {
            isNotBlank = isNotBlank && checkBlank(database);
        }
        if (!isNotBlank) {
            return false;
        }
        GeneratorProperties.of()
                .setDataType(oracle.isSelected() ? DatabaseTypeEnum.Oracle : DatabaseTypeEnum.Mysql)
                .setDatabase(database.getText())
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
    private void enterPressed(KeyEvent keyEvent) throws IOException {
        if ("ENTER".equals(keyEvent.getCode().name())) {
            loginAction();
        }
    }
}
