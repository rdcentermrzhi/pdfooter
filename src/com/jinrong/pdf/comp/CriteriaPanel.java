//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jinrong.pdf.comp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jinrong.pdf.entity.FooterParam;
import com.jinrong.pdf.result.ExcelResult;
import com.jinrong.pdf.result.Result;
import com.jinrong.pdf.utils.NumberUtil;
import com.jinrong.pdf.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;


public class CriteriaPanel extends JPanel {
    private static final long serialVersionUID = 1978594095396783978L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CriteriaPanel.class);

    private static File LAST_DIRECTORY_EXCEL;

    private static File LAST_DIRECTORY_PDF;

    private MainFrame mainFrame;

    private JButton btnSelectExl = new JButton("选择Excel文件");

    private JButton btnSelectPdf = new JButton("选择PDF文件  ");

    private JButton btnSubmit = new JButton("  开 始   ");

    private JButton btnCancel = new JButton("  取 消  ");

    private JTextField txtExlPath = new JTextField();

    private JTextField txtPdfPath = new JTextField();

    private ExcelParser excelParser = new ExcelParser();

    private PDFFooterService footerSvr = new PDFFooterService();

    public CriteriaPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BoxLayout(this, 1));
        addComponents();
        addListeners();
    }

    public void addComponents() {
        Font txtFont = new Font("宋体", 0, 20);
        this.txtExlPath.setFont(txtFont);
        this.txtPdfPath.setFont(txtFont);
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, 0));
        p1.setPreferredSize(new Dimension(30, 30));
        p1.add(Box.createRigidArea(new Dimension(10, 10)));
        p1.add(this.btnSelectExl);
        p1.add(Box.createRigidArea(new Dimension(10, 10)));
        p1.add(this.txtExlPath);
        p1.add(Box.createRigidArea(new Dimension(10, 10)));
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, 0));
        p2.setPreferredSize(new Dimension(30, 30));
        p2.add(Box.createRigidArea(new Dimension(10, 10)));
        p2.add(this.btnSelectPdf);
        p2.add(Box.createRigidArea(new Dimension(10, 10)));
        p2.add(this.txtPdfPath);
        p2.add(Box.createRigidArea(new Dimension(10, 10)));
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, 0));
        p3.add(this.btnSubmit);
        p3.add(Box.createRigidArea(new Dimension(20, 10)));
        p3.add(this.btnCancel);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(p1);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(p2);
        add(Box.createRigidArea(new Dimension(10, 10)));
        add(p3);
        add(Box.createRigidArea(new Dimension(15, 15)));
    }

    private void addListeners() {
        this.btnSelectExl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(0);
                if (CriteriaPanel.LAST_DIRECTORY_EXCEL != null)
                    fileChooser.setCurrentDirectory(CriteriaPanel.LAST_DIRECTORY_EXCEL);
                fileChooser.setFileFilter(new FileFilter() {
                    public String getDescription() {
                        return "*.xlsx";
                    }

                    public boolean accept(File file) {
                        String name = file.getName();
                        return !(!file.isDirectory() && !name.toLowerCase().endsWith(".xlsx"));
                    }
                });
                if (fileChooser.showDialog(CriteriaPanel.this.mainFrame, "选择") == 0) {
                    File file = fileChooser.getSelectedFile();
                    CriteriaPanel.this.txtExlPath.setText(file.getAbsolutePath());
                    CriteriaPanel.LOGGER.info("Select Excel file: {}.", new Object[]{file.getAbsolutePath()});
                    CriteriaPanel.LAST_DIRECTORY_EXCEL = file.getParentFile();
                    MessagePanel.getInstance().setMessage(file.getAbsolutePath());
                    ExcelResult excelResult = CriteriaPanel.this.excelParser.read(file);
                    if (excelResult.isSuccess()) {
                        CriteriaPanel.this.mainFrame.getTableModel().setData(excelResult.getData());
                        CriteriaPanel.this.mainFrame.getTableModel().fireTableDataChanged();
                        MessagePanel.getInstance().setMessage("文件:" + file.getAbsolutePath() + "读取完成");
                    } else {
                        MessagePanel.getInstance().setMessage("文件:" + file.getAbsolutePath() + "读取失败! 错误消息" + excelResult.getMessage());
                    }
                }
            }
        });
        this.btnSelectPdf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(0);
                if (CriteriaPanel.LAST_DIRECTORY_PDF != null)
                    fileChooser.setCurrentDirectory(CriteriaPanel.LAST_DIRECTORY_PDF);
                fileChooser.setFileFilter(new FileFilter() {
                    public String getDescription() {
                        return "*.pdf";
                    }

                    public boolean accept(File file) {
                        String name = file.getName();
                        return !(!file.isDirectory() && !name.toLowerCase().endsWith(".pdf"));
                    }
                });
                if (fileChooser.showDialog(CriteriaPanel.this.mainFrame, "选择") == 0) {
                    File file = fileChooser.getSelectedFile();
                    CriteriaPanel.this.txtPdfPath.setText(file.getAbsolutePath());
                    CriteriaPanel.LOGGER.info("Select PDF file: {}.", new Object[]{file.getAbsolutePath()});
                    CriteriaPanel.LAST_DIRECTORY_PDF = file.getParentFile();
                }
            }
        });
        this.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CriteriaPanel.this.txtExlPath.setText("");
                CriteriaPanel.this.txtPdfPath.setText("");
                CriteriaPanel.this.mainFrame.getTableModel().setData(null);
                CriteriaPanel.this.mainFrame.getTableModel().fireTableDataChanged();
            }
        });
        this.btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File fileExcel = new File(CriteriaPanel.this.txtExlPath.getText());
                File filePdf = new File(CriteriaPanel.this.txtPdfPath.getText());
                String errorMsg = CriteriaPanel.this.validateExcelData();
                if (StringUtil.isNotBlank(errorMsg)) {
                    MessagePanel.getInstance().setMessage(errorMsg, false);
                    return;
                }
                if (!filePdf.isFile() || !filePdf.exists()) {
                    MessagePanel.getInstance().setMessage("请选择有效的PDF文件", false);
                    return;
                }
                Integer totalPages = CriteriaPanel.this.footerSvr.readPageCount(filePdf);
                Map<Integer, FooterParam> footerParamMap = CriteriaPanel.this.createFooterParams();
                if (totalPages == null || !totalPages.equals(Integer.valueOf(footerParamMap.size()))) {
                    MessagePanel.getInstance().setMessage("ExcelExcel数据总数和PDF页数不等! Excel数据总数: " +
                            footerParamMap.size() + ", PDF页数：" + totalPages, false);
                    return;
                }
                if (JOptionPane.showConfirmDialog(CriteriaPanel.this.mainFrame, "确定开始设置PDF页脚吗？",
                        "确认", 0, 3) == 0) {
                    MessagePanel.getInstance().setMessage("");
                    String destFilename = filePdf.getName().substring(0, filePdf.getName().lastIndexOf(".")) + (
                            new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".pdf";
                    File destFilePdf = new File(filePdf.getParent(), destFilename);
                    CriteriaPanel.LOGGER.info("Start to set PDF footer. Excel: {}, PDF: {}. Dest PDF: {}.",
                            new Object[]{fileExcel.getAbsolutePath(), filePdf.getAbsolutePath(), destFilePdf.getAbsolutePath()});
                    Result result = CriteriaPanel.this.footerSvr.setFooter(filePdf, destFilePdf, footerParamMap);
                    if (result.isSuccess()) {
                        MessagePanel.getInstance().setMessage("操作成功， 新的PDF文件路径:" + destFilePdf.getAbsolutePath(), true);
                    } else {
                        MessagePanel.getInstance().setMessage("操作失败,错误消息:" + result.getMessage(), false);
                    }
                }
            }
        });
    }

    private String validateExcelData() {
        List<String[]> data = this.mainFrame.getTableModel().getData();
        if (data == null || data.size() <= 0)
            return "无Excel数据";
        String errorMsg = "表格数据无效";
        for (String[] ss : data) {
            if (ss.length < 4)
                return errorMsg;
            Integer page = NumberUtil.parseInt(ss[0].trim());
            Integer margin = NumberUtil.parseInt(ss[2].trim());
            Integer pageWidth = NumberUtil.parseInt(ss[3].trim());
            if (page == null || page.intValue() < 0 ||
                    margin == null || margin.intValue() < 0 || margin.intValue() > 999 ||
                    pageWidth == null || pageWidth.intValue() < 0 || pageWidth.intValue() > 9999)
                return errorMsg;
        }
        return null;
    }
    private Map<Integer, FooterParam> createFooterParams() {
        Map<Integer, FooterParam> map = new HashMap<Integer, FooterParam>();
        for (String[] ss : this.mainFrame.getTableModel().getData()) {
            FooterParam param = new FooterParam();
            Integer page = NumberUtil.parseInt(ss[0].trim());
            param.setPage(page.intValue());
            param.setText(ss[1].trim());
            param.setTextRise(NumberUtil.parseInt(ss[2].trim()).intValue());
            param.setPageWidth(NumberUtil.parseInt(ss[3].trim()).intValue());
            map.put(page, param);
        }
        return map;
    }
}
