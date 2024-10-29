package com.lyh.avalontools.utils;

import com.google.gson.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Map;
import java.util.Set;

/**
 * JSON 树形视图工具类
 */
public class JsonTreeViewer {
    /**
     * 将JSON字符串解析为树形结构
     * @param json 输入的JSON字符串
     * @return DefaultMutableTreeNode 根节点
     */
    public static DefaultMutableTreeNode parseJsonToTree(String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return buildTreeNode(null, jsonElement);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    private static DefaultMutableTreeNode buildTreeNode(String key, JsonElement element) {
        DefaultMutableTreeNode node;
        if (key != null) {
            node = new DefaultMutableTreeNode(key);
        } else {
            node = new DefaultMutableTreeNode("JSON");
        }

        if (element.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entrySet = element.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                node.add(buildTreeNode(entry.getKey(), entry.getValue()));
            }
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                node.add(buildTreeNode("[" + i + "]", array.get(i)));
            }
        } else {
            node.add(new DefaultMutableTreeNode(element.toString()));
        }

        return node;
    }

    /**
     * 将树形结构转化为JSON字符串
     * @param root 根节点
     * @return JSON字符串
     */
    public static String treeToJson(DefaultMutableTreeNode root) {
        JsonObject jsonObject = new JsonObject();
        buildJsonFromTree(jsonObject, root);
        return jsonObject.toString();
    }

    private static void buildJsonFromTree(JsonObject jsonObject, DefaultMutableTreeNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            String nodeName = childNode.toString();
            if (childNode.getChildCount() > 0) {
                JsonObject childJsonObject = new JsonObject();
                buildJsonFromTree(childJsonObject, childNode);
                jsonObject.add(nodeName,childJsonObject);
                System.out.println(childJsonObject.toString() + "has child");
            }
            if (childNode.getChildCount() == 1){
                jsonObject.addProperty(nodeName,childNode.getChildAt(0).toString());
            }
        }
    }
}
