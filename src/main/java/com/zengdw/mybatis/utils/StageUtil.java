package com.zengdw.mybatis.utils;

import com.zengdw.mybatis.config.Context;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/3/1 16:43
 */
public class StageUtil {
    public static void initStage(Stage stage, String title, String fxmlUrl, String stageName) throws IOException {
        FXMLLoader loader = new FXMLLoader(StageUtil.class.getResource(fxmlUrl));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        Image image = new Image(StageUtil.class.getResourceAsStream("/images/mybatis.png"));
        stage.getIcons().add(image);
        stage.show();
        Context.setStage(stageName, stage);
    }
}
