package com.jinrong.pdf.comp;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel {
    private static final long serialVersionUID = -6635576166035192961L;

    public static String MSG = "";

    private static MessagePanel messagePanel = new MessagePanel();

    private JLabel lblMessage = new JLabel();

    private MessagePanel() {
        add(this.lblMessage);
    }

    public static MessagePanel getInstance() {
        return messagePanel;
    }

    public void setMessage(String msg, boolean success) {
        this.lblMessage.setForeground(success ? Color.BLUE : Color.RED);
        this.lblMessage.setText(msg);
    }

    public void setMessage(String msg) {
        this.lblMessage.setForeground(Color.BLACK);
        this.lblMessage.setText(msg);
    }
}
