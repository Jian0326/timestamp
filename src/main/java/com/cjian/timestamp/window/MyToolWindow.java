package com.cjian.timestamp.window;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class MyToolWindow {
    //    private JButton buttonOK;
    private JButton transformation1;
    private JPanel myToolWindowContent;
    private JTextField textDate;
    private JTextField timestamp;
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
        long t1 = new BigInteger(textField1.getText(), 10).longValue();
        long t2 = new BigInteger(textField2.getText(), 10).longValue();
        long t3 = Math.abs(t1 - t2);
        long day = t3 / 86400;
        long hour = t3 % 86400 / 3600;
        long min = t3 % 3600 / 60;
        long sec = t3 % 60;
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
        LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        curDate.setText(dateFormat.format(date) + "week(" + dayOfWeek.getValue() + ")");
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
        Date d = new Date(new BigInteger(textTimestamp.getText(), 10).longValue() * 1000L);
        LocalDate localDate = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        date.setText(dateFormat.format(d) + "week(" + dayOfWeek.getValue() + ")");
    }
}
