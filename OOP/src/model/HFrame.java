package model;

import java.awt.Color;
import java.awt.Cursor;
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

@SuppressWarnings("serial")
public class HFrame extends JPanel {

    public HFrame() {
        super();
        title = "";
        flag = false;
        MyMouseListener temp = new MyMouseListener(info, this);
        addMouseListener(temp);
        addMouseMotionListener(temp);
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JComponent temp = (JComponent) e.getSource();
                HFrame frame = (HFrame) editorPanel.getFrame();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    temp.setBorder(null);
                    if(temp.getClass().getSimpleName().equals("JButton"))
                        temp.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    temp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    frame.repaint();
                    editorPanel.setSelectedItem(null);
                    // properties창 초기화
                    propertiesPanel.setProperties(null);
                }
            }
        });

    }

    public HFrame(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        info.setSize(0, 0);
        repaint();
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (flag)
            g.drawRect((int) info.getX(), (int) info.getY(), (int) info.getWidth(), (int) info.getHeight());
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        editorPanel.getBackgroundPanel().setSize(width + 4, height + 4);
    }

    public void setEditorPanel(EditorPanel panel) {
        this.editorPanel = panel;
        propertiesPanel = editorPanel.getProperteisPanel();
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    private class MyMouseListener extends MouseAdapter {
        private Rectangle info;
        private Point start = new Point();
        private HFrame me;
        private HFrame src;
        private final Cursor SE = new Cursor(Cursor.SE_RESIZE_CURSOR);
        private final Cursor S = new Cursor(Cursor.S_RESIZE_CURSOR);
        private final Cursor E = new Cursor(Cursor.E_RESIZE_CURSOR);
        private final Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        private int width;
        private int height;
        private int x;
        private int y;
        private int state;

        public MyMouseListener(Rectangle info, HFrame it) {
            this.info = info;
            this.me = it;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (editorPanel.getSelectedItem() != (JComponent) e.getSource()){
                ((JComponent) e.getSource()).setCursor(defaultCursor);
                return;
            }
            src = (HFrame) e.getSource();
            width = src.getWidth();
            height = src.getHeight();
            x = e.getX();
            y = e.getY();
            if ((x <= width && x >= width - 3) && (y <= height && y >= height - 3)) {
                src.setCursor(SE);
            } else if ((x <= width && x >= width - 3) && (y >= 0 && y < height - 3)) {
                src.setCursor(E);
            } else if ((x >= 0 && x < width - 3) && (y <= height && y >= height - 3)) {
                src.setCursor(S);
            } else {
                src.setCursor(defaultCursor);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            start.setLocation((double) e.getX(), (double) e.getY());
            if (editorPanel.getSelectedItem() != (JComponent) e.getSource())
                return;
            src = (HFrame) e.getSource();
            width = src.getWidth();
            height = src.getHeight();
            info.setBounds(src.getBounds());
            x = (int) start.getX();
            y = (int) start.getY();
            if ((x <= width && x >= width - 3) && (y <= height && y >= height - 3)) {
                // 오른쪽 아래
                state = 1;
            } else if ((x <= width && x >= width - 3) && (y >= 0 && y < height - 3)) {
                // 오른쪽
                state = 2;
            } else if ((x >= 0 && x < width - 3) && (y <= height && y >= height - 3)) {
                // 아래
                state = 3;
            }
            else {
                state = 0;
            }

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (editorPanel.getSelectedItem() == (JComponent) e.getSource() && me.getFlag() == false) {
                if(state == 0)
                    return;
                int tempX;
                int tempY;
                src = (HFrame) e.getSource();
                width = src.getWidth();
                height = src.getHeight();
                x = (int) start.getX();
                y = (int) start.getY();
                if (state == 1) {
                    // 오른쪽 아래
                    tempX = e.getX() - x;
                    tempY = e.getY() - y;
                    if (tempX + (int) info.getWidth() < 0)
                        tempX = -(int) info.getWidth();
                    if (tempY + (int) info.getHeight() < 0)
                        tempY = -(int) info.getHeight();
                    src.setSize((int) info.getWidth() + tempX, (int) info.getHeight() + tempY);
                } else if (state == 2) {
                    // 오른쪽
                    tempX = e.getX() - x;
                    if (tempX + (int) info.getWidth() < 0)
                        tempX = -(int) info.getWidth();
                    src.setSize((int) info.getWidth() + tempX, height);
                } else if (state == 3) {
                    // 아래
                    tempY = e.getY() - y;
                    if (tempY + (int) info.getHeight() < 0)
                        tempY = -(int) info.getHeight();
                    src.setSize(width, (int) info.getHeight() + tempY);
                }
                propertiesPanel.setProperties(src);
            }

            if (!me.getFlag())
                return;
            if (e.getX() < start.getX() && e.getY() < start.getY()) {
                info.setSize((int) start.getX() - e.getX(), (int) start.getY() - e.getY());
                info.setLocation(e.getX(), e.getY());
            } else if (e.getX() > start.getX() && e.getY() < start.getY()) {
                info.setSize(-(int) start.getX() + e.getX(), (int) start.getY() - e.getY());
                info.setLocation((int) start.getX(), e.getY());
            } else if (e.getX() > start.getX() && e.getY() > start.getY()) {
                info.setSize(-(int) start.getX() + e.getX(), -(int) start.getY() + e.getY());
                info.setLocation((int) start.getX(), (int) start.getY());
            } else {
                info.setSize((int) start.getX() - e.getX(), -(int) start.getY() + e.getY());
                info.setLocation(e.getX(), (int) start.getY());
            }
            me.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!me.getFlag()) {
                return;
            }
            editorPanel.addItem(info);
            info.setSize(0, 0);
            me.repaint();
            me.setFlag(false);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JComponent temp = (JComponent) e.getSource();
            JComponent old = editorPanel.getSelectedItem();
            if (old != null && old != temp) {
                old.setBorder(null);
                if(old.getClass().getSimpleName().equals("JButton"))
                    old.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                propertiesPanel.setProperties(null);
            }
            editorPanel.setSelectedItem(temp);
            temp.requestFocus();
            temp.setBorder(BorderFactory.createLineBorder(Color.RED));
            propertiesPanel.setProperties(temp);
        }

    }
    
    private String title;
    private boolean flag;
    private Rectangle info = new Rectangle();
    private EditorPanel editorPanel;
    private PropertiesPanel propertiesPanel;
    public ComponentName countName = new ComponentName();

}
