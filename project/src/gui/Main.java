package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import csv.CSVFormatException;
import csv.ReadCSV;
import csv.WriteCSV;
import delivery.DeliveryException;
import delivery.Manifest;
import stock.StockException;
import stock.Store;
import stock.Item;
import stock.Stock;

public class Main extends JFrame implements ActionListener, Runnable {

	private static final long serialVersionUID = -7031008862559936404L;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;
	
	private static Store store;
	private List<Item> itemList;
	private Manifest manifest;
	
	private boolean itemsLoaded = false;
	
	private JTable table;
	private DefaultTableModel tableModel;
	
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

	private void createGUI(){
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
	    
	    lblCapital = createLabel("Captial: $" + Store.getInstance().getCapital());
	    
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
		tableModel = new DefaultTableModel(); 
		JTable table = new JTable(tableModel); 

		tableModel.addColumn("Name"); 
		tableModel.addColumn("Quantity"); 
		tableModel.addColumn("Cost");
		tableModel.addColumn("Price");
		tableModel.addColumn("Reorder Point");
		tableModel.addColumn("Reorder Amount");
		tableModel.addColumn("Temperature");
		
		table.setFont(table.getFont().deriveFont((float) 16.0));
		
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
	public void actionPerformed(ActionEvent event) {
		Object src=event.getSource(); 
	      
		//Consider the alternatives - not all active at once. 
		if (src== btnLoadItems) {
			try {
				loadItemProperties();
			} catch (StockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CSVFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateTable();		
		}
		
		if (src == btnLoadSales) {
			JFileChooser chooser = new JFileChooser();
			
			int returnValue = chooser.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					store.loadSalesLog(chooser.getSelectedFile().getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (StockException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CSVFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateTable();
			}		
		}
		
		if (src == btnLoadManifest) {
			JFileChooser chooser = new JFileChooser();
			
			int returnValue = chooser.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					Manifest.loadManifest(chooser.getSelectedFile().getAbsolutePath());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (StockException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CSVFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DeliveryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateTable();
			}
		}
		
		if (src == btnExportManifest) {
			String fileName = JOptionPane.showInputDialog("Type your file name");
		
			try {
				WriteCSV.writeManifest(Manifest.automateManifest(Store.getInstance().getInventory()), fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DeliveryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CSVFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateTable();
		}
	}
	
	private void loadItemProperties() throws CSVFormatException, StockException, IOException {
		JFileChooser chooser = new JFileChooser();
		int returnValue = chooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			Store.getInstance().setItemList(ReadCSV.initialiseItems(chooser.getSelectedFile().getAbsolutePath())) ;
		}
	}

	private void updateTable() {
		itemList = null;
		//itemList.addAll(store.getInventory().getStock().keySet());
		Stock stock = store.getInventory();
		// TODO - dont know if this will break anything in the long run it stops a bug from loading the list over and over again
		//if (!itemsLoaded) { 
		tableModel.setRowCount(0);
			for (Item item : stock.getStock().keySet()) {
				Object[] newRowData = new Object[7];
				newRowData[0] = item.getName();
				newRowData[1] = store.getInventory().getStock().get(item);
				newRowData[2] = item.getCost();
				newRowData[3] = item.getPrice();
				newRowData[4] = item.getReorderPoint();
				newRowData[5] = item.getReorderQuantity();
				if (item.getTemp() == null) newRowData[6] = "N/A";
				else newRowData[6] = item.getTemp();
				
				tableModel.addRow(newRowData);	
			//}
			//itemsLoaded = true;
		}
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Main("BorderLayout"));
        store = Store.getInstance();
	}

}
