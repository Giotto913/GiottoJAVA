package com.giotto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainFrame extends JFrame {
    private int[][] data = new int[][] {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    private int[][] windata = new int[][] {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    private int count = 0 ;

    public static final String IMAGE_PATH = "stone-maze/src/image/";
    public MainFrame() {
       /* initFrame();
        initImage();
        initMenu();
        this.setVisible(true);*/
        //调用一个初始化方法，初始化窗口
        initFrame();
        //初始化菜单
        initMenu();
        //打乱色块顺序
        initRandomArray();
        //生成图片
        initImage();
        //给当前窗口绑定上下左右按键事件
        initKeyPressEvent();

        this.setVisible(true);
    }

    private void initKeyPressEvent() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        switchmove(Direction.up);
                        break;
                    case KeyEvent.VK_DOWN:
                        switchmove(Direction.down);
                        break;
                    case KeyEvent.VK_LEFT:
                        switchmove(Direction.left);
                        break;
                    case KeyEvent.VK_RIGHT:
                        switchmove(Direction.right);
                        break;
                }
            }
        });
    }


    private void switchmove(Direction dir){
        int ind1 = isIndex()[0];
        int ind2 = isIndex()[1];
        switch (dir){
            case up:
                if(ind1 < data.length - 1){
                    int temp = data[ind1][ind2];
                    data[ind1][ind2] = data[ind1+1][ind2];
                    data[ind1+1][ind2] = temp;
                    count ++;
                }
                break;
            case down:
                if(ind1 > 0){
                    int temp = data[ind1][ind2];
                    data[ind1][ind2] = data[ind1-1][ind2];
                    data[ind1-1][ind2] = temp;
                    count ++;
                }
                break;

            case left:
                if(ind2 < data.length-1){
                    int temp = data[ind1][ind2];
                    data[ind1][ind2] = data[ind1][ind2+1];
                    data[ind1][ind2+1] = temp;
                    count ++;
                }
                break;
            case right:
                if(ind2 > 0){
                    int temp = data[ind1][ind2];
                    data[ind1][ind2] = data[ind1][ind2-1];
                    data[ind1][ind2-1] = temp;
                    count ++;
                }
                break;
        }

        initImage();

    }
    private boolean isWin() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != windata[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public MainFrame(int a) {
        initFrame();
        initImage();
        initMenu();
        this.setVisible(true);
    }

    private void initMenu() {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("游戏");
        menuBar.setPreferredSize(new Dimension(200, 35));
        // 菜单黑色背景
        menuBar.setBackground(new Color(0xC0C0C0));
        JMenuItem imageItem = new JMenuItem("打乱顺序");
        imageItem.addActionListener(e -> {
            // 退出程序，销毁窗口即可
            this.dispose();
            new MainFrame();
        });
        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> {
            // 退出程序，销毁窗口即可
            this.dispose();
            new MainFrame(1);
        });


        menu.add(imageItem);
        menu.add(exitItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

    }

    //打乱数字色块的顺序
    private void initRandomArray() {
        int temp = 0;
        int temp1 = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++){

            List<Integer> number = new ArrayList<>();
            number.add(0); number.add(1); number.add(2); number.add(3);
            int flag = random.nextInt(4);
            int a = isIndex()[0];
            int b = isIndex()[1];

            if (flag == 0){ //向上
                if(isIndex()[0] == 0){
                    number.remove(0);
                    flag = random.nextInt(number.size());
                } else{
                    int c = a-1;
                    temp1 = data[c][b];
                    temp = data[a][b];
                    data[a][b]=  temp1;
                    data[c][b] = temp;
                }
            }
            if (flag == 1){ //向右
                if(isIndex()[1] == 3){
                    number.remove(1);
                    flag = random.nextInt(number.size());

                }else{
                    int c = b+1;
                    temp1 = data[a][c];
                    temp = data[a][b];
                    data[a][b]=temp1;
                    data[a][c] = temp;

                }
            }
            if (flag == 2){ //向下
                if(isIndex()[0] == 3){
                    number.remove(2);
                    flag = random.nextInt(number.size());
                }else{
                    int c = a+1;
                    temp1 = data[c][b];
                    temp = data[a][b];
                    data[a][b]=temp1;
                    data[c][b] = temp;

                }
            }
            if (flag == 3){ //向左
                if(isIndex()[1] == 0){
                    number.remove(3);
                    flag = random.nextInt(number.size());
                }else{
                    int c = b-1;
                    temp1 = data[a][c];
                    temp = data[a][b];
                    data[a][b]= temp1;
                    data[a][c] = temp;
                }
            }
        }

    }



    //判断索引
    private int[] isIndex() {
        int[] index = new int[2];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    index[0] = i;
                    index[1] = j;
                    return index;
                }
            }
        }
        return index;
    }

    private void initImage() {
        //删除所有组件
        this.getContentPane().removeAll();

        JLabel countTxt = new JLabel("当前移动"+count+"步");
        countTxt.setBounds(10, 2, 100, 20);
        countTxt.setForeground(Color.blue);
        this.add(countTxt);

        //判断输赢
        if(isWin()){
            JLabel winLabel = new JLabel(new ImageIcon(IMAGE_PATH + "win.png"));
            winLabel.setBounds(90, 200, 266, 88);
            this.add(winLabel);
        }

       for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                String imageName = data[i][j] + ".png";
                JLabel jLabel = new JLabel();
                jLabel.setIcon( new ImageIcon(IMAGE_PATH+imageName));
                // 设置图片标签的位置
                jLabel.setBounds(20 + j * 100, 70 + i * 100, 100, 100);
                this.add(jLabel);
            }
       }
       JLabel background = new JLabel(new ImageIcon(IMAGE_PATH+"background.png"));
       background.setBounds(0, 10, 450, 484);
       this.add(background);

        //刷新图层
        this.repaint();

    }

    private void initFrame() {
        this.setTitle("数字迷阵 v1.0");
        this.setSize(465, 580);
        this.setLocationRelativeTo( null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout( null);

    }

}
