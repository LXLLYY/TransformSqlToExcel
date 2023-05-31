package lxllyy.top.transformsqltoexcel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.Iterator;

/**
 * @author LXL
 * @description TableSheetStyleHandler
 * @data 2023/5/31
 */
public class TableSheetStyleHandler implements SheetWriteHandler {
    public static int lastRow = 0;

    @Override
    public int order() {
        return 100000;
    }

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        String sheetName = context.getWriteSheetHolder().getSheetName();
        System.out.println(context.getWriteSheetHolder().getSheet().getLastRowNum());

    }
}
