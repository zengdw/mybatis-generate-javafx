module mybatis.generate.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.commons.lang3;
    requires java.sql;
    requires ojdbc6;
    requires mysql.connector.j;
    requires freemarker;
    requires org.mybatis.generator;
    requires com.github.javaparser.core;
    requires org.slf4j;

    opens com.zengdw.mybatis.controller to javafx.fxml;
    opens com.zengdw.mybatis.config to javafx.base, freemarker;
    opens com.zengdw.mybatis.domain to javafx.base;
    opens com.zengdw.mybatis to javafx.graphics;
    opens com.zengdw.mybatis.plugins to org.mybatis.generator;
    opens com.zengdw.mybatis.generator to org.mybatis.generator;
}