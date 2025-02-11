package stationtocommand.view.mainStructure;

import javafx.scene.control.TreeItem;

public class CustomTreeItem<T> extends TreeItem<T> {
    private final TreeItemType type;
    private final Object object;

    public CustomTreeItem(TreeItemType type, Object object, T value) {
        super(value);
        this.type = type;
        this.object = object;
    }

    public TreeItemType getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

}
