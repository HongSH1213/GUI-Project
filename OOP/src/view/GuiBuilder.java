package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ToolTipManager;

import controller.Controller;

public class GuiBuilder extends JFrame {

    public GuiBuilder() {
        initComponents();
    }

    // 생성
    private void initComponents() {
        editorPanel = new EditorPanel();
        propertiesPanel = new PropertiesPanel();
        palettePanel = new PalettePanel();
        controller = new Controller(editorPanel, this);
        palettePanel.setEditorPanel(editorPanel);
        propertiesPanel.setEditorPanel(editorPanel);
        setTitle("OOP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1500, 1050));
        contentPane = getContentPane();
        // actionListener 생성
        createActionListener();
        // 메뉴 바 생성
        createMenuBar();
        setJMenuBar(menuBar);
        // 툴바 생성
        createToolBar();
        contentPane.add(toolBar, BorderLayout.NORTH);
        // panel 만든것들 추가
        addPanel();

        setVisible(true);
    }

    private void createActionListener() {

        newActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.newFunction();
            }
        };
        openActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.openFunction();
            }
        };
        saveActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.saveFunction();
            }
        };
        saveAsActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.saveAsFunction();
            }
        };
        createJavaActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createJavaFunction();
            }
        };
        exitActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.exitFunction();
            }
        };

    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Oepn");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As");
        createJavaMenuItem = new JMenuItem("Create Java");
        exitMenuItem = new JMenuItem("Exit");

        newMenuItem.addActionListener(newActionListener);
        openMenuItem.addActionListener(openActionListener);
        saveMenuItem.addActionListener(saveActionListener);
        saveAsMenuItem.addActionListener(saveAsActionListener);
        createJavaMenuItem.addActionListener(createJavaActionListener);
        exitMenuItem.addActionListener(exitActionListener);

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(createJavaMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
    }

    private void createToolBar() {
        toolBar = new JToolBar("ToolBar");

        newIcon = new ImageIcon("images/new_icon.png");
        openIcon = new ImageIcon("images/open_icon.png");
        saveIcon = new ImageIcon("images/save_icon.png");
        saveAsIcon = new ImageIcon("images/saveas_icon.png");
        createJavaIcon = new ImageIcon("images/java.png");
        exitIcon = new ImageIcon("images/exit.png");

        newButton = new JButton(newIcon);
        newButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        openButton = new JButton(openIcon);
        openButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        saveButton = new JButton(saveIcon);
        saveButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        saveAsButton = new JButton(saveAsIcon);
        saveAsButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        createJavaButton = new JButton(createJavaIcon);
        createJavaButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        exitButton = new JButton(exitIcon);
        exitButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        saveButton.setEnabled(false);
        saveAsButton.setEnabled(false);
        createJavaButton.setEnabled(false);

        // 툴바 고정
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(240, 240, 240));

        newButton.addActionListener(newActionListener);
        openButton.addActionListener(openActionListener);
        saveButton.addActionListener(saveActionListener);
        saveAsButton.addActionListener(saveAsActionListener);
        createJavaButton.addActionListener(createJavaActionListener);
        exitButton.addActionListener(exitActionListener);

        newButton.setToolTipText("새로 만들기");
        openButton.setToolTipText("열기");
        saveButton.setToolTipText("저장");
        saveAsButton.setToolTipText("다른 이름으로 저장");
        createJavaButton.setToolTipText("자바 파일 생성");
        exitButton.setToolTipText("종료");

        ToolTipManager manager = ToolTipManager.sharedInstance();
        manager.setInitialDelay(200); // 초기 툴팁 출력 지연 시간을 0.2초로 설정
        manager.setDismissDelay(1000); // 툴팁 지속 시간을 1초로 설정

        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.addSeparator();
        toolBar.add(saveButton);
        toolBar.add(saveAsButton);
        toolBar.add(createJavaButton);
        toolBar.addSeparator();
        toolBar.add(exitButton);
    }

    private void addPanel() {
        paletteAndPropertiesPanel = new JPanel();
        editorScrollPane = new JScrollPane(editorPanel);
        paletteScrollPane = new JScrollPane(palettePanel);
        propertiesScrollPane = new JScrollPane(propertiesPanel);
        editorPanel.setPropertiesPanel(propertiesPanel);

        contentPane.add(editorScrollPane, BorderLayout.CENTER);
        contentPane.add(paletteAndPropertiesPanel, BorderLayout.EAST);

        GroupLayout layout = new GroupLayout(paletteAndPropertiesPanel);
        paletteAndPropertiesPanel.setLayout(layout);

        palettePanel.setPreferredSize(new Dimension(300, 440));
        propertiesPanel.setPreferredSize(new Dimension(300, 440));

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(paletteScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(propertiesScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(paletteScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(propertiesScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
                                Short.MAX_VALUE));

    }

    public void setVisibleButton() {
        saveButton.setEnabled(true);
        saveAsButton.setEnabled(true);
        createJavaButton.setEnabled(true);
    }

    private Container contentPane;
    private Controller controller;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem createJavaMenuItem;
    private JMenuItem exitMenuItem;

    private JToolBar toolBar;
    private JButton newButton;
    private JButton openButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private JButton createJavaButton;
    private JButton exitButton;

    private ImageIcon newIcon;
    private ImageIcon openIcon;
    private ImageIcon saveIcon;
    private ImageIcon saveAsIcon;
    private ImageIcon createJavaIcon;
    private ImageIcon exitIcon;

    private ActionListener newActionListener;
    private ActionListener openActionListener;
    private ActionListener saveActionListener;
    private ActionListener saveAsActionListener;
    private ActionListener createJavaActionListener;
    private ActionListener exitActionListener;

    private EditorPanel editorPanel;
    private JPanel paletteAndPropertiesPanel;
    private PalettePanel palettePanel;
    private PropertiesPanel propertiesPanel;
    private JScrollPane editorScrollPane;
    private JScrollPane paletteScrollPane;
    private JScrollPane propertiesScrollPane;

    public static void main(String[] args) {
        new GuiBuilder();

    }

}
