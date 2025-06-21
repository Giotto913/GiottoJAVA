package com.giotto.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class EmployeeManagerUI extends JFrame {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nametextFieldSearch;


    public EmployeeManagerUI(String  username){
        frame = this;
        initialize();
        this.setVisible(true);
        this.setTitle(username+"的登录界面");
    }
    public EmployeeManagerUI() {  }

    private void initialize() {
        this.setBounds(100, 100, 800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        // 输入框和搜索/添加按钮
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        nametextFieldSearch = new JTextField(20);
        JButton btnSearch = new JButton("搜索");
        JButton btnAdd = new JButton("添加");

        topPanel.add(new JLabel("姓名："));
        topPanel.add(nametextFieldSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnAdd);

        // 表格模型
        model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "姓名", "性别", "年龄", "电话", "职位", "入职时间", "薪水", "部门信息"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有单元格不可编辑
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(30);

        // 初始化数据（20行）
       for (int i = 0; i < 20; i++) {
            model.addRow(new Object[]{
                    i + 1,                // ID
                    "员工" + (i + 1),       // 姓名
                    i % 2 == 0 ? "男" : "女", // 性别
                    25 + i % 10,            // 年龄
                    "1380013800" + (i % 10),// 电话
                    "职位" + (i + 1),       // 职位
                    "202" + (i % 5) + "-01-01", // 入职时间（如 2020-01-01）
                    8000 + i * 100,         // 薪水
                    "部门" + ((i % 4) + 1)   // 部门信息（部门1~4循环）
            });
        }

        // 底部按钮面板
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnEdit = new JButton("修改");
        JButton btnDelete = new JButton("删除");
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        // 右键菜单
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("编辑");
        JMenuItem deleteItem = new JMenuItem("删除");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        // 鼠标监听器用于右键菜单
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        // 封装统一的编辑方法
        ActionListener editAction = e -> editSelectedRow();

        // 绑定事件到右键菜单项
        editItem.addActionListener(editAction);
        btnEdit.addActionListener(editAction);

        // 删除菜单项事件
        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Integer id = (Integer) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "确定要删除 ID 为 " + id + " 的员工吗？",
                        "删除确认",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                    for (int i = 0; i < model.getRowCount(); i++) {
                        model.setValueAt(i + 1, i, 0); // 第 0 列是 ID
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "请先选择一行数据！");
            }
        });

        // 删除按钮事件
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Integer id = (Integer) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "确定要删除 ID 为 " + id + " 的员工吗？",
                        "删除确认",
                        JOptionPane.YES_NO_OPTION

                );
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "请先选择一行数据！");
            }
        });

        // 搜索按钮事件
        btnSearch.addActionListener(e -> {
            String keyword = nametextFieldSearch.getText().trim();
// 获取当前表格的所有列名
            Object[] columnNames = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                columnNames[i] = model.getColumnName(i);
            }

            DefaultTableModel filteredModel = new DefaultTableModel(new Object[][]{}, columnNames);
            for (int i = 0; i < model.getRowCount(); i++) {
                String name = (String) model.getValueAt(i, 1); // 姓名在第2列
                if (name != null && name.contains(keyword)) {
                    Object[] rowData = new Object[model.getColumnCount()];
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        rowData[j] = model.getValueAt(i, j);
                    }
                    filteredModel.addRow(rowData);
                }
            }
            table.setModel(filteredModel);
        });

        // 添加按钮事件
        btnAdd.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JComboBox<String> genderBox = new JComboBox<>(new String[]{"男", "女"});
            JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 60, 1));
            JTextField phoneField = new JTextField();
            JTextField posField = new JTextField();
            JTextField hireDateField = new JTextField("2025-01-01");
            JTextField salaryField = new JTextField("8000");
            JTextField deptField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("姓名:"));
            panel.add(nameField);
            panel.add(new JLabel("性别:"));
            panel.add(genderBox);
            panel.add(new JLabel("年龄:"));
            panel.add(ageSpinner);
            panel.add(new JLabel("电话:"));
            panel.add(phoneField);
            panel.add(new JLabel("职位:"));
            panel.add(posField);
            panel.add(new JLabel("入职时间:"));
            panel.add(hireDateField);
            panel.add(new JLabel("薪水:"));
            panel.add(salaryField);
            panel.add(new JLabel("部门信息:"));
            panel.add(deptField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "添加员工", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int newId = model.getRowCount() + 1;
                    model.addRow(new Object[]{
                            newId,
                            nameField.getText(),
                            genderBox.getSelectedItem(),
                            ageSpinner.getValue(),
                            phoneField.getText(),
                            posField.getText(),
                            hireDateField.getText(),
                            Integer.parseInt(salaryField.getText().trim()),
                            deptField.getText()
                    });
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的数字作为薪水！", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 添加组件到界面
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    // 统一编辑方法
    private void editSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            // 获取当前行数据
            Integer id = (Integer) model.getValueAt(selectedRow, 0);
            String name = (String) model.getValueAt(selectedRow, 1);
            String gender = (String) model.getValueAt(selectedRow, 2);
            Integer age = (Integer) model.getValueAt(selectedRow, 3);
            String phone = (String) model.getValueAt(selectedRow, 4);
            String position = (String) model.getValueAt(selectedRow, 5);
            String hireDate = (String) model.getValueAt(selectedRow, 6);
            Integer salary = (Integer) model.getValueAt(selectedRow, 7);
            String department = (String) model.getValueAt(selectedRow, 8);

            JTextField nameField = new JTextField(name);
            JComboBox<String> genderBox = new JComboBox<>(new String[]{"男", "女"});
            genderBox.setSelectedItem(gender);
            JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(age != null ? age : 25, 18, 60, 1));
            JTextField phoneField = new JTextField(phone);
            JTextField posField = new JTextField(position);
            JTextField hireDateField = new JTextField(hireDate != null ? hireDate : "2025-01-01");
            JTextField salaryField = new JTextField(salary != null ? salary.toString() : "8000");
            JTextField deptField = new JTextField(department);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("姓名:"));
            panel.add(nameField);
            panel.add(new JLabel("性别:"));
            panel.add(genderBox);
            panel.add(new JLabel("年龄:"));
            panel.add(ageSpinner);
            panel.add(new JLabel("电话:"));
            panel.add(phoneField);
            panel.add(new JLabel("职位:"));
            panel.add(posField);
            panel.add(new JLabel("入职时间:"));
            panel.add(hireDateField);
            panel.add(new JLabel("薪水:"));
            panel.add(salaryField);
            panel.add(new JLabel("部门信息:"));
            panel.add(deptField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "编辑员工", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    model.setValueAt(nameField.getText(), selectedRow, 1);
                    model.setValueAt(genderBox.getSelectedItem(), selectedRow, 2);
                    model.setValueAt(ageSpinner.getValue(), selectedRow, 3);
                    model.setValueAt(phoneField.getText(), selectedRow, 4);
                    model.setValueAt(posField.getText(), selectedRow, 5);
                    model.setValueAt(hireDateField.getText(), selectedRow, 6);
                    model.setValueAt(Integer.parseInt(salaryField.getText()), selectedRow, 7);
                    model.setValueAt(deptField.getText(), selectedRow, 8);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的数字作为薪水！", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "请先选择一行数据！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}