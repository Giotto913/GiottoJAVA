package com.giotto.ui;

import com.giotto.bean.User;
import com.giotto.bean.UserSerive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame implements ActionListener{

    private JTextField usernameField;      // 用户名
    private JTextField loginNameField;     // 登录名
    private JPasswordField passwordField;  // 密码
    private JPasswordField confirmPasswordField; // 确认密码
    private JButton registerButton;
    private JButton resetButton;

    public RegisterForm() {
        this.setTitle("用户注册");
        this.setSize(450, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // 居中显示
        initUI();
    }
    private void initUI() {
        // 设置全局字体为楷体
        Font font = new Font("楷体", Font.PLAIN, 16);

        // 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // 表单面板
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 1, 10, 10));
        formPanel.setBackground(new Color(245, 245, 245));

        // 用户名
        JLabel usernameLabel = new JLabel("用户名：");
        usernameLabel.setFont(font);
        usernameField = new JTextField();
        usernameField.setFont(font);
        usernameField.setPreferredSize(new Dimension(200, 30));

        // 登录名
        JLabel loginNameLabel = new JLabel("登录名：");
        loginNameLabel.setFont(font);
        loginNameField = new JTextField();
        loginNameField.setFont(font);
        loginNameField.setPreferredSize(new Dimension(200, 30));

        // 密码
        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setFont(font);
        passwordField = new JPasswordField();
        passwordField.setFont(font);
        passwordField.setEchoChar('*'); // 默认隐藏密码符号
        passwordField.setPreferredSize(new Dimension(200, 30));

        // 确认密码
        JLabel confirmPasswordLabel = new JLabel("确认密码：");
        confirmPasswordLabel.setFont(font);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(font);
        confirmPasswordField.setEchoChar('*'); // 默认隐藏密码符号
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));

        // 注册按钮
        registerButton = new JButton("注册");
        registerButton.setFont(font);
        registerButton.setBackground(new Color(0, 150, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(this);

        // 重置按钮
        resetButton = new JButton("重置");
        resetButton.setFont(font);
        resetButton.setBackground(new Color(200, 200, 200));
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(this);
        // 添加组件到表单面板
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(loginNameLabel);
        formPanel.add(loginNameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(registerButton);
        buttonPanel.add(resetButton);

        // 添加事件监听器
        // registerButton.addActionListener(e -> registerUser());
        // resetButton.addActionListener(e -> resetForm());

        // 组装主界面
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        this.setVisible(true);
    }


    // 注册逻辑
    private void registerUser() {
        String username = usernameField.getText().trim();
        String loginName = loginNameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || loginName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 模拟注册成功
        // 创建用户对象并加入 LoginFrame 的 allUsers 集合
        User newUser = new User(username, password, loginName);
        if (UserSerive.isExist(loginName)) {
            JOptionPane.showMessageDialog(this, "登录名已存在！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        UserSerive.addUser(newUser); // 使用封装方法添加
        JOptionPane.showMessageDialog(this, "注册成功！\n用户名：" + username + "\n登录名：" + loginName, "成功", JOptionPane.INFORMATION_MESSAGE);
        new LoginFrame();
        this.dispose();
    }

    // 重置表单
    private void resetForm() {
        usernameField.setText("");
        loginNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterForm().setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if(btn == registerButton){
            registerUser();
        }else{
            resetForm();
        }
    }
}