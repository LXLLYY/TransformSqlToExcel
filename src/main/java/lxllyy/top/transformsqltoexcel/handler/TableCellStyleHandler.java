package lxllyy.top.transformsqltoexcel.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.commons.math3.geometry.partitioning.Region;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author LXL
 * @description TableCellStyleHandler
 * @data 2023/5/30
 */
public class TableCellStyleHandler implements CellWriteHandler {

    @Override
    public int order() {
        return 1000000;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = sheet.getWorkbook();
        String stringCellValue = cell.getStringCellValue();

        cellNormalStyle(workbook, cell);

        CellStyle cellHeadStyle = getCellHeadStyle(workbook);

        switch (stringCellValue) {
            // if your have some column comment call "a",must change table head to other name
            case "字段说明":
                Row row = cell.getRow();
                int rowIndex = cell.getRowIndex();
                Row lastOneRow = sheet.getRow(rowIndex - 1);
                Row lastTwoRow = sheet.getRow(rowIndex - 2);
                for (int i = 0; i < 6; i++) {
                    row.getCell(i).setCellStyle(cellHeadStyle);
                    lastOneRow.getCell(i).setCellStyle(cellHeadStyle);
                    if (lastTwoRow != null) {
                        lastTwoRow.getCell(i).setCellStyle(workbook.createCellStyle());
                    }
                }
                sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 5));
                break;
            default:
                break;
        }

    }

    public CellStyle getCellHeadStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置字体大小
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        return cellStyle;

    }

    public void cellNormalStyle(Workbook workbook, Cell cell) {
        CellStyle cellStyle = workbook.createCellStyle();
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置字体大小
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);
    }
}
