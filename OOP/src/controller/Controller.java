package controller;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;

import model.NodeModel;
import view.EditorPanel;
import view.PalettePanel;
import view.PropertiesPanel;

public class Controller {
    EditorPanel editorPanel;
    PropertiesPanel propertiesPanel;
    PalettePanel palettePanel;
    NewFunctionDialog newDialog;
    JFileChooser chooser;
    FileNameExtensionFilter filter;
    
    public Controller(EditorPanel editorPanel, PropertiesPanel propertiesPanel, PalettePanel palettePanel) {
        this.editorPanel = editorPanel;
        this.propertiesPanel = propertiesPanel;
        this.palettePanel = palettePanel;
        newDialog = new NewFunctionDialog(editorPanel);
        chooser = new JFileChooser(System.getProperty("user.home")+"\\Desktop");
        filter = new FileNameExtensionFilter("JSON Files","json");
        chooser.setFileFilter(filter);
    }
    
	public void newFunction() {
	    newDialog.setVisible(true);
	    newDialog.resetDialog();
	}
	public void openFunction() {
	    int ret = chooser.showOpenDialog(null);
	    File file = new File(chooser.getSelectedFile().getPath());
	    FileReader fin = null;
	    Gson gson = new Gson();
	    StringBuffer buffer = new StringBuffer();
	    if(ret!=JFileChooser.APPROVE_OPTION)
	        return ;
	    
	    try {
            fin = new FileReader(file);
            int c;
            while((c=fin.read())!=-1) {
                buffer.append((char)c);
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	    NodeModel model = gson.fromJson(buffer.toString(), NodeModel.class);
	    ManageModel.openModel(model, file, editorPanel);
	}
	public void saveFunction() {
	    NodeModel model = ManageModel.parseComponent(editorPanel.getFrame(), null);
        File file = editorPanel.getFile();
	    ManageModel.saveModel(model, file);
	}
	public void saveAsFunction() {
	    int ret = chooser.showSaveDialog(null);
	    File file = null;
	    if(ret!=JFileChooser.APPROVE_OPTION)
	        return ;
	    NodeModel model = ManageModel.parseComponent(editorPanel.getFrame(), null);
	    if(!chooser.getSelectedFile().getName().contains(".json"))
	        file = new File(chooser.getSelectedFile().getPath() + ".json");
	    else
	        file = new File(chooser.getSelectedFile().getPath());
        ManageModel.saveModel(model, file);
	}
	public void createJavaFunction() {
	}
	public void exitFunction() {
		System.exit(0);
	}
	
	
}
