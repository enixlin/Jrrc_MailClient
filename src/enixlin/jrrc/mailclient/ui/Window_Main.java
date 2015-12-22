package enixlin.jrrc.mailclient.ui;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.gdbeim.oa.applet.DownloadTask;
import com.gdbeim.oa.applet.HttpGet;

import enixlin.jrrc.mailclient.model.TableModel;

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
import javax.swing.RowSorter;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ItemEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ListSelectionEvent;

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
		frame.setBounds(new Rectangle(0, 0, 1200, 600));
		frame.setSize(new Dimension(1085, 606));
		frame.setBounds(10, 10, 1200, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setLocation(10, 10);
		tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabbedPane.setSize(new Dimension(1200, 568));
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("邮件列表", null, panel, null);
		panel.setLayout(null);

		JList<String> list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedIndex() == 0) {
					String uid = "42546";
					String start = "2015-1-1";
					String end = "2015-12-31";
					String ListType = "收件箱";
					Object list[][] = getTableList(uid, ListType, start, end);
					initTitleList(list, "1");
				}else{
					String uid = "42546";
					String start = "2015-1-1";
					String end = "2015-12-31";
					String ListType = "发件箱";
					Object list[][] = getTableList(uid, ListType, start, end);
					initTitleList(list, "2");
				}
			}
		});
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "收件箱", "发件箱", "", "收件箱（归档）", "发件箱（归档）" };

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
		scrollPane.setBounds(224, 6, 949, 510);
		panel.add(scrollPane);

		table = new JTable();

		scrollPane.setViewportView(table);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(8, 206, 204, 27);
		panel.add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		// 选择查询条件
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// 按关键字查询
				if (comboBox_1.getSelectedIndex() == 1) {
					textField.setEnabled(true);
					textField_1.setEnabled(false);
					textField_2.setEnabled(false);
				} else {
					// 按时间条件查询
					textField.setEnabled(false);
					textField_1.setEnabled(true);
					textField_2.setEnabled(true);
				}
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "按时间查询", "按关键字查询" }));
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

	/**
	 * 取得邮件列表
	 * 
	 * @param uid
	 *            用户ＩＤ
	 * @param ListType
	 *            String ListType 发件箱、收件箱
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return object[][] data 邮件列表
	 */
	protected Object[][] getTableList(String uid, String ListType, String start, String end) {
		// TODO Auto-generated method stub
		DownloadTask task = new DownloadTask();
		String strUrl = "http://96.0.32.11/oa/messageDownload?method=getDownList&startDate=" + start + "&endDate=" + end
				+ "&userId=" + uid;
		task = new HttpGet().getDownloadTask(strUrl);
		java.util.List arrayList = null;
		if (ListType == "收件箱") {
			arrayList = (java.util.List) task.getReceiveList();
		}
		if (ListType == "发件箱") {
			arrayList = (java.util.List) task.getSendList();
		}
		
		long size=arrayList.size();
		Object row[]=(Object[]) arrayList.get(0);
		long length=row.length;
		
		Object[][] data=new Object[(int) size][(int) length];
		for (int n = 0; n < arrayList.size(); n++) {
			Object objects[] = (Object[]) arrayList.get(n);
			int e = 0;
			System.out.println(objects[0]);
			System.out.println(objects[1]);
			System.out.println(objects[2]);

			data[n][0] = objects[0];
			data[n][1] = objects[1];
			data[n][2] = objects[2];
		}
		return data;
	}

	/**
	 * 始初化邮件收件箱和发件箱列表
	 * 
	 * @param list
	 *            邮件列表
	 * @param listtype
	 *            收件箱 ＝1 或 发件箱 ＝2
	 */
	public void initTitleList(Object[][] list, String type) {
		TableModel tableModel = new TableModel();
		Object[] columns=new Object[3] ;

		if (type == "1") {
			columns[0] = "收件箱";
			columns[1] = "日期时间";
			columns[2] = "邮件标题";
		} else {
			columns[0] = "发件箱";
			columns[1] = "日期时间";
			columns[2] = "邮件标题";
		}
		tableModel.setDataVector(list, columns);

		RowSorter sorter = new TableRowSorter(tableModel);
		table.setRowSorter(sorter);
		
		table.setModel(tableModel);

	}
}
