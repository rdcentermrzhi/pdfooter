package com.jinrong.pdf.comp;

import com.jinrong.pdf.result.ExcelResult;
import com.jinrong.pdf.utils.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);

    private static final String DEFAULT_PAGE_WIDTH = "600";

    private static final String DEFAULT_TEXT_RISE = "30";

    private static final int DATA_LENGTH = 4;

    private static final String TEXT_SPACE = "    ";

    public ExcelResult read(File file) {
        LOGGER.info("Start to read data from excel: {}.", new Object[] { file.getAbsoluteFile() });
        ExcelResult result = new ExcelResult();
        List<String[]> list = (List)new ArrayList<String>();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
            int i = 0;
            if (i < workbook.getNumberOfSheets()) {
                LOGGER.info("Start to read sheet {}.", new Object[] { workbook.getSheetAt(i).getSheetName() });
                XSSFSheet sheet = workbook.getSheetAt(i);
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    LOGGER.info("Read row {}.", new Object[] { Integer.valueOf(j) });
                    XSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        LOGGER.error("Row {} is null. Skip it.", new Object[] { Integer.valueOf(j) });
                    } else {
                        String[] data = new String[4];
                        XSSFCell cell0 = row.getCell(0);
                        data[0] = (cell0 != null) ? cell0.toString().trim().replaceAll("\\.0", "") : "";
                        XSSFCell cell1 = row.getCell(1);
                        XSSFCell cell2 = row.getCell(2);
                        String text1 = (cell1 != null) ? cell1.getStringCellValue() : "";
                        String text2 = (cell2 != null) ? cell2.toString().trim() : "";
                        if (StringUtil.isBlank(text1) && StringUtil.isBlank(text2)) {
                            LOGGER.error("Footer text not found. Row {}.", new Object[] { Integer.valueOf(j) });
                        } else {
                            StringBuffer text = new StringBuffer();
                            text.append(text1).append("    ").append(text2);
                            data[1] = text.toString();
                            XSSFCell cell3 = row.getCell(3);
                            String text3 = (cell3 != null) ? cell3.toString().trim().replaceAll("\\.0", "") : "";
                            if (StringUtil.isBlank(text3))
                                text3 = "60";
                            data[2] = text3;
                            XSSFCell cell4 = row.getCell(4);
                            String text4 = (cell4 != null) ? cell4.toString().trim().replaceAll("\\.0", "") : "";
                            if (StringUtil.isBlank(text4))
                                text4 = "600";
                            data[3] = text4;
                            LOGGER.info("Row {}, read footer text: {}.", new Object[] { Integer.valueOf(j), Arrays.toString((Object[])data) });
                            list.add(data);
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.fail();
            result.setMessage(e.getMessage());
            LOGGER.error("Failed to read data from excel file.", e);
        } finally {
            try {
                if (workbook != null)
                    workbook.close();
            } catch (IOException e) {
                result.fail();
                result.setMessage(e.getMessage());
                LOGGER.error("Failed to close workbook.", e);
            }
        }
        result.setData(list);
        LOGGER.info("Read data from excel file finished. Total: {}. Result: {}.",
                new Object[] { Integer.valueOf(list.size()), result });
        return result;
    }

    public static void main(String[] args) {
        File file = new File("f:\\a.xlsx");
        (new ExcelParser()).read(file);
    }
}
