package controller;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;

import model.NodeModel;
import view.EditorPanel;
import view.HFrame;

public class ManageModel {
    
    
    public static NodeModel createFirstModel() {
        NodeModel base = new NodeModel();
        base.setBounds(20, 60, 450, 300);
        base.setType("JFrame");
        base.setName("frame" + base.getFrameCnt());
        base.setText(base.getName());
        return base;
    }
    public static void saveModel(NodeModel model,File path) {
        Gson gson = new Gson();
        FileWriter fout = null;
        String output = gson.toJson(model); // json 형식 변환
        
        try {
            fout = new FileWriter(path);
            fout.write(output);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public static void openModel(NodeModel model,File file,EditorPanel editorPanel) { //열기가아니라 editorPanel에 세팅
        HFrame panel = (HFrame)ManageModel.parseModel(model, null);
        editorPanel.setFrame(panel);
        editorPanel.setFile(file);
        editorPanel.setNodeModel(model);
        editorPanel.setFileNameLabel(file.getName());
    }

    public static Container parseModel(NodeModel model, Container parent) {
        Container result = null;
        Container contentPane = null;
        
        if(model.getType().equals("JFrame")) {
            HFrame frame = new HFrame();
            frame.setTitle(model.getText());
            frame.setLayout(null);
            contentPane = frame;
            result = frame;
        }
        else if(model.getType().equals("JPanel")) {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            contentPane = panel;
            result = panel;
        }
        else if(model.getType().equals("JButton")) {
            JButton btn = new JButton();
            btn.setText(model.getText());
            result = btn;
        }
        else if(model.getType().equals("JLabel")) {
            JLabel lbl = new JLabel();
            lbl.setText(model.getText());
            result = lbl;
        }
//        else if(model.getType().equals("")) {
//            
//        }
        result.setBounds(model.getBounds());
        result.setName(model.getName());
        
        
        if(!model.isChildNull())        // 자식이 있을 떄
            parseModel(model.getChild(), contentPane);
        if(!model.isSiblingNull()) // 형제가 있을 떄
            parseModel(model.getSibling(), parent);
        
        if(parent != null)
            parent.add(result);
        
        return result;
    }
    
    //JFrame을 NodeModel로 파싱
    public static NodeModel parseComponent(Container element, NodeModel parent) {
        NodeModel base = new NodeModel();
        NodeModel temp = null;
        Component [] children = null;
        
        base.setBounds(element.getBounds());
        base.setName(element.getName());
        base.setType(element.getClass().getSimpleName());
        if(base.getType().equals("HFrame"))
            base.setType("JFrame");
        
        if(element.getParent()!=null)
            base.setParentName(element.getParent().getName());
        
        children = element.getComponents();
        
        if(base.getType().equals("JFrame")) {
            HFrame frame = (HFrame)element;
            base.setText(frame.getTitle());
        }
        else if(base.getType().equals("JPanel")) {
            //JPanel은 text설정 안해도댐
        }
        else if(base.getType().equals("JButton")) {
            JButton btn = (JButton)element;
            base.setText(btn.getText());
        }
        else if(base.getType().equals("JLabel")) {
            JLabel lbl = (JLabel)element;
            base.setText(lbl.getText());
        }
        
        temp = base;
        for(int i=0;i<children.length;++i) {
            parseComponent((Container)children[i], temp);
            if(i==0)
                temp = temp.getChild();
            else
                temp = temp.getSibling();
        }
        if(parent == null)
            return base;

        if(base.getParentName() == parent.getParentName()) {
            parent.setSibling(base);
        }
        else
            parent.setChild(base);
        
        
        return base;
    }
    
//    public static void main(String [] args) {
//        NodeModel base = ManageModel.createFirstModel();
//        NodeModel temp ;
//        NodeModel btn = new NodeModel();
//        btn.setBounds(20,20,100,20);
//        btn.setType("JButton");
//        btn.setName("button1");
//        btn.setText("hello~");
//        btn.setParentName(base.getName());
//        base.setChild(btn);
//        NodeModel lbl = new NodeModel();
//        lbl.setBounds(130,20,60,20);
//        lbl.setType("JLabel");
//        lbl.setName("label1");
//        lbl.setText("iamlabel");
//        lbl.setParentName(btn.getParentName());
//        btn.setSibling(lbl);  
//        
//        NodeModel panel = new NodeModel();
//        panel.setBounds(300,100,100,100);
//        panel.setType("JPanel");
//        panel.setName("panel1");
//        panel.setParentName(btn.getParentName());
//        lbl.setSibling(panel);  
//        
//        NodeModel lbl2 = new NodeModel();
//        lbl2.setBounds(0,20,50,20);
//        lbl2.setType("JLabel");
//        lbl2.setName("label2");
//        lbl2.setText("hello???");
//        lbl2.setParentName(panel.getName());
//        panel.setChild(lbl2);  
//        
//        Gson gson = new Gson();
//        String output = gson.toJson(base);
//        System.out.println("parse before: \n" + output);
//        JFrame frame = (JFrame)ManageModel.parseModel(base, null);
//        NodeModel test = ManageModel.parseComponent(frame, null);
//        output = gson.toJson(test);
//        System.out.println("parse after: \n" + output);
//        JFrame frame2 = (JFrame)ManageModel.parseModel(test, null);
//    }
    
    
}
