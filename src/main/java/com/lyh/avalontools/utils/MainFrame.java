package com.lyh.avalontools.utils;


import com.lyh.avalontools.pane.JSONPane;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 主框架类，包含JSON编辑器的主要界面
 */
public class MainFrame extends JFrame {

    private JTextArea inputArea;
    private JTree jsonTree;
    private DefaultTreeModel treeModel;
    private JTextArea outputArea;
    private JButton formatButton;
    private JButton viewButton;
    private JButton clearButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton createJsonButton;

    public MainFrame() throws IOException {
        setTitle("数仓工具箱");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        add(JSONPane.getInstance());
        initComponents();
        setLayout(new BorderLayout());

        // 添加组件到框架
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(inputArea), new JScrollPane(jsonTree));
        splitPane.setResizeWeight(0.5);
        JSplitPane res  = new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitPane,new JScrollPane(outputArea));
        res.setResizeWeight(0.6);
        add(res,BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.NORTH);
    }

    /**
     * 初始化组件
     */
    private void initComponents() {
        inputArea = new JTextArea(20, 40);
                outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        jsonTree = new JTree(new DefaultMutableTreeNode("JSON"));
        treeModel = (DefaultTreeModel) jsonTree.getModel();

        formatButton = new JButton("Format JSON");
        viewButton = new JButton("View JSON as Tree");
        clearButton = new JButton("Clear");
        addButton = new JButton("Add Node");
        deleteButton = new JButton("Delete Node");
        createJsonButton = new JButton("Create JSON");

        // 设置按钮动作监听器
        formatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputArea.getText();
                String formattedJson = JsonFormatter.formatJson(input);
                outputArea.setText(formattedJson);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputArea.getText();
                DefaultMutableTreeNode root = JsonTreeViewer.parseJsonToTree(input);
                if (root != null) {
                    treeModel.setRoot(root);
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Invalid JSON", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputArea.setText("");
                outputArea.setText("");
                treeModel.setRoot(new DefaultMutableTreeNode("JSON"));
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jsonTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    String nodeName = JOptionPane.showInputDialog("Enter node name:");
                    if (nodeName != null) {
                        selectedNode.add(new DefaultMutableTreeNode(nodeName));
                        treeModel.reload();
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jsonTree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getParent() != null) {
                    treeModel.removeNodeFromParent(selectedNode);
                }
            }
        });

        createJsonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
                String json = JsonTreeViewer.treeToJson(root);
                outputArea.setText(json);
            }
        });
    }

    /**
     * 创建按钮面板
     * @return JPanel 包含所有操作按钮
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(formatButton);
        panel.add(viewButton);
        panel.add(clearButton);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(createJsonButton);
        return panel;
    }

    /**
     * 主方法，启动应用程序
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame().setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
