package model;

public class NodeModel extends ModelInfo {

    public boolean isSiblingNull() {
        return sibling == null;
    }

    public boolean isChildNull() {
        return child == null;
    }

    public void setSibling(NodeModel nd) {
        sibling = nd;
    }

    public void setChild(NodeModel nd) {
        child = nd;
    }

    public NodeModel getSibling() {
        return sibling;
    }

    public NodeModel getChild() {
        return child;
    }

    private NodeModel sibling;
    private NodeModel child;
    
}
