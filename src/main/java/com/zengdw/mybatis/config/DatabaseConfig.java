package com.zengdw.mybatis.config;

import com.zengdw.mybatis.domain.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/27 17:36
 */
@Data
@Accessors(chain = true)
public class DatabaseConfig {
    private static DatabaseConfig instance;
    private String dataType;
    private String url = "jdbc:oracle:thin:@192.168.3.92:1521:orcl";
    private String userName = "apcos_1110";
    private String password = "apcos_1110";
    private List<Table> selectedTable;

    private DatabaseConfig() {
    }

    public static DatabaseConfig instance() {
        if (null == instance) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
}

