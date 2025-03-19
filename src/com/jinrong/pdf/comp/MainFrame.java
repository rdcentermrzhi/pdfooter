package com.jinrong.pdf.comp;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 5908523041890715440L;

    private static final int INIT_WIDTH = 800;

    private static final int INIT_HEIGHT = 600;

    private TextTableModel tableModel = new TextTableModel();

    public MainFrame() {
        setTitle("PDF Footer Tool");
        setDefaultCloseOperation(3);
        Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((dimScreen.width - 800) / 2, (dimScreen.height - 600) / 2, 800, 600);
        init();
    }

    private void init() {
        add(new CriteriaPanel(this), "North");
        JTable tblFooterText = new TextTable(this.tableModel);
        JScrollPane scrollPane = new JScrollPane(tblFooterText);
        add(scrollPane, "Center");
        add(MessagePanel.getInstance(), "South");
        MessagePanel.getInstance().setMessage("Hi, 请选择Excel文件和PDF文件");
    }

    public TextTableModel getTableModel() {
        return this.tableModel;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
