package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.HFrame;
import model.NodeModel;

public class EditorPanel extends JPanel{
    EditorPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        fileNameLabel = new JLabel("File Name");
        fileNameLabel.setFont(new Font("Ariel", Font.PLAIN,28));
        fileNameLabel.setBounds(5, 5,500,30);
        add(fileNameLabel);
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println(e.getX()+"  " + e.getY());
            }
        });
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0, 40, this.getWidth(), 40);
    }
    public void setFrame(HFrame frame) {
        this.frame = frame;
        if(backgroundPanel!=null)
            remove(backgroundPanel);
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(frame.getX()-2, frame.getY()-2, frame.getWidth()+4, frame.getHeight()+4);
        backgroundPanel.setBackground(Color.CYAN);
        add(frame);
        add(backgroundPanel);
        repaint();
    }
    public HFrame getFrame() {
        return frame;        
    }
    public void setFileNameLabel(String text) {
        fileNameLabel.setText(text);
    }
    public String getFileNameLabel() {
        return fileNameLabel.getText();
    }
    public void setFile(File file) {
        this.file=file;
    }
    public File getFile() {
        return file;
    }
    public void setNodeModel(NodeModel base) {
        this.base = base;
    }
    public NodeModel getNodeModel() {
        return base;
    }
    
    private JLabel fileNameLabel;
    private JPanel backgroundPanel;
    private NodeModel base;
    private HFrame frame;
    private File file;
    private String type;
}