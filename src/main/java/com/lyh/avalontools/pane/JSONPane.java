package com.lyh.avalontools.pane;

import com.lyh.avalontools.utils.JsonFormatter;
import com.lyh.avalontools.utils.JsonTreeViewer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * json 工具面板
 */
public class JSONPane extends JPanel{

    private static volatile JSONPane instance = null;

    private JSONPane(){

    }

    private static JTextArea inputArea;
    private static JTree jsonTree;
    private static DefaultTreeModel treeModel;
    private static JTextArea outputArea;
    private static JButton formatButton;
    private static JButton viewButton;
    private static JButton clearButton;
    private static JButton addButton;
    private static JButton deleteButton;
    private static JButton createJsonButton;

    public static JSONPane getInstance(){
        if (instance == null){
            synchronized (JSONPane.class){
                if (instance == null){
                    instance = new JSONPane();
                    instance.setLayout(new BorderLayout());
                    initComponents();
                    instance.add(createButtonPanel(),BorderLayout.NORTH);
                    instance.add(createCenterPane(),BorderLayout.CENTER);
                }
            }
        }
        return instance;
    }

    /**
     * 初始化组件
     */
    private static void initComponents() {
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
                    JOptionPane.showMessageDialog(instance.getParent(), "Invalid JSON", "Error", JOptionPane.ERROR_MESSAGE);
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
     *
     */
    private static JSplitPane createCenterPane(){
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(inputArea), new JScrollPane(jsonTree));
        splitPane.setResizeWeight(0.5);
        JSplitPane res  = new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitPane,new JScrollPane(outputArea));
        res.setResizeWeight(0.6f);
        return res;
    }

    /**
     * 创建按钮面板
     * @return JPanel 包含所有操作按钮
     */
    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(formatButton);
        panel.add(viewButton);
        panel.add(clearButton);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(createJsonButton);
        return panel;
    }
}
