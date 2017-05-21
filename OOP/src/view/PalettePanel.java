package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PalettePanel extends JPanel {
    public PalettePanel() {
        setLayout(null);
        backGroundColor = new Color(240,240,240);
        chooseColor = new Color(216,230,242);
        selectedColor = new Color(192, 220, 243);
        defaultFont = new Font("Nirmala UI Semilight",Font.PLAIN,20);
        myListener = new MyMouseListener();
        setBackground(backGroundColor);
        
        titleContainers = new JLabel("Swing Containers");
        titleContainers.setBounds(10,5,300,15);
        titleContainers.setFont(new Font("Nirmala UI Semilight",Font.PLAIN,15));
        titleContainers.setOpaque(true);
        titleContainers.setBackground(new Color(213,213,213));
                
        jPanel = new JLabel("JPanel");
        jPanel.setFont(defaultFont);
        jPanel.setBounds(0,26,150, 20);
        jPanel.setOpaque(true);
        jPanel.setBackground(backGroundColor);
        jPanel.addMouseListener(myListener);
        
        
        titleContainers2 = new JLabel("Swing Controls");
        titleContainers2.setBounds(10,110,300,15);
        titleContainers2.setFont(new Font("Nirmala UI Semilight",Font.PLAIN,15));
        titleContainers2.setOpaque(true);
        titleContainers2.setBackground(new Color(213,213,213));
        
        jButton = new JLabel("JButton");
        jButton.setFont(defaultFont);
        jButton.setBounds(0,131,150, 20);
        jButton.setOpaque(true);
        jButton.setBackground(backGroundColor);
        jButton.addMouseListener(myListener);
        
        jLabel = new JLabel("JLabel");
        jLabel.setFont(defaultFont);
        jLabel.setBounds(151,131,150, 20);
        jLabel.setOpaque(true);
        jLabel.setBackground(backGroundColor);
        jLabel.addMouseListener(myListener);
        
        
        
        add(titleContainers);
        add(titleContainers2);
        add(jPanel);
        add(jButton);
        add(jLabel);
        
        repaint();
        
    }
    public void setEditorPanel(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }
    public EditorPanel getEditorPanel() {
        return editorPanel;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0, 25, this.getWidth(), 25);       
        g.drawLine(0, 130, this.getWidth(), 130);
        g.setColor(new Color(213,213,213));
        g.fillRect(0, 0, 300, 24);
        g.fillRect(0,110,300,20);
    } 
    /*
     * 라벨 선택부분
     */
    private class MyMouseListener extends MouseAdapter{
        @Override
        public void mouseEntered(MouseEvent e) {
            if(editorPanel.getSuccess()==true) {
                selectedItem = null;
                editorPanel.makeItem(null);
            }
            JLabel lbl = (JLabel)e.getSource();
            if(lbl != selectedItem)
                lbl.setBackground(chooseColor);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            JLabel lbl = (JLabel)e.getSource();
            if(lbl != selectedItem)
                lbl.setBackground(backGroundColor);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(editorPanel.getFile() == null)
                return;
            if(editorPanel.getSuccess()==true) {
                selectedItem = null;
                editorPanel.makeItem(null);
            }
            if(selectedItem != null ) {
                selectedItem.setBackground(backGroundColor);
                selectedItem = null;
                editorPanel.makeItem(null);
                return ;
            }
            JLabel lbl = (JLabel)e.getSource();
            lbl.setBackground(selectedColor);
            selectedItem = lbl;
            editorPanel.makeItem(selectedItem);
            editorPanel.setSuccess(false);
        }        
        
    }
    
    private JLabel titleContainers;
    private JLabel titleContainers2;
    private JLabel jPanel; 
    private JLabel jButton;
    private JLabel jLabel;
    private Font defaultFont;
    private MyMouseListener myListener;
    private Color backGroundColor;
    private Color chooseColor;
    private Color selectedColor;
    private JLabel selectedItem;
    private EditorPanel editorPanel;
}
