package controller;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.HFrame;

public class ManageJava {

    public ManageJava() {
        baseJava = "import javax.swing.*;\n" + "import java.awt.*;\n" + "import java.awt.event.*;\n" + "\n"
                + "public class %s extends JFrame {\n" + "    public %s() {\n"
                + "    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n" + "    setTitle(\"%s\");\n"
                + "    setBounds(%d, %d, %d +15, %d + 45);\n" + "    setLayout(null);\n" + "    \n" + "%s\n" + "    \n"
                + "    getContentPane().setBackground(new Color(%d, %d, %d));\n" + "    setVisible(true);\n" + "    }\n"
                + "    \n" + "    public static void main(String [] args) {\n" + "        new %s();\n" + "    }\n"
                + "}\n";
        baseChild = "    %s %s = new %s();\n" + "    %s.setText(\"%s\");\n" + "    %s.setBounds(%d, %d, %d, %d);\n"
                + "    %s.setOpaque(true);\n" + "    %s.setBackground(new Color(%d, %d, %d));\n"
                + "    %s\n    add(%s);\n";

    }

    public void createJava(HFrame frame, File file) {
        FileWriter fout = null;
        String java;
        String child = "";
        String fileName = file.getName().substring(0, file.getName().length() - 5);
        Component[] children = frame.getComponents();
        for (int i = 0; i < frame.getComponentCount(); ++i) {
            child += childInfo(children[i]);
        }

        java = String.format(baseJava, fileName, fileName, frame.getTitle(), frame.getX(), frame.getY(),
                frame.getWidth(), frame.getHeight(), child, frame.getBackground().getRed(),
                frame.getBackground().getGreen(), frame.getBackground().getBlue(), fileName);
        try {
            fout = new FileWriter(file);
            fout.write(java);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String childInfo(Component child) {
        String type = "";
        String text = "";
        String labelAlignment = "";
        int r = child.getBackground().getRed();
        int g = child.getBackground().getGreen();
        int b = child.getBackground().getBlue();
        if (child.getClass().getSimpleName().equals("JLabel")) {
            type = "JLabel";
            text = ((JLabel) child).getText();
            labelAlignment = child.getName() + ".setHorizontalAlignment(JLabel.CENTER);\n";
        } else if (child.getClass().getSimpleName().equals("JButton")) {
            type = "JButton";
            text = ((JButton) child).getText();
        }
        return String.format(baseChild, type, child.getName(), type, child.getName(), text, child.getName(),
                child.getX(), child.getY(), child.getWidth(), child.getHeight(), child.getName(), child.getName(), r, g,
                b, labelAlignment, child.getName());
    }

    private String baseJava;
    private String baseChild;

}

