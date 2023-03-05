package com.zengdw.mybatis.config;

import lombok.Data;

import java.util.List;

/**
 * @author zengd
 * @date 2023/3/5 7:32
 */
@Data
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
     * 需要生成代码的表名
     */
    private List<String> tables;

    /**
     * 是否需要mybatis plus
     */
    private boolean needMybatisPlus = false;
    /**
     * 表前缀
     */
    private String tableNamePre;
    /**
     * 文件合并失败是否继续，默认退出
     */
    private boolean mergeFailed = false;

    /**
     * mybatis generator 生成类型 MyBatis3 or MyBatis3Simple
     * MyBatis3Simple 不生成example
     */
    private boolean generateType;
}
