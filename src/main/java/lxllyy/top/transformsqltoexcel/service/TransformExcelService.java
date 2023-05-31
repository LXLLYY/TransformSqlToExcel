package lxllyy.top.transformsqltoexcel.service;

import lxllyy.top.transformsqltoexcel.pojo.DBTable;

import java.util.List;
import java.util.Map;

/**
 * @author LXL
 * @description TransformExcelService
 * @data 2023/5/29
 */
public interface TransformExcelService {

    /**
     * 查询当前数据库所有的表
     * @return
     */
    Map<String, String> selectTables();

    /**
     * 查询当前数据库所有的列，key 表名，value 列对象
     * @return
     */
    Map<String, List<DBTable>> selectColumns();

    /**
     * 写出 excel 文件
     */
    void writeExcel();
}
