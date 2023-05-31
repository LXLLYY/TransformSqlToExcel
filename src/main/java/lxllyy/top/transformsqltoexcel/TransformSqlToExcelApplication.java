package lxllyy.top.transformsqltoexcel;

import lxllyy.top.transformsqltoexcel.service.TransformExcelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TransformSqlToExcelApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TransformSqlToExcelApplication.class, args);
        TransformExcelService transformExcelService = context.getBean(TransformExcelService.class);
        transformExcelService.writeExcel();

    }

}
