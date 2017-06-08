package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

public class ModelInfo extends ComponentName {
    private int x, y;
    private int width, height;
    private String type;
    private String name;
    private String text;
    private String parentName;
    private int RGB;
    

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setBounds(Rectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }

    public void setSize(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    public void setColor(Color color) {
        RGB = color.getRGB();
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Dimension getSize(int x, int y) {
        return new Dimension(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getParentName() {
        return parentName;
    }
    
    public Color getColor() {
        return new Color(RGB);
    }

}
