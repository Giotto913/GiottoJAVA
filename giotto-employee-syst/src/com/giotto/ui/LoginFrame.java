package com.giotto.ui;

import com.giotto.bean.User;
import com.giotto.bean.UserSerive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class LoginFrame extends JFrame implements ActionListener {
    private JTextField loginNameField;// 用户名输入框
    private JPasswordField passwordField;// 密码输入框
    private JCheckBox showPassword;// 显示密码复选框
    private JButton loginButton;
    private JButton registerButton;


    public LoginFrame() {
        // 设置窗口标题
        this.setTitle("登录界面");
        this.setSize(400, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // 居中显示
        createComponents();
    }

    private void createComponents() {
        // 创建主面板并设置布局和背景颜色
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // 浅蓝色背景
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 组件间距

        // 设置全局字体为楷体
        Font kaiFont = new Font("楷体", Font.PLAIN, 14);

        // 标题标签
        JLabel titleLabel = new JLabel("Giotto控制管理系统");
        titleLabel.setFont(new Font("楷体", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // 用户名标签与输入框
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setFont(kaiFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(userLabel, gbc);

        loginNameField = new JTextField(20);
        loginNameField.setPreferredSize(new Dimension(200, 30));
        loginNameField.setFont(kaiFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(loginNameField, gbc);

        // 密码标签与输入框
        JLabel passwordLabel = new JLabel("密   码:");
        passwordLabel.setFont(kaiFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setEchoChar('*'); // 默认隐藏密码符号
        passwordField.setFont(kaiFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        // 显示密码复选框
        showPassword = new JCheckBox("显示密码");
        showPassword.setFont(kaiFont);
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0); // 显示密码
            } else {
                passwordField.setEchoChar('*'); // 隐藏密码
            }
        });
        // 添加到登录面板
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(showPassword, gbc);

        // 按钮面板：登录 + 注册，水平排列并居中显示
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false); // 背景透明

        loginButton = new JButton("登录");
        loginButton.setFont(new Font("楷体", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(90, 30));
        //监听器
        loginButton.addActionListener(this);


        registerButton = new JButton("注册");
        registerButton.setFont(new Font("楷体", Font.BOLD, 14));
        registerButton.setBackground(new Color(51, 204, 51));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(90, 30));
        registerButton.addActionListener(this);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // 添加按钮面板到主布局
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        // 添加面板到窗口
        this.add(panel);

        // 显示窗口
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if(btn == loginButton){
            login();

        }else{
            new RegisterForm();
            this.dispose();
        }
    }


    private void login() {
        String loginName = loginNameField.getText();
        String password = new String(passwordField.getPassword());
        User user = UserSerive.getUserByLoginName(loginName);
        if(user != null){
            if(user.getPassword().equals(password)){
                JOptionPane.showMessageDialog(this, "用户"+user.getUsername()+"登录成功");
                new EmployeeManagerUI(user.getUsername());
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this,"密码错误");
            }
        }else{
            JOptionPane.showMessageDialog(this,"用户不存在");
        }

    }
        // 根据登录名获取用户

}