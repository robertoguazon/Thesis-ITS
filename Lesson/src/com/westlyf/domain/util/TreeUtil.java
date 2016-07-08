package com.westlyf.domain.util;

import com.westlyf.domain.lesson.Level;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 07/06/2016.
 */
public class TreeUtil {

    public static TreeItem createTree(Level root) {
       TreeItem<Level> rootItem = createNode(root);

        if (root.hasSubLevels()) {
            ArrayList<Level> subLevels = root.getSubLevels();

            while(!subLevels.isEmpty()) {
                rootItem.getChildren().add(createTree(subLevels.get(0)));
                subLevels.remove(0);
            }
        }

        return rootItem;
    }

    public static TreeItem createNode(Level level) {
        TreeItem<Level> levelTreeItem;
        if (level.hasGraphics() && level.isUnlocked()) {
           levelTreeItem = new TreeItem(level,level.getGraphics());
        } else {
            levelTreeItem = new TreeItem(level);
        }

        return levelTreeItem;
    }
}
