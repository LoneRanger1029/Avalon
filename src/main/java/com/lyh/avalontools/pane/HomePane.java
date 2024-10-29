package com.lyh.avalontools.pane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 插件的主页面板
 */
public class HomePane extends JTabbedPane {

    private static HomePane instance;

    private final static JPanel homePane = new JPanel();
    private final static JPanel jsonPane = JSONPane.getInstance();
    private final static JPanel dwhPane = DWHPane.getInstance();

    private HomePane(){

    }

    public static HomePane getInstance(){
        if (instance == null){
            synchronized (HomePane.class){
                instance = new HomePane();
                initHomePane();
                instance.addTab("首页",null,homePane);
            }
        }
        return instance;
    }

    private static void initHomePane(){
        homePane.setLayout(new BoxLayout(homePane,BoxLayout.Y_AXIS));

        JButton jsonButton = new JButton("json 格式化工具");
        JButton dwhButton = new JButton("数仓工具箱");

        BufferedImage jsonImg,dwhImg;
        try {
            jsonImg = ImageIO.read(HomePane.class.getResource("/ui/json-xml.png"));
            dwhImg = ImageIO.read(HomePane.class.getResource("/ui/dwh.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jsonButton.setIcon(new ImageIcon(jsonImg));
        dwhButton.setIcon(new ImageIcon(dwhImg));

        jsonButton.addActionListener(e -> {
            if (instance.indexOfComponent(jsonPane) == -1){ // 没有添加该 tab
                instance.addTab(jsonButton.getText(),new ImageIcon(jsonImg),jsonPane);
            }
            instance.setSelectedComponent(jsonPane);
        });

        dwhButton.addActionListener(e -> {
            if (instance.indexOfComponent(dwhButton) == -1) {
                instance.addTab(dwhButton.getText(), new ImageIcon(dwhImg), dwhPane);
            }
            instance.setSelectedComponent(dwhPane);
        });

        homePane.add(jsonButton);
        homePane.add(dwhButton);
    }

}
