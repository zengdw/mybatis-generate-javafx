package com.zengdw.mybatis.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * loading(加载)组件
 *
 * @author zengd
 * @date 2023/3/5 2:58
 */
public class LoadingCom {
    private VBox vBox;
    private Pane parent;

    private LoadingCom() {

    }

    public static LoadingCom show(Pane parent, String loadingMsg, double w, double h) {
        LoadingCom loading = new LoadingCom();
        loading.init(parent, loadingMsg, w, h);
        return loading;
    }

    private void init(Pane parent, String loadingMsg, double w, double h) {
        this.parent = parent;
        Label adLbl = new Label(loadingMsg);
        adLbl.setTextFill(Color.GREEN);

        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setStyle("-fx-progress-color: green");

        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(indicator, adLbl);
        vBox.setPrefWidth(w);
        vBox.setPrefHeight(h);
        vBox.setStyle("-fx-background-color: rgba(239,233,233,0.8)");

        parent.getChildren().add(vBox);
    }

    public void close() {
        parent.getChildren().remove(vBox);
    }
}
