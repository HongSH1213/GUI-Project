package controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.NodeModel;
import view.EditorPanel;

public class NewFunctionDialog extends JDialog{
    private String fileName ;
    private String directory; 
    private String path; 
    private JFileChooser chooser; 
    private JTextField fileNameTextField;
    private JTextField directoryTextField;
    private JTextField pathTextField;
    private JLabel newAndLocationLabel;
    private JLabel fileNameLabel;
    private JLabel folderLabel;
    private JLabel createdFileLabel;
    private JLabel warningLabel;
    private JButton browseButton;
    private JButton finishButton;
    private JButton cancelButton;
    private JPanel contentPane;
    private EditorPanel editorPanel;
    public NewFunctionDialog(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
        fileName = "";
        directory= System.getProperty("user.home")+"\\Desktop"; //바탕화면 경로
        path = directory ; //바탕화면 경로
        chooser = new JFileChooser(new File(path)); 
        fileNameTextField = new JTextField();
        directoryTextField = new JTextField(directory.toString());
        pathTextField = new JTextField();
        newAndLocationLabel = new JLabel("New and Location");
        fileNameLabel = new JLabel("File Name:");
        folderLabel = new JLabel("Folder:");
        createdFileLabel = new JLabel("Created File:");
        warningLabel = new JLabel("! The target folder does not exist.");
        browseButton = new JButton("browse");
        finishButton = new JButton("finish");
        cancelButton = new JButton("cancel");
        
        setTitle("New GUI Form");
        setModal(true);
        Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); // 화면크기
        Font font = new Font("Ariel", Font.PLAIN,20);
        setBounds(res.width/2-300, res.height/2-175, 600,350);
        contentPane = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponents(g);
                g.drawLine(0, 40, 600, 40);
                
            }
        };
        //line 그리기
        contentPane.setLayout(null);
        add(contentPane);
        
        newAndLocationLabel.setFont(new Font("Ariel", Font.PLAIN,30));
        newAndLocationLabel.setBounds(10, 10,240,30);
        fileNameLabel.setFont(font);
        fileNameLabel.setBounds(10, 50,120,30);
        folderLabel.setFont(font);
        folderLabel.setBounds(10, 100,120,30);
        createdFileLabel.setFont(font);
        createdFileLabel.setBounds(10, 135,120,30);
        warningLabel.setFont(font);
        warningLabel.setBounds(10, 230, 400,30);
        warningLabel.setForeground(Color.RED);
        warningLabel.setOpaque(true);
        
        fileNameTextField.setBounds(130,52,450,30);
        directoryTextField.setBounds(130,100,360,30);
        pathTextField.setBounds(130,135,450,30);
        pathTextField.setEnabled(false);
        pathTextField.setText(path);
        fileNameTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int c = e.getKeyChar();
                if((c>='0'&&c<='9')||(c>='A'&&c<='Z')||(c>='a'&&c<='z')||c==8) 
                    fileName = fileNameTextField.getText();
                
                path =(directory.length()==0?"":(directory+"\\"))+(fileName.length()==0?"":fileName);
                File file = new File(directory);
               if(fileName.length()!=0 && file.exists()) {
                   pathTextField.setText(path + ".json");
                   finishButton.setEnabled(true);
               }
               else {
                   pathTextField.setText(path);
                   finishButton.setEnabled(false);
               }
            }
        });
        directoryTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int c = e.getKeyChar();
                if((c>='0'&&c<='9')||(c>='A'&&c<='Z')||(c>='a'&&c<='z')||c==8) 
                    directory = directoryTextField.getText();
                path =(directory.length()==0?"":(directory+"\\"))+(fileName.length()==0?"":fileName);
               
                if(fileName.length()!=0)
                   pathTextField.setText(path + ".json");
                else
                   pathTextField.setText(path);
                
               File file = new File(directory);
               if(file.exists()){
                   warningLabel.setVisible(false);
                   if(fileName.length()!=0)
                       finishButton.setEnabled(true);
                   repaint();
               }
               else{
                   warningLabel.setVisible(true);
                   finishButton.setEnabled(false);
                   repaint();
               }
            }
        });
        //파일 이름과 디렉토리 이름이 완벽히 들어온다는 전제하 기능 
        browseButton.setBounds(500,100,80,30);
        cancelButton.setBounds(500,280,80,30);
        finishButton.setBounds(415,280,80,30);
     
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 폴더 만 open
        
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = chooser.showOpenDialog(null);
                if(ret != JFileChooser.APPROVE_OPTION)
                    return ;
                directory = chooser.getSelectedFile().getPath(); 
                directoryTextField.setText(directory);
                path =(directory.length()==0?"":(directory+"\\"))+(fileName.length()==0?"":fileName);
                if(fileName.length()!=0)
                    pathTextField.setText(path+".json");
                else
                    pathTextField.setText(path);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NodeModel temp = ManageModel.createFirstModel();
                File file = new File(path+".json");
                ManageModel.saveModel(temp,file);
                ManageModel.openModel(temp,file,editorPanel);
                setVisible(false);
            }
        });
        warningLabel.setVisible(false);
        finishButton.setEnabled(false);
        contentPane.add(newAndLocationLabel);
        contentPane.add(fileNameLabel);
        contentPane.add(folderLabel);
        contentPane.add(createdFileLabel);
        contentPane.add(warningLabel);
        contentPane.add(fileNameTextField);
        contentPane.add(directoryTextField);
        contentPane.add(pathTextField);
        contentPane.add(browseButton);
        contentPane.add(finishButton);
        contentPane.add(cancelButton);
        setResizable(false);
        setVisible(false);
    }
    
    public void resetDialog() {
        fileName="";
        directory= System.getProperty("user.home")+"\\Desktop";
        path = directory;
        fileNameTextField.setText(fileName);
        directoryTextField.setText(directory);
        pathTextField.setText(path);
        finishButton.setEnabled(false);
        warningLabel.setVisible(false);
        Dimension res = Toolkit.getDefaultToolkit().getScreenSize(); // 화면크기
        setLocation(res.width/2-300, res.height/2-175);
        repaint();
    }
    
}
