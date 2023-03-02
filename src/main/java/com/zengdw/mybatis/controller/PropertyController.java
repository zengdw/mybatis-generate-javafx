package com.zengdw.mybatis.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button selectDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("source directory select");
        dc.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) selectDirectory.getScene().getWindow();
        dc.showDialog(stage);
    }
}
