package model;

public class ComponentName {
    private int frameCnt=0;
    private int buttonCnt=0;
    private int labelCnt=0;
    private int panelCnt=0;
    
    public int getFrameCnt() {
        return ++frameCnt;
    }
    public int getButtonCnt() {
        return ++buttonCnt;
    }
    public int getLabelCnt() {
        return ++labelCnt;
    }
    public int getPanelCnt() {
        return ++panelCnt;
    }
    
}
