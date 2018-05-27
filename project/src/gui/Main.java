package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = -7031008862559936404L;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;
	
	private JTable table;
	
	private JScrollPane scrollPane;
	
	private JPanel pnlBtn;
	private JPanel pnlThree;
	private JPanel pnlFour;
	private JPanel pnlFive;
	
	private JButton btnLoadItems;
	private JButton btnExportManifest;
	private JButton btnLoadManifest;
	private JButton btnLoadSales;
	
	private JLabel lblCapital; 
	
	public Main(String title) throws HeadlessException {
		super(title);
	}

	private void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    setTitle("Store Manager");
	    
	    table = createTable();
	    scrollPane = new JScrollPane(table);
	    pnlThree = createPanel(Color.LIGHT_GRAY);
	    pnlBtn = createPanel(Color.LIGHT_GRAY);
	    pnlFour = createPanel(Color.LIGHT_GRAY);
	    pnlFive = createPanel(Color.LIGHT_GRAY);
	    
	    btnLoadItems = createButton("Load Items");
	    btnExportManifest = createButton("Export Manifest");
	    btnLoadManifest = createButton("Load Manifest");
	    btnLoadSales = createButton("Load Sales Log");
	    
	    lblCapital = createLabel("Captial:");
	    
	    //pnlDisplay.setLayout(new BorderLayout());
	    pnlThree.add(lblCapital);
	 
	    layoutButtonPanel(); 
	    
	    this.getContentPane().add(scrollPane,BorderLayout.CENTER);
	    this.getContentPane().add(pnlThree,BorderLayout.NORTH);
	    this.getContentPane().add(pnlBtn,BorderLayout.WEST);
	    this.getContentPane().add(pnlFour,BorderLayout.EAST);
	    this.getContentPane().add(pnlFive,BorderLayout.SOUTH);
	    repaint(); 
	    this.setVisible(true);
	}
	
	private JTable createTable() {
		DefaultTableModel model = new DefaultTableModel(); 
		JTable table = new JTable(model); 

		model.addColumn("Name"); 
		model.addColumn("Quantity"); 
		model.addColumn("Cost");
		model.addColumn("Price");
		model.addColumn("Reorder Point");
		model.addColumn("Reorder Amount");
		model.addColumn("Temperature");
		
		return table;
	}

	private JPanel createPanel(Color color) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		return panel;
	}
	
	private JButton createButton(String string) {
		JButton button = new JButton(string); 
		button.addActionListener(this);
		return button; 
	}
	
	private JLabel createLabel(String string) {
		JLabel label = new JLabel(string);
		label.setFont(label.getFont().deriveFont((float) 24.0));
		return label;
	}

	private void layoutButtonPanel() {
		BoxLayout layout = new BoxLayout(pnlBtn, BoxLayout.PAGE_AXIS);
	    pnlBtn.setLayout(layout);
	    
	    addBtn(btnLoadItems);
	    pnlBtn.add(Box.createRigidArea(new Dimension(0,50)));
	    addBtn(btnExportManifest);
	    pnlBtn.add(Box.createRigidArea(new Dimension(0,15)));
	    addBtn(btnLoadManifest);
	    pnlBtn.add(Box.createRigidArea(new Dimension(0,50)));
	    addBtn(btnLoadSales);
	}

	private void addBtn(JButton button) {
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFont(button.getFont().deriveFont((float) 24.0));
        pnlBtn.add(button);
	}

	@Override
	public void run() {
		createGUI(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Main("BorderLayout"));
	}

}