package com.jinrong.pdf.comp;

import com.jinrong.pdf.utils.NumberUtil;
import com.jinrong.pdf.utils.StringUtil;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class TextTable extends JTable {
    private static final long serialVersionUID = -3840642342182571102L;

    private static final int COLUMN_WIDTH_PAGE = 100;

    private static final int COLUMN_WIDTH_MARGIN = 150;

    private static final int COLUMN_PAGE_WIDTH = 150;

    public TextTable(TableModel model) {
        super(model);
        getColumnModel().getColumn(0).setPreferredWidth(100);
        getColumnModel().getColumn(0).setMaxWidth(100);
        getColumnModel().getColumn(0).setMinWidth(100);
        getColumnModel().getColumn(2).setPreferredWidth(150);
        getColumnModel().getColumn(2).setMaxWidth(150);
        getColumnModel().getColumn(2).setMinWidth(150);
        getColumnModel().getColumn(3).setPreferredWidth(150);
        getColumnModel().getColumn(3).setMaxWidth(150);
        getColumnModel().getColumn(3).setMinWidth(150);
        TableColumn colPage = getColumnModel().getColumn(0);
        DefaultTableCellRenderer tcrPage = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 4905749205516910981L;

            public void setValue(Object value) {
                if (value == null || StringUtil.isBlank(value.toString())) {
                    setBackground(Color.RED);
                    setText("");
                } else {
                    Integer page = NumberUtil.parseInt(value.toString().trim());
                    if (page == null) {
                        setBackground(Color.RED);
                        setText("");
                    } else {
                        setBackground(Color.WHITE);
                        setText(String.valueOf(Math.round(page.intValue())));
                    }
                }
            }
        };
        tcrPage.setHorizontalAlignment(0);
        colPage.setCellRenderer(tcrPage);
        TableColumn colMargin = getColumnModel().getColumn(2);
        DefaultTableCellRenderer tcrMargin = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 4905749205516910981L;

            public void setValue(Object value) {
                if (value == null || StringUtil.isBlank(value.toString())) {
                    setBackground(Color.RED);
                    setText("");
                } else {
                    if (value.toString().matches("\\d{1,3}")) {
                        setBackground(Color.WHITE);
                    } else {
                        setBackground(Color.RED);
                    }
                    setText(value.toString());
                }
            }
        };
        tcrMargin.setHorizontalAlignment(0);
        colMargin.setCellRenderer(tcrMargin);
        TableColumn colPageWidth = getColumnModel().getColumn(3);
        DefaultTableCellRenderer tcrPageWidth = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 4905749205516910981L;

            public void setValue(Object value) {
                if (value == null || StringUtil.isBlank(value.toString())) {
                    setBackground(Color.RED);
                    setText("");
                } else {
                    if (value.toString().matches("\\d{2,4}")) {
                        setBackground(Color.WHITE);
                    } else {
                        setBackground(Color.RED);
                    }
                    setText(value.toString());
                }
            }
        };
        tcrPageWidth.setHorizontalAlignment(0);
        colPageWidth.setCellRenderer(tcrPageWidth);
    }
}
