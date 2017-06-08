package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.google.gson.Gson;

import model.NodeModel;
import view.EditorPanel;
import view.HFrame;

public class ManageModel {

    public static NodeModel createFirstModel() {
        NodeModel base = new NodeModel();
        base.setBounds(20, 60, 450, 300);
        base.setType("JFrame");
        base.plusFrameCnt();
        base.setName("frame" + base.getFrameCnt());
        base.setText(base.getName());
        base.setColor(new Color(238,238,238));
        return base;
    }

    public static void saveModel(NodeModel model, File path) {
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

    public static void openModel(NodeModel model, File file, EditorPanel editorPanel) { // 열기가아니라
                                                                                        // editorPanel에
                                                                                        // 세팅
        HFrame panel = (HFrame) ManageModel.parseModel(model, null);
        editorPanel.setFrame(panel);
        editorPanel.setFile(file);
        editorPanel.setFileNameLabel(file.getName());
    }

    public static Container parseModel(NodeModel model, Container parent) {
        Container result = null;
        Container contentPane = null;

        if (model.getType().equals("JFrame")) {
            HFrame frame = new HFrame();
            frame.setTitle(model.getText());
            frame.setLayout(null);
            contentPane = frame;
            result = frame;
            frame.countName.setFrameCnt(model.getFrameCnt());
            frame.countName.setButtonCnt(model.getButtonCnt());
            frame.countName.setLabelCnt(model.getLabelCnt());
        }
        // else if(model.getType().equals("JPanel")) {
        // JPanel panel = new JPanel();
        // panel.setLayout(null);
        // contentPane = panel;
        // result = panel;
        // }
        else if (model.getType().equals("JButton")) {
            JButton btn = new JButton();
            btn.setText(model.getText());
            btn.setOpaque(true);
            result = btn;
        } else if (model.getType().equals("JLabel")) {
            JLabel lbl = new JLabel();
            lbl.setText(model.getText());
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setOpaque(true);
            result = lbl;
        }
        // else if(model.getType().equals("")) {
        //
        // }
        result.setBounds(model.getBounds());
        result.setName(model.getName());
        result.setBackground(model.getColor());
        
        if (!model.isChildNull()) // 자식이 있을 떄
            parseModel(model.getChild(), contentPane);
        if (!model.isSiblingNull()) // 형제가 있을 떄
            parseModel(model.getSibling(), parent);

        if (parent != null)
            parent.add(result);

        return result;
    }

    // JFrame을 NodeModel로 파싱
    public static NodeModel parseComponent(Container element, NodeModel parent) {
        NodeModel base = new NodeModel();
        NodeModel temp = null;
        Component[] children = null;

        base.setBounds(element.getBounds());
        base.setName(element.getName());
        base.setType(element.getClass().getSimpleName());
        if (base.getType().equals("HFrame")) {
            HFrame frame = (HFrame) element;
            base.setType("JFrame");
            base.setFrameCnt(frame.countName.getFrameCnt());
            base.setButtonCnt(frame.countName.getButtonCnt());
            base.setLabelCnt(frame.countName.getLabelCnt());
            // base.setPanelCnt(frame.countName.getPanelCnt());
        }

        if (element.getParent() != null)
            base.setParentName(element.getParent().getName());

        children = element.getComponents();

        if (base.getType().equals("JFrame")) {
            HFrame frame = (HFrame) element;
            base.setText(frame.getTitle());
        }
        // else if(base.getType().equals("JPanel")) {
        // //JPanel은 text설정 안해도댐
        // }
        else if (base.getType().equals("JButton")) {
            JButton btn = (JButton) element;
            base.setText(btn.getText());
        } else if (base.getType().equals("JLabel")) {
            JLabel lbl = (JLabel) element;
            base.setText(lbl.getText());
        }
        base.setColor(element.getBackground());
        
        temp = base;
        for (int i = 0; i < children.length; ++i) {
            parseComponent((Container) children[i], temp);
            if (i == 0)
                temp = temp.getChild();
            else
                temp = temp.getSibling();
        }
        if (parent == null)
            return base;

        if (base.getParentName() == parent.getParentName()) {
            parent.setSibling(base);
        } else
            parent.setChild(base);

        return base;
    }

}
