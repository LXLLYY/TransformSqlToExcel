package lxllyy.top.transformsqltoexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import lxllyy.top.transformsqltoexcel.handler.TableCellStyleHandler;
import lxllyy.top.transformsqltoexcel.handler.TableSheetStyleHandler;
import lxllyy.top.transformsqltoexcel.pojo.DBTable;
import lxllyy.top.transformsqltoexcel.service.TransformExcelService;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author LXL
 * @description TransformExcelServiceImpl
 * @data 2023/5/29
 */
@Service
@Slf4j
public class TransformExcelServiceImpl implements TransformExcelService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String url;

    private String databaseName;

//    private Map<String, String> tableCommentMap;
//
//    private Map<String, List<DBTable>> tableColumnMap;

    @PostConstruct
    public void talkDatebaseName() {
        String regexp = "jdbc:mysql:\\/\\/\\S*\\/(\\S*)\\?";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            this.databaseName = matcher.group(1);
        }
    }

    /**
     * 查询当前数据库所有的表
     *
     * @return
     */
    @Override
    public Map<String, String> selectTables() {
        HashMap<String, String> tableCommentMap = new HashMap<>();

        RowCallbackHandler tableCommentHandler = rs -> {
            String tableName = rs.getString("TABLE_NAME");
            String tableComment = rs.getString("TABLE_COMMENT");
            tableCommentMap.put(tableName, tableComment);
        };

        String sql = "SELECT\n" +
                "\tTABLE_NAME,\n" +
                "\tTABLE_COMMENT\n" +
                "FROM\n" +
                "\tINFORMATION_SCHEMA.TABLES\n" +
                "WHERE\n" +
                "\tTABLE_SCHEMA = '" + this.databaseName + "'";

        jdbcTemplate.query(sql, tableCommentHandler);

        return tableCommentMap;
    }

    /**
     * 查询当前数据库所有的列，key 表名，value 列对象
     *
     * @return
     */
    @Override
    public Map<String, List<DBTable>> selectColumns() {
        String sql = "SELECT\n" +
                "\tTABLE_NAME,\n" +
                "\tCOLUMN_NAME,\n" +
                "\tCOLUMN_TYPE,\n" +
                "\tDATA_TYPE,\n" +
                "\tCHARACTER_MAXIMUM_LENGTH,\n" +
                "\tIS_NULLABLE,\n" +
                "\tCOLUMN_DEFAULT,\n" +
                "\tCOLUMN_COMMENT\n" +
                "FROM\n" +
                "\tINFORMATION_SCHEMA.COLUMNS \n" +
                "WHERE\n" +
                "\ttable_schema = '"+this.databaseName+"'";

        List<DBTable> dbTables = jdbcTemplate.query(sql, (rs, i) -> {
            DBTable dbTable = new DBTable();
            dbTable.setTableName(rs.getString("TABLE_NAME"));
            dbTable.setColumnName(rs.getString("COLUMN_NAME"));
            dbTable.setData_type(rs.getString("COLUMN_TYPE"));
            dbTable.setCharacterMaximumLength(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
            dbTable.setIsNullable(rs.getString("IS_NULLABLE"));
            dbTable.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
            dbTable.setColumnComment(rs.getString("COLUMN_COMMENT"));
            return dbTable;
        });

        Map<String, List<DBTable>> tableColumnMap = dbTables.stream().collect(Collectors.groupingBy(dbTable -> {
            return dbTable.getTableName();
        }));

        return tableColumnMap;
    }

    /**
     * 写出 excel 文件
     */
    @Override
    public void writeExcel() {
        Map<String, String> tableCommentMap = this.selectTables();
        Map<String, List<DBTable>> tableColumnMap = this.selectColumns();

        if (ObjectUtils.isEmpty(tableColumnMap) || ObjectUtils.isEmpty(tableCommentMap)) {
            log.error("Not hava database information,please check your sql connection is available or not");
            return;
        }

        String fileName = System.getProperty("user.home") + "\\Desktop\\" + this.databaseName + "_数据库表.xlsx";

        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DBTable.class).registerWriteHandler(new TableCellStyleHandler()).registerWriteHandler(new TableSheetStyleHandler()).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet(this.databaseName).needHead(Boolean.FALSE).build();

            Set<Map.Entry<String, List<DBTable>>> tableColumnEntry = tableColumnMap.entrySet();
            Iterator<Map.Entry<String, List<DBTable>>> iterator = tableColumnEntry.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<DBTable>> columnList = iterator.next();

                String tableNamekey = columnList.getKey();
                List<DBTable> columnListValue = columnList.getValue();
                // 插入空行
                if (iterator.hasNext()) {
                    columnListValue.add(new DBTable());
                }

                String tableComment = tableCommentMap.get(tableNamekey);
                ArrayList<List<String>> tableDetail = new ArrayList<>();
                ArrayList<String> tableDetail_0 = new ArrayList<>();
                tableDetail_0.add(tableNamekey);
                tableDetail_0.add(tableComment);
                for (int i = 0; i < 4; i++) {
                    
                }
                tableDetail_0.add("");
                tableDetail_0.add("");
                tableDetail_0.add("");
                tableDetail_0.add("");
                tableDetail.add(tableDetail_0);
                excelWriter.write(tableDetail, writeSheet);

                ArrayList<List<String>> columnInformation = new ArrayList<>();
                ArrayList<String> columnInformation_0 = new ArrayList<>();
                columnInformation_0.add("列名");
                columnInformation_0.add("字段类型");
                columnInformation_0.add("字段最大长度");
                columnInformation_0.add("是否可为空");
                columnInformation_0.add("字段默认值");
                columnInformation_0.add("字段说明");
                columnInformation.add(columnInformation_0);
                excelWriter.write(columnInformation, writeSheet);

                excelWriter.write(columnListValue, writeSheet);
            }
        }

    }
}
