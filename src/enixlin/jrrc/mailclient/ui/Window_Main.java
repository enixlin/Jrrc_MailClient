package enixlin.jrrc.mailclient.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Window_Main {

	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		frame.setBounds(new Rectangle(0, 0, 1000, 600));
		frame.setSize(new Dimension(1000, 606));
		frame.setBounds(100, 100, 928, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setLocation(10, 10);
		tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabbedPane.setSize(new Dimension(912, 568));
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("邮件列表", null, panel, null);
		panel.setLayout(null);
		
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"收件箱", "发件箱", "", "收件箱（归档）", "发件箱（归档）"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setLocation(8, 7);

		list.setSize(new Dimension(204, 187));
		panel.add(list);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(224, 6, 661, 510);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(8, 206, 204, 27);
		panel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		//选择查询条件
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//按关键字查询
				if(comboBox_1.getSelectedIndex()==1){
					textField.setEnabled(true);
					textField_1.setEnabled(false);				
					textField_2.setEnabled(false);				
				}else{
				//按时间条件查询
					textField.setEnabled(false);
					textField_1.setEnabled(true);				
					textField_2.setEnabled(true);		
				}
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"按时间查询", "按关键字查询"}));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(8, 245, 204, 27);
		panel.add(comboBox_1);
		
		JLabel label = new JLabel("关键字");
		label.setBounds(18, 284, 39, 16);
		panel.add(label);
		
		textField = new JTextField();
		textField.setBounds(69, 279, 143, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("开始时间");
		label_1.setBounds(8, 317, 59, 16);
		panel.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(69, 312, 143, 26);
		panel.add(textField_1);
		
		JLabel label_2 = new JLabel("结束时间");
		label_2.setBounds(8, 350, 62, 16);
		panel.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(69, 345, 143, 26);
		panel.add(textField_2);
		
		JButton button = new JButton("查询");
		button.setBounds(36, 383, 117, 29);
		panel.add(button);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
	}
}
