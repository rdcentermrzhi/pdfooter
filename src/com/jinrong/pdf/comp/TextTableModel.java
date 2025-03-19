package com.jinrong.pdf.comp;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TextTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 2747285340382537401L;

    private static final String[] HEADER = new String[] { "页码", "页脚文本", "页边距-底部", "页面宽度"};

    private List<String[]> data = (List)new ArrayList<String>();

    public int getRowCount() {
        if (this.data == null)
            return 0;
        return this.data.size();
    }

    public int getColumnCount() {
        return HEADER.length;
    }

    public String getColumnName(int columnIndex) {
        return HEADER[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (this.data == null)
            return "";
        return ((String[])this.data.get(rowIndex))[columnIndex];
    }

    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        if (val != null)
            ((String[])this.data.get(rowIndex))[columnIndex] = val.toString();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex != 1);
    }

    public List<String[]> getData() {
        return this.data;
    }

    public void setData(List<String[]> data) {
        if (data == null)
            this.data = (List)new ArrayList<String>();
        this.data = data;
    }
}
