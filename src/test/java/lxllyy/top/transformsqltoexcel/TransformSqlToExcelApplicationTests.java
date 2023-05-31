package lxllyy.top.transformsqltoexcel;

import lxllyy.top.transformsqltoexcel.pojo.DBTable;
import lxllyy.top.transformsqltoexcel.service.TransformExcelService;
import lxllyy.top.transformsqltoexcel.service.impl.TransformExcelServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class TransformSqlToExcelApplicationTests {

    @Test
    void TestJDBC(@Autowired JdbcTemplate jdbcTemplate) {
        String sql = "SELECT\n" +
                "\tTABLE_NAME,\n" +
                "\tCOLUMN_NAME,\n" +
                "\tDATA_TYPE,\n" +
                "\tCHARACTER_MAXIMUM_LENGTH,\n" +
                "\tIS_NULLABLE,\n" +
                "\tCOLUMN_DEFAULT,\n" +
                "\tCOLUMN_COMMENT \n" +
                "FROM\n" +
                "\tINFORMATION_SCHEMA.COLUMNS \n" +
                "WHERE\n" +
                "\ttable_schema = 'sz_questionnaire' limit 1";

        RowMapper<DBTable> dbTableRowMapper = new RowMapper<>(){
            @Override
            public DBTable mapRow(ResultSet rs, int rowNum) throws SQLException {
                DBTable dbTable = new DBTable();
                dbTable.setTableName(rs.getString("TABLE_NAME"));
                dbTable.setColumnName(rs.getString("COLUMN_NAME"));
                dbTable.setData_type(rs.getString("DATA_TYPE"));
                dbTable.setCharacterMaximumLength(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
                dbTable.setIsNullable(rs.getString("IS_NULLABLE"));
                dbTable.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
                dbTable.setColumnComment(rs.getString("COLUMN_COMMENT"));
                return dbTable;
            }
        };

        DBTable dbTable = jdbcTemplate.queryForObject(sql, dbTableRowMapper);

        System.out.println(dbTable);
    }

    @Test
    void testToadaptation() {
        String url = "jdbc:mysql://47.120.37.9:33060/information_schema?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&nullCatalogMeansCurrent=true";
        String regexp = "jdbc:mysql:\\/\\/\\S*\\/(\\S*)\\?";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    @Test
    void testExceteDataBaseName(@Autowired TransformExcelService transformExcelService) {
//        Map<String, String> tableCommentMap = transformExcelService.selectTables();
//        tableCommentMap.entrySet().stream().forEach(System.out::println);
//        System.out.println("=====================================================================");
//        Map<String, List<DBTable>> tableColumnMap = transformExcelService.selectColumns();
//        tableColumnMap.entrySet().stream().forEach(System.out::println);
//        System.out.println("=====================================================================");

        transformExcelService.writeExcel();

    }
}
