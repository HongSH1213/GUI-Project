package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PropertiesPanel extends JPanel {
	public PropertiesPanel() {
		Font defaultFont = new Font("Nirmala UI Semilight", Font.PLAIN, 30);
		setLayout(new GridLayout(8, 2, 0, 5));
		String[] types = { "JButton", "JLabel", "JFrame" };
		itemMouseListener = new ItemMouseListener();
		itemKeyListener = new ItemKeyListener();
		intKeyListener = new IntKeyListener();
		comboActionListener = new ComboActionListener();
		stringKeyListener = new StringKeyListener();
		typeLabel = new JLabel("type :");
		typeLabel.setFont(defaultFont);
		typeBox = new JComboBox<String>(types);
		typeBox.addActionListener(comboActionListener);
		nameLabel = new JLabel("name :");
		nameLabel.setFont(defaultFont);
		nameTextField = new JTextField();
		nameTextField.setEnabled(false);
		nameTextField.setName("name");
		nameTextField.addKeyListener(stringKeyListener);
		textLabel = new JLabel("text :");
		textLabel.setFont(defaultFont);
		textTextField = new JTextField();
		textTextField.setEnabled(false);
		textTextField.setName("text");
		textTextField.addKeyListener(stringKeyListener);
		xLabel = new JLabel("x :");
		xLabel.setFont(defaultFont);
		xTextField = new JTextField();
		xTextField.setEnabled(false);
		xTextField.addKeyListener(intKeyListener);
		xTextField.setName("x");
		yLabel = new JLabel("y :");
		yLabel.setFont(defaultFont);
		yTextField = new JTextField();
		yTextField.setEnabled(false);
		yTextField.addKeyListener(intKeyListener);
		yTextField.setName("y");
		widthLabel = new JLabel("width :");
		widthLabel.setFont(defaultFont);
		widthTextField = new JTextField();
		widthTextField.setEnabled(false);
		widthTextField.addKeyListener(intKeyListener);
		widthTextField.setName("width");
		heightLabel = new JLabel("height :");
		heightLabel.setFont(defaultFont);
		heightTextField = new JTextField();
		heightTextField.setEnabled(false);
		heightTextField.addKeyListener(intKeyListener);
		heightTextField.setName("height");
		colorLabel = new JLabel("Color :");
		colorLabel.setFont(defaultFont);
		colorTypeLabel = new JLabel();
		colorTypeLabel.setPreferredSize(new Dimension(80,40));
		colorButton = new JButton("...");
		colorActionListener = new ColorActionListener();
		colorButton.addActionListener(colorActionListener);
		JPanel temp = new JPanel();
		temp.add(colorTypeLabel);
		temp.add(colorButton);
		
		setFalse(); // 초기에는 선택 안되게
		add(typeLabel);
		add(typeBox);
		add(nameLabel);
		add(nameTextField);
		add(textLabel);
		add(textTextField);
		add(xLabel);
		add(xTextField);
		add(yLabel);
		add(yTextField);
		add(widthLabel);
		add(widthTextField);
		add(heightLabel);
		add(heightTextField);
		add(colorLabel);
		add(temp);
	}

	public void setProperties(JComponent comp) {
		if (comp == null) {
			if (textLabel.getText().equals("title :"))
				textLabel.setText("text :");
			setFalse();
			return;
		}
		setTrue();
		item = comp;
		String className = comp.getClass().getSimpleName();
		if (className.equals("JButton")) {
			state = false;
			typeBox.setSelectedIndex(0);
			state = true;
			textTextField.setText(((JButton) comp).getText());
		} else if (className.equals("JLabel")) {
			state = false;
			typeBox.setSelectedIndex(1);
			state = true;
			textTextField.setText(((JLabel) comp).getText());
		} else if (className.equals("HFrame")) {
			state = false;
			typeBox.setSelectedIndex(2);
			state = true;
			typeBox.setEnabled(false);
			xTextField.setEnabled(false);
			yTextField.setEnabled(false);
			textTextField.setText(((HFrame) comp).getTitle());
			textLabel.setText("title :");
		}
		nameTextField.setText(comp.getName());
		xTextField.setText(Integer.toString(comp.getX()));
		yTextField.setText(Integer.toString(comp.getY()));
		widthTextField.setText(Integer.toString(comp.getWidth()));
		heightTextField.setText(Integer.toString(comp.getHeight()));
		colorTypeLabel.setText(comp.getBackground().getRed()+" "+comp.getBackground().getGreen()+" "+comp.getBackground().getBlue());
	}

	private void setFalse() {
		typeBox.setEnabled(false);
		state = false;
		typeBox.setSelectedIndex(0);
		state = true;
		nameTextField.setEnabled(false);
		nameTextField.setText("");
		textTextField.setEnabled(false);
		textTextField.setText("");
		xTextField.setEnabled(false);
		xTextField.setText("");
		yTextField.setEnabled(false);
		yTextField.setText("");
		widthTextField.setEnabled(false);
		widthTextField.setText("");
		heightTextField.setEnabled(false);
		heightTextField.setText("");
		colorTypeLabel.setText("");
		colorButton.setEnabled(false);
	}

	private void setTrue() {
		typeBox.setEnabled(true);
		state = false;
		typeBox.setSelectedIndex(0);
		state = true;
		nameTextField.setEnabled(true);
		nameTextField.setText("");
		textTextField.setEnabled(true);
		textTextField.setText("");
		xTextField.setEnabled(true);
		xTextField.setText("");
		yTextField.setEnabled(true);
		yTextField.setText("");
		widthTextField.setEnabled(true);
		widthTextField.setText("");
		heightTextField.setEnabled(true);
		heightTextField.setText("");
		colorTypeLabel.setText("");
		colorButton.setEnabled(true);
	}

	private class ColorActionListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        Color selectedColor = JColorChooser.showDialog(null, "Choose Color", Color.YELLOW);
	        if(selectedColor == null)
	            return;
	        item.setBackground(selectedColor);
	        colorTypeLabel.setText(item.getBackground().getRed()+" "+item.getBackground().getGreen()+" "+item.getBackground().getBlue());
	    }
	}
	
	private class IntKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() != 10)
				return;
			int num = 0;
			JTextField temp = (JTextField) e.getSource();
			try {
				num = Integer.parseInt(temp.getText());
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "숫자만 입력하시오.", "error", JOptionPane.ERROR_MESSAGE);
				if (temp.getName().equals("x")) {
					temp.setText(Integer.toString(item.getX()));
				} else if (temp.getName().equals("y")) {
					temp.setText(Integer.toString(item.getY()));
				} else if (temp.getName().equals("width")) {
					temp.setText(Integer.toString(item.getWidth()));
				} else if (temp.getName().equals("height")) {
					temp.setText(Integer.toString(item.getHeight()));
				}
				return;
			}
			if (temp.getName().equals("x")) {
				item.setLocation(num, item.getY());
			} else if (temp.getName().equals("y")) {
				item.setLocation(item.getX(), num);
			} else if (temp.getName().equals("width")) {
				item.setSize(num, item.getHeight());
			} else if (temp.getName().equals("height")) {
				item.setSize(item.getWidth(), num);
			}
			// JOptionPane.showMessageDialog(null,
			// "Success!","Success!",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class StringKeyListener extends KeyAdapter {
		private String regExp = "[a-zA-Z|$|_][\\w|_|$]*";
		private boolean ok;

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() != 10)
				return;
			JTextField temp = (JTextField) e.getSource();
			if (temp.getName().equals("name")) {
				ok = Pattern.matches(regExp, temp.getText());
				if (ok)
					item.setName(temp.getText());
				else {
					JOptionPane.showMessageDialog(null, "Wrong name.", "error", JOptionPane.ERROR_MESSAGE);
					temp.setText(item.getName());
				}

			} else if (temp.getName().equals("text")) {
				ok = Pattern.matches("[^\\n]+", temp.getText());
				if (ok) {
					if (item.getClass().getSimpleName().equals("HFrame")) {
						((HFrame) item).setTitle(temp.getText());
					} else if (item.getClass().getSimpleName().equals("JButton")) {
						((JButton) item).setText(temp.getText());
					} else if (item.getClass().getSimpleName().equals("JLabel")) {
						((JLabel) item).setText(temp.getText());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Wrong text.", "error", JOptionPane.ERROR_MESSAGE);
					if (item.getClass().getSimpleName().equals("HFrame")) {
						temp.setText(((HFrame) item).getTitle());
					} else if (item.getClass().getSimpleName().equals("JButton")) {
						temp.setText(((JButton) item).getText());
					} else if (item.getClass().getSimpleName().equals("JLabel")) {
						temp.setText(((JLabel) item).getText());
					}
				}
			}
			// JOptionPane.showMessageDialog(null,
			// "Success!","Success!",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class ComboActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int num = -1;
			if (item == null || state == false)
				return;
			String className = item.getClass().getSimpleName();
			if (className.equals("JButton")) {
				num = 0;
			} else if (className.equals("JLabel")) {
				num = 1;
			}

			if (typeBox.getSelectedIndex() == 2) {
				state = false;
				typeBox.setSelectedIndex(num);
				state = true;
				JOptionPane.showMessageDialog(null, "Can not convert to JFrame.", "error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (typeBox.getSelectedIndex() == num)
				return;
			if (typeBox.getSelectedIndex() == 0) {
				JButton temp = new JButton();
				JLabel lbl = (JLabel) item;
				Container parent = lbl.getParent();

				temp.setText(lbl.getText());
				temp.setName(lbl.getName());
				temp.setBounds(lbl.getBounds());
				temp.addMouseListener(itemMouseListener);
				temp.addMouseMotionListener(itemMouseListener);
				temp.addKeyListener(itemKeyListener);
				temp.setOpaque(true);
				temp.setBackground(new Color(0,255,255));
				
				parent.remove(lbl);
				parent.add(temp);
				parent.repaint();
			} else if (typeBox.getSelectedIndex() == 1) {
				JButton btn = (JButton) item;
				JLabel temp = new JLabel();
				Container parent = btn.getParent();

				temp.setText(btn.getText());
				temp.setName(btn.getName());
				temp.setBounds(btn.getBounds());
				temp.setHorizontalAlignment(JLabel.CENTER);
				temp.addMouseListener(itemMouseListener);
				temp.addMouseMotionListener(itemMouseListener);
				temp.addKeyListener(itemKeyListener);
				temp.setOpaque(true);
                temp.setBackground(new Color(255,255,102));

				parent.remove(btn);
				parent.add(temp);
				parent.repaint();
			}
			editorPanel.setSelectedItem(null);
			setProperties(null);

		}
	}

	public void setEditorPanel(EditorPanel editorPanel) {
		this.editorPanel = editorPanel;
		itemMouseListener.setPanel(editorPanel, this);
		itemKeyListener.setPanel(editorPanel, this);
	}

	private JLabel typeLabel;
	private JComboBox<?> typeBox;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel textLabel;
	private JTextField textTextField;
	private JLabel xLabel;
	private JTextField xTextField;
	private JLabel yLabel;
	private JTextField yTextField;
	private JLabel widthLabel;
	private JTextField widthTextField;
	private JLabel heightLabel;
	private JTextField heightTextField;
	private JLabel colorLabel;
	private JLabel colorTypeLabel;
	private JButton colorButton;
	private JComponent item;
	
	private IntKeyListener intKeyListener;
	private StringKeyListener stringKeyListener;
	private ComboActionListener comboActionListener;
	private ItemMouseListener itemMouseListener;
	private ItemKeyListener itemKeyListener;
	private ColorActionListener colorActionListener;
	private EditorPanel editorPanel;
	private boolean state;
}
