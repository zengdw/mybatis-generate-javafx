package com.zengdw.mybatis.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zengd
 * @date 2023/3/5 7:32
 */
@Data
@Accessors(chain = true)
public class GeneratorProperties {
    /**
     * 项目地址 全路径
     */
    private String projectPath;

    /**
     * 实体类文件路径
     */
    private String modelPath;

    /**
     * mapper接口文件路径
     */
    private String mapperPath;

    /**
     * mapper xml 文件路径
     */
    private String mapperXmlPath;
    /**
     * 是否需要lombok
     */
    private boolean needLombok = false;

    /**
     * 实体类父类全路径
     */
    private String modelSuperClassPath;

    /**
     * 是否需要mybatis plus
     */
    private boolean needMybatisPlus = false;
    /**
     * 表前缀
     */
    private String tableNamePre;
    /**
     * 表后缀
     */
    private String tableNameSuf;
    /**
     * 文件合并失败是否继续，默认退出
     */
    private boolean mergeFailed = false;

    /**
     * mybatis generator 生成类型 MyBatis3 or MyBatis3Simple
     * MyBatis3Simple 不生成example
     */
    private boolean generateType;
    private DatabaseTypeEnum dataType;
    private String url = "jdbc:oracle:thin:@192.168.3.92:1521:orcl";
    private String userName = "apcos_1110";
    private String password = "apcos_1110";
    private String database;
    private List<String> tables;

    private static GeneratorProperties instance;

    private GeneratorProperties() {

    }

    public static GeneratorProperties of() {
        if (null == instance) {
            instance = new GeneratorProperties();
        }
        return instance;
    }

    public String getDriverClass() {
        if (DatabaseTypeEnum.Oracle.equals(this.dataType)) {
            return "oracle.jdbc.OracleDriver";
        } else {
            return "com.mysql.cj.jdbc.Driver";
        }
    }
}
