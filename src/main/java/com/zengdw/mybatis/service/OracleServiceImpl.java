package com.zengdw.mybatis.service;

import com.zengdw.mybatis.config.DatabaseConfig;
import com.zengdw.mybatis.domain.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * oracle 数据库操作
 *
 * @author zengd
 * @version 1.0
 * @date 2023/2/28 14:32
 */
public class OracleServiceImpl implements IDatabaseService {
    @Override
    public List<Table> selectTable() throws ClassNotFoundException, SQLException {
        List<Table> list = new ArrayList<>();
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.instance().getUrl(), DatabaseConfig.instance().getUserName(), DatabaseConfig.instance().getPassword());
             Statement statement = conn.createStatement()) {
            //执行sql
            ResultSet resultSet = statement.executeQuery("select TABLE_NAME, COMMENTS from user_tab_comments");
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String comments = resultSet.getString("COMMENTS");
                list.add(new Table(false, tableName, comments));
            }
        }
        return list;
    }

}
