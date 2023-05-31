package lxllyy.top.transformsqltoexcel.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author LXL
 * @description DBTable
 * @data 2023/5/29
 */
@Data
public class DBTable {

    /**
     * 表名
     */
    @ExcelProperty("表名")
    @ExcelIgnore
    private String tableName;

    /**
     * 列名
     */
    @ExcelProperty("列名")
    private String columnName;

    /**
     * 字段类型
     */
    @ExcelProperty("字段类型")
    private String data_type;

    /**
     * 字段最大长度
     */
    @ExcelProperty("字段最大长度")
    private String characterMaximumLength;

    /**
     * 是否可为空
     */
    @ExcelProperty("是否可为空")
    private String isNullable;

    /**
     * 字段默认值
     */
    @ExcelProperty("字段默认值")
    private String columnDefault;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String columnComment;



}
