package com.cjian.timestamp.window;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyToolWindow {
    //    private JButton buttonOK;
    private JButton transformation1;
    private JPanel myToolWindowContent;
    private JTextField textDate;
    private JLabel timestamp;
    private JLabel curDate;

    private JButton transformation2;
    private JTextField textTimestamp;
    private JLabel date;
    private JTextField textField1;
    private JTextField textField2;
    private JButton calculationButton;

    private JLabel result;

    public MyToolWindow(ToolWindow toolWindow) {
        transformation1.addActionListener(e -> {
            try {
                timeToTimestamp();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        transformation2.addActionListener(e -> {
            timestampToTime();
        });
        calculationButton.addActionListener(e -> {
            calculationResult();
        });
        transformation1.setText("timeToTimestamp");
        transformation2.setText("timestampToTime");
        this.initUi();
    }

    private void calculationResult() {
        if (textField1.getText().length() < 10 || textField2.getText().length() < 10) return;
        int t1 = Integer.parseInt(textField1.getText());
        int t2 = Integer.parseInt(textField2.getText());
        int t3 = Math.abs(t1 - t2);
        int day = t3 / 86400;
        int hour = t3 % 86400 / 3600;
        int min = t3 % 3600 / 60;
        int sec = t3 % 60;
        result.setText(String.format("%d[day] %d:%d:%d", day, hour, min, sec));
    }

    private void initUi() {
        new Timer(1, (e -> currentDateTime())).start();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        textDate.setText(dateFormat.format(d));
        date.setText(dateFormat.format(d));

        String str = String.valueOf(d.getTime() / 1000);
        timestamp.setText(str);
        textTimestamp.setText(str);
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }

    private void currentDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        curDate.setText(dateFormat.format(date));
    }

    // Time to timestamp
    private void timeToTimestamp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(textDate.getText());
        timestamp.setText(String.valueOf(date.getTime() / 1000));
    }

    // timestamp to time
    private void timestampToTime() {
        if (textTimestamp.getText().length() < 10) return;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(Integer.parseInt(textTimestamp.getText()) * 1000L);
        date.setText(dateFormat.format(d));
    }
}
