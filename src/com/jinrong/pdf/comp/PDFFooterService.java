package com.jinrong.pdf.comp;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jinrong.pdf.entity.FooterParam;
import com.jinrong.pdf.result.CharResult;
import com.jinrong.pdf.result.Result;
import com.jinrong.pdf.utils.NumberUtil;
import com.jinrong.pdf.utils.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFFooterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PDFFooterService.class);

    private static final int DEFAULT_WORD_CHAR_WIDTH = 6;

    private static final int DEFAULT_ZH_CHAR_WIDTH = 12;

    private static final Map<Character, Integer> SPECIAL_CHAR_WIDTH_MAP = new HashMap<Character, Integer>();

    private static final int DEFAULT_FONT_SIZE = 12;

    static {
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('A'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('B'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('C'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('D'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('G'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('H'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('I'), Integer.valueOf(5));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('J'), Integer.valueOf(5));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('K'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('M'), Integer.valueOf(10));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('N'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('O'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('Q'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('R'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('S'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('T'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('U'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('V'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('W'), Integer.valueOf(10));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('X'), Integer.valueOf(9));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('Y'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('Z'), Integer.valueOf(7));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('%'), Integer.valueOf(9));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('f'), Integer.valueOf(5));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('i'), Integer.valueOf(4));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('j'), Integer.valueOf(4));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('l'), Integer.valueOf(4));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('s'), Integer.valueOf(4));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('r'), Integer.valueOf(4));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('t'), Integer.valueOf(4));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('m'), Integer.valueOf(8));
        SPECIAL_CHAR_WIDTH_MAP.put(Character.valueOf('w'), Integer.valueOf(8));
    }

    private static final BaseColor DEFAULT_FONT_COLOR = new BaseColor(0, 0, 0);

    public Result setFooter(File inputFile, File outputFile, Map<Integer, FooterParam> paramMap) {
        LOGGER.info("Start to set footer. Input file: {}. Output file: {}.",
                new Object[] { inputFile.getAbsolutePath(), outputFile.getAbsolutePath() });
        long tStart = System.currentTimeMillis();
        int numberOfPages = 0;
        Result result = new Result();
        try {
            PdfReader reader = new PdfReader(inputFile.getAbsolutePath());
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            BaseFont zhFont = BaseFont.createFont("C:/Windows/Fonts/simsun.ttc,0", BaseFont.IDENTITY_H , true);
            BaseFont wordFont = BaseFont.createFont("Times-Roman", "Cp1252", true);
            //BaseFont baseFont = BaseFont.createFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            numberOfPages = reader.getNumberOfPages();
            for (int pageNum = 1; pageNum <= numberOfPages; pageNum++) {
                FooterParam param = paramMap.get(Integer.valueOf(pageNum));
                if (param == null) {
                    LOGGER.warn("No footer parameter found. Page number: {}.", new Object[] { Integer.valueOf(pageNum) });
                } else {
                    PdfContentByte under = stamper.getOverContent(pageNum);
                    CharResult charResult = calcCharsCount(param.getText(), zhFont, wordFont);
                    float pageWidth = reader.getPageSizeWithRotation(pageNum).getWidth();
                    float pageHeight = reader.getPageSizeWithRotation(pageNum).getHeight();
                    System.out.println(pageHeight);
                    float footerWidth = 538.5f;
                    float start = 42.75f;
                    float charResultTotalWidth = charResult.getTotalWidth();

                    List<CharInfo> currentLine = new ArrayList<>();
                    float currentLineWidth = 0; // 当前行的宽度
                    int num = (int)Math.ceil(charResultTotalWidth/footerWidth);

                    for (int i = 0; i < charResult.getCharInfoList().size(); i++) {
                        CharInfo charInfo = charResult.getCharInfoList().get(i);
                        float charWidth = charInfo.getWidth();
                        // 检查添加当前字符后是否超出页面宽度
                        if (currentLineWidth + charWidth > footerWidth) {
                            drawLine(under, currentLine, param, footerWidth, zhFont, wordFont, currentLineWidth, start, num);

                            currentLine.clear();
                            currentLineWidth = 0;
                            num--;
                        }
                        // 添加当前字符到当前行
                        currentLine.add(charInfo);
                        currentLineWidth += charWidth;
                    }

                    // 绘制最后一行（如果有字符）
                    if (currentLine.size() > 0) {
                        drawLine(under, currentLine, param, footerWidth, zhFont, wordFont, currentLineWidth, start, num);
                    }
                    LOGGER.info("Page {} finished. Param: {}.", new Object[] { Integer.valueOf(pageNum), param });
                }
            }
            stamper.close();
        } catch (Exception e) {
            LOGGER.error("Failed to set footer.", e);
            result.fail();
            result.setMessage(e.getMessage());
        }
        LOGGER.info("Set PDF footer finished. Pages: {}. Result: {}. Time: {}s.",
                new Object[] { Integer.valueOf(numberOfPages), result, NumberUtil.round(Double.valueOf((System.currentTimeMillis() - tStart) / 1000.0D), 2) });
        return result;
    }

    private void drawLine(PdfContentByte under, List<CharInfo> lineChars, FooterParam param ,float footerWidth,
                          BaseFont zhFont, BaseFont wordFont,
                          float currentLineWidth, float start, int num){
        float lineHeight = 15; // 行高，您可以根据需要调整

        float leftPadding = (footerWidth - currentLineWidth) / 2 + start;
        for (CharInfo charInfo : lineChars) {
            under.beginText();
            under.setColorFill(DEFAULT_FONT_COLOR);
            under.setTextRise(param.getTextRise());
            under.setFontAndSize(charInfo.isZhChar() ? zhFont : wordFont, 12.0F);
            under.showTextAligned(0, String.valueOf(charInfo.getValue()), leftPadding, (num - 1) * lineHeight, 0.0F); // 2 表示右对齐
            under.endText();
            leftPadding += charInfo.getWidth();
        }
    }

    public Integer readPageCount(File inputFile) {
        try {
            return Integer.valueOf((new PdfReader(inputFile.getAbsolutePath())).getNumberOfPages());
        } catch (Exception e) {
            LOGGER.error("Failed to read page count.", e);
            return null;
        }
    }
    public static boolean isChinese(char c) {
        // 判断字符是否在中文字符的 Unicode 范围内
        if ((c >= 0x4E00 && c <= 0x9FA5) || // CJK 统一汉字
                (c >= 0x3400 && c <= 0x4DBF) || // CJK 统一汉字扩展A
                (c >= 0x20000 && c <= 0x2A6DF) || // CJK 统一汉字扩展B
                (c >= 0x2A700 && c <= 0x2B73F) || // CJK 统一汉字扩展C
                (c >= 0x2B740 && c <= 0x2B81F) || // CJK 统一汉字扩展D
                (c >= 0x2B820 && c <= 0x2CEAF) || // CJK 统一汉字扩展E
                (c >= 0xF900 && c <= 0xFAFF) ||   // CJK 兼容字符
                (c >= 0xE7C7 && c <= 0xE7F3)) {    // CJK 兼容汉字
            return true; // 是中文字符
        }

        // 判断字符是否为中文标点符号
        return (c >= 0x3000 && c <= 0x303F) || // CJK 符号和标点
                (c >= 0xFF00 && c <= 0xFFEF);   // 全角字符

    }
    private static final Map<Character, Integer> SPECIAL_CUSTOM_CHAR_WIDTH_MAP = new HashMap<Character, Integer>();
    private static final Map<Character, Boolean> SPECIAL_CUSTOM_CHAR_FONT_MAP = new HashMap<Character, Boolean>();
    static {
        SPECIAL_CUSTOM_CHAR_WIDTH_MAP.put(Character.valueOf('-'), Integer.valueOf(5));
        SPECIAL_CUSTOM_CHAR_WIDTH_MAP.put(Character.valueOf('\u2014'), Integer.valueOf(12));
    }
    static {
        SPECIAL_CUSTOM_CHAR_FONT_MAP.put(Character.valueOf('-'), false);
        SPECIAL_CUSTOM_CHAR_FONT_MAP.put(Character.valueOf('\u2014'), true);
    }

    private CharResult calcCharsCount(String text, BaseFont zhFont, BaseFont workFont) {
        CharResult result = new CharResult();

        if (!StringUtil.isBlank(text)) {
            char[] chars = text.trim().toCharArray();
            for (int i = 0; i < chars.length; ++i) {
                CharInfo charInfo = new CharInfo();
                charInfo.setValue(chars[i]);
                if (isChinese(chars[i])){
                    charInfo.setZhChar(true);
                    charInfo.setWidth(zhFont.getWidth(chars[i])*12/1000);
                }else{
                    charInfo.setZhChar(false);
                    charInfo.setWidth(workFont.getWidth(chars[i])*12/1000);
                }

                if (SPECIAL_CUSTOM_CHAR_WIDTH_MAP.containsKey(chars[i])){
                    charInfo.setZhChar(SPECIAL_CUSTOM_CHAR_FONT_MAP.get(chars[i]));
                    charInfo.setWidth(SPECIAL_CUSTOM_CHAR_WIDTH_MAP.get(chars[i]));
                }

                if (charInfo.getWidth() == 0){
                    charInfo.setZhChar(true);
                    charInfo.setWidth(zhFont.getWidth(chars[i])*12/1000);
                }


//                if (SPECIAL_CHAR_WIDTH_MAP.containsKey(chars[i])) {
//                    charInfo.setZhChar(false);
//                    charInfo.setWidth((Integer) SPECIAL_CHAR_WIDTH_MAP.get(chars[i]));
//                } else if (chars[i] >= 0 && chars[i] <= 127) {
//                    charInfo.setZhChar(false);
//                    charInfo.setWidth(6);
//                } else {
//                    charInfo.setZhChar(true);
//                    charInfo.setWidth(12);
//                }

                result.setTotalWidth(result.getTotalWidth() + charInfo.getWidth());
                result.getCharInfoList().add(charInfo);
            }

            LOGGER.info("Calculate character finished. Text: {}. Char result: {}.", new Object[]{text, result});
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "F:\\pdf\\test.pdf";
        try {
            PdfReader reader = new PdfReader(filePath);
            System.out.println(reader.getPageSize(1));
            System.out.println(reader.getPageSize(2));
            System.out.println(reader.getPageSize(37));
            System.out.println(reader.getPageSize(38));
            System.out.println(reader.getPageSizeWithRotation(1));
            System.out.println(reader.getPageSizeWithRotation(338));
        } catch (Exception e) {
            LOGGER.error("Failed to set footer.", e);
        }
    }
}
