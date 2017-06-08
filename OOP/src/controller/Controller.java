package controller;

import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import model.NodeModel;
import view.EditorPanel;
import view.GuiBuilder;

public class Controller {
    private EditorPanel editorPanel;
    private NewFunctionDialog newDialog;
    private JFileChooser chooser;
    private JFileChooser javaChooser;
    private FileNameExtensionFilter filter;
    private FileNameExtensionFilter javaFilter;
    private ManageJava javaManager;
    private String desktop;
    private GuiBuilder gui;

    public Controller(EditorPanel editorPanel, GuiBuilder gui) {
        this.editorPanel = editorPanel;
        this.gui = gui;
        desktop = System.getProperty("user.home") + "\\Desktop";
        newDialog = new NewFunctionDialog(editorPanel);
        chooser = new JFileChooser(desktop);
        filter = new FileNameExtensionFilter("JSON Files", "json");
        chooser.setFileFilter(filter);
        javaChooser = new JFileChooser(desktop);
        javaFilter = new FileNameExtensionFilter("JAVA Files", "java");
        javaChooser.setFileFilter(javaFilter);
        javaChooser.setDialogTitle("자바 파일 생성");
        javaManager = new ManageJava();

    }

    public void newFunction() {
        newDialog.setVisible(true);
        newDialog.resetDialog();
        if (editorPanel.getFile() != null)
            gui.setVisibleButton();
    }

    public void openFunction() {
        int ret = chooser.showOpenDialog(null);
        if (ret != JFileChooser.APPROVE_OPTION)
            return;

        File file = new File(chooser.getSelectedFile().getPath());
        FileReader fin = null;
        Gson gson = new Gson();
        StringBuffer buffer = new StringBuffer();

        try {
            fin = new FileReader(file);
            int c;
            while ((c = fin.read()) != -1) {
                buffer.append((char) c);
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NodeModel model = gson.fromJson(buffer.toString(), NodeModel.class);
        ManageModel.openModel(model, file, editorPanel);
        gui.setVisibleButton();
    }

    public void saveFunction() {
        if (editorPanel.getFrame() == null)
            return;
        NodeModel model = ManageModel.parseComponent(editorPanel.getFrame(), null);
        File file = editorPanel.getFile();
        ManageModel.saveModel(model, file);
    }

    public void saveAsFunction() {
        if (editorPanel.getFrame() == null)
            return;
        int ret = chooser.showSaveDialog(null);
        File file = null;
        if (ret != JFileChooser.APPROVE_OPTION)
            return;
        NodeModel model = ManageModel.parseComponent(editorPanel.getFrame(), null);
        if (!chooser.getSelectedFile().getName().contains(".json"))
            file = new File(chooser.getSelectedFile().getPath() + ".json");
        else
            file = new File(chooser.getSelectedFile().getPath());
        ManageModel.saveModel(model, file);
    }

    public void createJavaFunction() {
        if (editorPanel.getFrame() == null)
            return;
        String path = desktop + "\\" + editorPanel.getFrame().getName() + ".java";
        javaChooser.setSelectedFile(new File(path));
        int ret = javaChooser.showSaveDialog(null);
        File file = null;
        if (ret != JFileChooser.APPROVE_OPTION)
            return;
        if (!javaChooser.getSelectedFile().getName().contains(".java"))
            file = new File(javaChooser.getSelectedFile().getPath() + ".java");
        else
            file = new File(javaChooser.getSelectedFile().getPath());
        // createJava 호출
        javaManager.createJava(editorPanel.getFrame(), file);

    }

    public void exitFunction() {
        System.exit(0);
    }

}
