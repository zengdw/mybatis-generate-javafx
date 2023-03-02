package com.zengdw.mybatis;

import com.zengdw.mybatis.utils.StageUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/24 15:28
 */
public class MybatisApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        StageUtil.initStage(primaryStage, "", "/fxml/login.fxml", "login");
    }
}
