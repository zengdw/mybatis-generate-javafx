package com.zengdw.mybatis.service;

import com.zengdw.mybatis.domain.Table;

import java.util.List;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/28 14:32
 */
public interface IDatabaseService {
    /**
     * 表列表查询
     */
    List<Table> selectTable() throws Exception;

}
