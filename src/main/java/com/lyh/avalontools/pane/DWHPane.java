package com.lyh.avalontools.pane;

import javax.swing.*;
import java.awt.*;

/**
 * 数仓工具面板
 */
public class DWHPane extends JPanel {

    private static volatile DWHPane instance = null;

    private DWHPane(){

    }

    public static DWHPane getInstance(){
        if (instance == null){
            synchronized (DWHPane.class){
                if (instance == null){
                    instance = new DWHPane();
                    instance.add(new Label("正在开发中..."),BorderLayout.CENTER);
                }
            }
        }
        return instance;
    }


}
