module mybatis.generate.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.commons.lang3;
    requires java.sql;
    requires ojdbc6;
    requires mysql.connector.j;

    opens com.zengdw.mybatis.controller to javafx.fxml;
    opens com.zengdw.mybatis.config to javafx.base;
    opens com.zengdw.mybatis.domain to javafx.base;
    exports com.zengdw.mybatis;
}