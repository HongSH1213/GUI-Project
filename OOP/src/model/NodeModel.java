package model;

public class NodeModel extends ModelInfo{
    private NodeModel sibling;
    private NodeModel child;
    
    public static final String JFrame = "JFrame";
    public static final String JPanel = "JPanel";
    public static final String JButton = "JButton";
    public static final String JLabel = "JLabel";
    
    public boolean isSiblingNull() {
        return  sibling == null;
    }
    public boolean isChildNull() {
        return  child == null;
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
    
}
