package com.seecoder.BlueWhale.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;
import com.seecoder.BlueWhale.vo.OrderVO;

@Component
public class ExcelUtil {

    public CellStyle headSytle(SXSSFWorkbook workbook) {
        // 设置style1的样式，此样式运用在第二行
        CellStyle style1 = workbook.createCellStyle();// cell样式
        // 设置单元格背景色，设置单元格背景色以下两句必须同时设置
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 设置填充样式
        style1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置填充色
        // 设置单元格上、下、左、右的边框线
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        Font font1 = workbook.createFont();// 创建一个字体对象
//        font1.setBoldweight((short) 10);// 设置字体的宽度
        font1.setFontHeightInPoints((short) 10);// 设置字体的高度
        font1.setBold(true);// 粗体显示
        style1.setFont(font1);// 设置style1的字体
        style1.setWrapText(true);// 设置自动换行
        style1.setAlignment(HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
        return style1;
    }

    public CellStyle contentStyle(SXSSFWorkbook wb) {
        // 设置style1的样式，此样式运用在第二行
        CellStyle style1 = wb.createCellStyle();// cell样式
        // 设置单元格上、下、左、右的边框线
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setWrapText(true);// 设置自动换行
        style1.setAlignment(HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
        return style1;
    }

    public void initTitleEX(SXSSFSheet sheet, CellStyle header, List<String> attributeList, int titleLength[]) {
        SXSSFRow row0 = sheet.createRow(0);
        row0.setHeight((short) 800);
        for (int j = 0; j < attributeList.size(); j++) {
            SXSSFCell cell = row0.createCell(j);
            //设置每一列的字段名
            cell.setCellValue(attributeList.get(j));
            cell.setCellStyle(header);
            sheet.setColumnWidth(j, titleLength[j]);
        }
    }

    public InputStream exportExcel(List<OrderVO> orders) {

        ByteArrayOutputStream output = null;
        InputStream inputStream = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = headSytle(wb);
        // 报表体样式 cell样式
        CellStyle content = contentStyle(wb);
        // 每一列字段名
        List<String> attributeList = orders.get(0).getHeaderList();
        // 字段名所在表格的宽度
        int[] ints = new int[attributeList.size()];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = 5000;
        }

        // 设置表头样式
        initTitleEX(sheet, header, attributeList, ints);
        //写入表格
        for (OrderVO orderVO : orders) {
            SXSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            List<Object> cellContent = orderVO.getCellContent();
            int columnNumber = 0;
            for (Object item : cellContent) {
                SXSSFCell cell = row.createCell(columnNumber++);
                cell.setCellValue(String.valueOf(item));
            }
        }
        
        try {
            //将wb中的数据以字节形式存到inputStream1中
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream;

    }
}
