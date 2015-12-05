package enixlin.jrrc.mailclient.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.AbstractListModel;

public class Window_Main {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					Window_Main window = new Window_Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window_Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 928, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setLocation(10, 10);
		tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabbedPane.setSize(new Dimension(890, 528));
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(null);
		
		JList list = new JList();
		list.setLocation(10, 5);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"123", "12323", "厅"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSize(new Dimension(204, 484));
		panel.add(list);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(224, 6, 551, 483);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
	}
}
