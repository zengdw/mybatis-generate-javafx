package com.zengdw.mybatis.service;

import com.zengdw.mybatis.config.DatabaseConfig;
import com.zengdw.mybatis.domain.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/28 15:02
 */
public class MysqlServiceImpl implements IDatabaseService {
    @Override
    public List<Table> selectTable() throws ClassNotFoundException, SQLException {
        List<Table> list = new ArrayList<>();
        //加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(DatabaseConfig.instance().getUrl(), DatabaseConfig.instance().getUserName(), DatabaseConfig.instance().getPassword()); Statement statement = conn.createStatement()) {
            //执行sql
            ResultSet resultSet = statement.executeQuery("SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = '" + DatabaseConfig.instance().getDatabase() + "'");
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String comments = resultSet.getString("TABLE_COMMENT");
                list.add(new Table(false, tableName, comments));
            }
        }
        return list;
    }

}
