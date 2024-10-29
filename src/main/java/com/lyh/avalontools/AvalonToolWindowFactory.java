package com.lyh.avalontools;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.lyh.avalontools.pane.HomePane;
import org.jetbrains.annotations.NotNull;

public class AvalonToolWindowFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        Content content = ContentFactory.getInstance().createContent(HomePane.getInstance(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

}