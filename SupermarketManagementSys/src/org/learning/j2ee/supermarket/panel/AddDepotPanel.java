package org.learning.j2ee.supermarket.panel;

import static javax.swing.BorderFactory.createTitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.learning.j2ee.supermarket.dao.JoinDepot;
import org.learning.j2ee.supermarket.dao.JoinDepotDao;
import org.learning.j2ee.supermarket.dao.SupermarketMySql;

public class AddDepotPanel extends JPanel {
	private JTextField dateTextField;
	private final JoinDepotDao model = new JoinDepotDao();
	private JTable table_1;
	private JoinDepotDao dao = new JoinDepotDao();
	private JComboBox comboBox;
	private JTable stockTable;

	/**
	 * Create the panel.
	 */
	public AddDepotPanel() {
		this.setBorder(createTitledBorder(null, "�ֿ����",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font(
						"sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
		setSize(631, 413);
		setLayout(null);
		this.setBackground(new Color(71, 201, 223));
		JLabel conditionLabel = new JLabel("�����ţ�");
		conditionLabel.setBounds(57, 86, 66, 15);
		add(conditionLabel);

		dateTextField = new JTextField();
		dateTextField.setBounds(347, 83, 156, 25);
		add(dateTextField);
		dateTextField.setColumns(10);

		JButton findButton = new JButton("����");
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oid = comboBox.getSelectedItem().toString();
				String joinDate = dateTextField.getText();
				if (oid.equals("") && (joinDate.equals(""))) {
					JOptionPane.showMessageDialog(getParent(), "û����д��ѯ������",
							"��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (!oid.equals("") && (joinDate.equals(""))) {
					JoinDepot searchTargetDepot = new JoinDepot();
					searchTargetDepot.setOrderId(oid);

					List list = searchMatchingRecords(searchTargetDepot);

					for (int i = 0; i < list.size(); i++) {
						JoinDepot depot = (JoinDepot) list.get(i);
						String dRemark = depot.getRemark();
						if (dRemark.length() > 4) {
							dRemark = dRemark.substring(0, 4) + "...";
						}
						JoinDepot newJoinDepotRecord = new JoinDepot();
						newJoinDepotRecord.setAll(depot.getId(),
								depot.getOrderId(), depot.getDepoId(),
								depot.getWareName(), depot.getJoinTime(),
								depot.getWeight(), dRemark);
						try {
							model.create(SupermarketMySql.getConnection(),
									newJoinDepotRecord);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else if (oid.equals("") && (!joinDate.equals(""))) {
					JoinDepot searchTargetDepot = new JoinDepot();
					searchTargetDepot.setJoinTime(joinDate.toString());
					
					List matchingRecords = searchMatchingRecords(searchTargetDepot);

					displaySearchResult(matchingRecords);

				} else if (!oid.equals("") && (!joinDate.equals(""))) {
					JoinDepot searchTargetDepot = new JoinDepot();
					searchTargetDepot.setJoinTime(joinDate.toString());
					List list = searchMatchingRecords(searchTargetDepot);

					for (int i = 0; i < list.size(); i++) {
						JoinDepot depot = (JoinDepot) list.get(i);
						String dRemark = depot.getRemark();
						if (dRemark.length() > 4) {
							dRemark = dRemark.substring(0, 4) + "...";
						}
						JoinDepot newJoinDepotRecord = new JoinDepot();
						newJoinDepotRecord.setAll(depot.getId(),
								depot.getOrderId(), depot.getDepoId(),
								depot.getWareName(), depot.getJoinTime(),
								depot.getWeight(), dRemark);
						try {
							model.create(SupermarketMySql.getConnection(),
									newJoinDepotRecord);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}

			private List searchMatchingRecords(JoinDepot searchTargetDepot) {
				List matchingRecords = null;
				try {
					matchingRecords = dao.searchMatching(
							SupermarketMySql.getConnection(),
							searchTargetDepot);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return matchingRecords;
			}
		});
		
		addFindButton(findButton);

		addInsertButton();
		
		addUpdateButton();

		addDeleteButton();

		addSearchCriteriaInputArea();

		initializeStockDisplayTable();
	}

	private void addFindButton(JButton findButton) {
		findButton.setBounds(513, 82, 93, 23);
		add(findButton);
	}

	private void addSearchCriteriaInputArea() {
		JLabel dateLabel = new JLabel("���ʱ�䣺");
		dateLabel.setBounds(282, 86, 66, 15);
		add(dateLabel);
		
		List lists = null;
		try {
			lists = dao.loadAll(SupermarketMySql.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] orderId = new String[lists.size() + 1];
		orderId[0] = "";
		for (int i = 0; i < lists.size(); i++) {
			orderId[i + 1] = ((JoinDepot) (lists.get(i))).getOrderId();
		}
		comboBox = new JComboBox(orderId);
		comboBox.setBounds(110, 83, 162, 21);
		add(comboBox);
	}

	private void addDeleteButton() {
		JButton deleteButton = new JButton("ɾ��");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(getParent(), "û��ѡ��Ҫ�h�������ݣ�",
							"��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					// String column = model.getValueAt(row, 0).toString();
					// dao.deleteJoinDepot(Integer.parseInt(column));
					JOptionPane.showMessageDialog(getParent(), "����ɾ���ɹ���",
							"��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					repaint();

				}
			}
		});
		deleteButton.setBounds(380, 369, 66, 23);
		add(deleteButton);
	}

	private void addInsertButton() {
		JButton insertButton = new JButton("���");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// InserJoinDepotFrame insertJoin = new InserJoinDepotFrame();
				// insertJoin.setVisible(true);
			}
		});
		insertButton.setBounds(167, 369, 66, 23);
		add(insertButton);
	}

	private void addUpdateButton() {
		JButton updateButton = new JButton("�޸�");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(getParent(), "û��ѡ��Ҫ�޸ĵ����ݣ�",
							"��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					File file = new File("file.txt");
					// try {
					// String column = model.getValueAt(row, 0).toString();
					// file.createNewFile();
					// FileOutputStream out = new FileOutputStream(file);
					// out.write((Integer.parseInt(column)));
					// out.close();
					// UpdateJoinDepotFrame update = new UpdateJoinDepotFrame();
					// update.setVisible(true);
					// repaint();
					// } catch (Exception ee) {
					// ee.printStackTrace();
					// }
				}
			}
		});
		updateButton.setBounds(282, 369, 66, 23);
		add(updateButton);
	}

	private void initializeStockDisplayTable() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(68, 150, 534, 199);
		add(scrollPane);

		String[] columnNames = { "���", "�������", "�ֿ�����", "��Ʒ����", "���ʱ��", "��Ʒ����",
				"��ע��Ϣ" };
		String[][] data = {};

		stockTable = new JTable(data, columnNames);
		stockTable.setModel(new DefaultTableModel(data, columnNames));
		scrollPane.setViewportView(stockTable);
	}

	protected void displaySearchResult(List matchingRecords) {
		for (Object record : matchingRecords) {
			addNewStockInTable(((JoinDepot) record).getId(),
					((JoinDepot) record).getOrderId(),
					((JoinDepot) record).getDepoId(),
					((JoinDepot) record).getWareName(),
					((JoinDepot) record).getJoinTime(),
					((JoinDepot) record).getWeight(),
					((JoinDepot) record).getRemark());
		}

	}

	private void addNewStockInTable(int id, String orderId, int depoId,
			String wareName, String joinTime, double weight, String remark) {
		DefaultTableModel defaultTableModel = (DefaultTableModel) (stockTable.getModel());

		Vector<String> newRow = new Vector<>();

		newRow.add(Integer.toString(id));
		newRow.add(orderId);
		newRow.add(Integer.toString(depoId));
		newRow.add(wareName);
		newRow.add(joinTime);
		newRow.add(Double.toString(weight));
		newRow.add(remark);

		defaultTableModel.addRow(newRow);

	}
}
