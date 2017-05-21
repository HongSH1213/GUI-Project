package model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import view.EditorPanel;
import view.PropertiesPanel;

public class HFrame extends JPanel{
    private String title;
    private boolean flag;
    private Rectangle info = new Rectangle();
    private EditorPanel editorPanel;
    private PropertiesPanel propertiesPanel;
    public ComponentName countName = new ComponentName();
    public HFrame() {
        super();
        title="";
        flag = false;
        MyMouseListener temp = new MyMouseListener(info,this);
        addMouseListener(temp);
        addMouseMotionListener(temp);
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JComponent temp = (JComponent)e.getSource();
                HFrame frame = (HFrame)editorPanel.getFrame();
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    temp.setBorder(null);
                    frame.repaint();
                    editorPanel.setSelectedItem(null);
                  //properties창 초기화
                }
         }
        });
        
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
    public void setFlag(boolean flag) {
        this.flag = flag;
        info.setSize(0,0);
        repaint();
    }
    public boolean getFlag() {
        return flag;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(flag)
            g.drawRect((int)info.getX(), (int)info.getY(), (int)info.getWidth(), (int)info.getHeight());
    }
    public void setEditorPanel(EditorPanel panel) {
        this.editorPanel = panel;
        propertiesPanel=editorPanel.getProperteisPanel();
    }
    public EditorPanel getEditorPanel() {
        return editorPanel;
    }
    private class MyMouseListener extends MouseAdapter{
        private Rectangle info;
        private Point start = new Point();
//        private PropertiesPanel panel;
        private HFrame me;
        public MyMouseListener(Rectangle info,HFrame it) { //, PropertiesPanel panel
            this.info = info;
            this.me = it;
//            this.panel = panel;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if(!me.getFlag())
                return;
            start.setLocation(e.getX(), e.getY());
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(!me.getFlag())
               return; 
            if(e.getX()<start.getX()&&e.getY()<start.getY()) {
                info.setSize((int)start.getX()-e.getX(),(int)start.getY()-e.getY());
                info.setLocation(e.getX(), e.getY());
            }
            else if(e.getX()>start.getX()&&e.getY()<start.getY()) {
                info.setSize(-(int)start.getX()+e.getX(),(int)start.getY()-e.getY());
                info.setLocation((int)start.getX(), e.getY());
            }
            else if(e.getX()>start.getX()&&e.getY()>start.getY()) {
                info.setSize(-(int)start.getX()+e.getX(),-(int)start.getY()+e.getY());
                info.setLocation((int)start.getX(), (int)start.getY());
            }
            else {
                info.setSize((int)start.getX()-e.getX(),-(int)start.getY()+e.getY());
                info.setLocation(e.getX(), (int)start.getY());
            }
            me.repaint();
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!me.getFlag()) {
                return;
            }
            editorPanel.addItem(info);
            info.setSize(0, 0);
            me.repaint();
            me.setFlag(false);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            JComponent temp =(JComponent)e.getSource();
            JComponent old = editorPanel.getSelectedItem();
            if(old!=null && old!=temp){
                old.setBorder(null);
            }
            editorPanel.setSelectedItem(temp);
            temp.requestFocus();
            temp.setBorder(BorderFactory.createLineBorder(Color.RED));
            
        }
        
        
    }
    
    
}
