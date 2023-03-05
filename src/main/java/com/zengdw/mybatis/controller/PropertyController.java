package com.zengdw.mybatis.controller;

import com.zengdw.mybatis.config.Context;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/3/2 14:05
 */
public class PropertyController implements Initializable {
    @FXML
    private TextField projectPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectPath.onActionProperty().addListener((observableValue, h1, h2) -> selectDirectory());
    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("project directory select");
        dc.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) projectPath.getScene().getWindow();
        File file = dc.showDialog(stage);
        projectPath.setText(null != file ? file.getAbsolutePath() : projectPath.getText());
    }

    @FXML
    private void cancel() {
        Context.getStage("tableList").show();
        Context.getStage("property").close();
    }
}
