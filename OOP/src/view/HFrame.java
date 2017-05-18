package view;
import javax.swing.*;

public class HFrame extends JPanel{
    private String title;
    public HFrame() {
        super();
        title="";
    }
    public HFrame(String title) {
        super();
        this.title=title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
