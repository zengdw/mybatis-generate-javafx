package com.zengdw.mybatis.config;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/3/1 11:14
 */
public class Context {
    private final static Map<String, Stage> STAGE_MAP = new HashMap<>();

    public static void setStage(String name, Stage stage) {
        STAGE_MAP.put(name, stage);
    }

    public static Stage getStage(String name) {
        return STAGE_MAP.get(name);
    }
}
