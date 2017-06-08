package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditorPanel extends JPanel {
    EditorPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        fileNameLabel = new JLabel("File Name");
        fileNameLabel.setFont(new Font("Ariel", Font.PLAIN, 28));
        fileNameLabel.setBounds(5, 5, 500, 30);
        add(fileNameLabel);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedItem != null) {
                    selectedItem.setBorder(null);
                    if (selectedItem.getClass().getSimpleName().equals("JButton"))
                        selectedItem.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    // properties 정보 초기화
                    propertiesPanel.setProperties(null);
                }
                selectedItem = null;
            }
        });

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0, 40, this.getWidth(), 40);
    }

    public void setFrame(HFrame frame) {
        if (backgroundPanel != null)
            remove(backgroundPanel);
        if (this.frame != null) {
            remove(this.frame);
        }
        this.frame = frame;
        Component[] list = frame.getComponents();
        for (int i = 0; i < frame.getComponentCount(); ++i) {
            ItemKeyListener itemKeyListener = new ItemKeyListener();
            itemKeyListener.setPanel(this, propertiesPanel);
            list[i].addKeyListener(itemKeyListener);
            list[i].addMouseListener(itemMouseListener);
            list[i].addMouseMotionListener(itemMouseListener);
        }

        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(frame.getX() - 2, frame.getY() - 2, frame.getWidth() + 4, frame.getHeight() + 4);
        backgroundPanel.setBackground(Color.CYAN);
        add(frame);
        add(backgroundPanel);
        repaint();
        backgroundPanel.repaint();
        frame.setEditorPanel(this);
    }

    public JPanel getBackgroundPanel() {
        return backgroundPanel;
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
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setPropertiesPanel(PropertiesPanel panel) {
        propertiesPanel = panel;
        itemMouseListener.setPanel(this, propertiesPanel);
    }

    public PropertiesPanel getProperteisPanel() {
        return propertiesPanel;
    }

    public void setSelectedItem(JComponent selectedItem) {
        this.selectedItem = selectedItem;
    }

    public JComponent getSelectedItem() {
        return selectedItem;
    }

    public void setSuccess(boolean ox) {
        success = ox;
    }

    public boolean getSuccess() {
        return success;
    }

    public void makeItem(JLabel type) {
        if (type == null) {
            item = null;
            this.type = null;
            frame.setFlag(false);
            return;
        }
        this.type = type;

        if (type.getText().equals("JLabel")) {
            item = new JLabel("JLabel" + (frame.countName.getLabelCnt() + 1));
            item.setName("JLabel" + (frame.countName.getLabelCnt() + 1));
        } else if (type.getText().equals("JButton")) {
            item = new JButton("JButton" + (frame.countName.getButtonCnt() + 1));
            item.setName("JButton" + (frame.countName.getButtonCnt() + 1));
        }
        frame.setFlag(true);
    }

    public void addItem(Rectangle info) {
        success = true;
        if (type.getText().equals("JLabel")) {
            frame.countName.plusLabelCnt();
            ((JLabel) item).setHorizontalAlignment(JLabel.CENTER);
            item.setBackground(new Color(255,255,102));
        } else if (type.getText().equals("JButton")) {
            frame.countName.plusButtonCnt();
            item.setBackground(new Color(0,255,255));
        }
        item.setOpaque(true);
        ItemKeyListener itemKeyListener = new ItemKeyListener();
        itemKeyListener.setPanel(this, propertiesPanel);
        item.addMouseListener(itemMouseListener);
        item.addMouseMotionListener(itemMouseListener);
        item.addKeyListener(itemKeyListener);
        item.setBounds(info);
        frame.add(item);
        type.setBackground(backGroundColor);
    }

    private ItemMouseListener itemMouseListener = new ItemMouseListener();
    private Color backGroundColor = new Color(240, 240, 240);
    private JLabel fileNameLabel;
    private JPanel backgroundPanel;
    private HFrame frame;
    private File file;
    private JComponent item;
    private JLabel type;
    private PropertiesPanel propertiesPanel;
    private JComponent selectedItem;
    private boolean success;
}