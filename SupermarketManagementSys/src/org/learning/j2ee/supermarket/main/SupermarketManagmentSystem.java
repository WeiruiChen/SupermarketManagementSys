package org.learning.j2ee.supermarket.main;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.learning.j2ee.supermarket.dao.SupermarketMySql;
import org.learning.j2ee.supermarket.dao.User;
import org.learning.j2ee.supermarket.dao.UserDao;
import org.learning.j2ee.supermarket.panel.ClockPanel;

public class SupermarketManagmentSystem extends JFrame {
	private BackgroundPanel loginPanel;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	private Point spoint;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							SupermarketManagmentSystem mostly = new SupermarketManagmentSystem();
							mostly.setVisible(true);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SupermarketManagmentSystem() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);

		setLocationRelativeTo(null);// �������

		setTitle("��¼����");

		setBounds(100, 100, 559, 285);

		loginPanel = getLoginPanel();
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(loginPanel);
		loginPanel.setLayout(null);
	}

	/**
	 * ��ʼ����¼���
	 * 
	 * @return
	 */
	private BackgroundPanel getLoginPanel() {
		if (loginPanel == null) {
			loginPanel = new BackgroundPanel();// ������¼������

			loginPanel.config();

			addUserNameLabelAndField();

			addPasswordLabelAndField();

			addLoginButton();

			// URL urlclose = getClass().getResource("close.png");
			// ImageIcon imageIconclose = new ImageIcon(urlclose);

			// �������¼�������
			loginPanel.addMouseListener(new TitleMouseAdapter());
			// �����궯��������

		}
		return loginPanel;
	}

	private void addLoginButton() {
		JButton loginButton = new JButton("");
		URL url = getClass().getResource("enter.png");
		ImageIcon imageIcon = new ImageIcon(url);

		loginButton.setBounds(0, 40, imageIcon.getIconWidth(),
				imageIcon.getIconHeight());
		loginButton.setIcon(imageIcon);
		loginButton.setContentAreaFilled(false); // ȡ���������
		loginButton.setBorder(null); // ȡ���߿�

		loginButton.addActionListener(new ActionListener() { // ��ť�ĵ����¼�
					public void actionPerformed(ActionEvent e) {
						User userToLogin = new User();
						userToLogin.setUserName(userNameTextField.getText());
						userToLogin.setPassword(passwordField.getText()); // ���û���ӵ��û���������Ϊ�������ò�ѯ�û�����

						UserDao userDao = new UserDao();
						try {
							userDao.searchMatching(
									SupermarketMySql.getConnection(),
									userToLogin);
						} catch (SQLException e1) {

							JOptionPane.showMessageDialog(getContentPane(),
									"Error in querying MySQL DB with message "
											+ e1.toString());

						}

						if (userToLogin.getId() > 0) { // �ж��û�����Ƿ����0
							// Session.setUser(user); // ����Session�����User����ֵ
							// RemoveButtomFrame frame = new
							// RemoveButtomFrame(); // �������������
							// frame.setVisible(true); // ��ʾ������
							SupermarketManagmentSystem.this.dispose(); // ���ٵ�¼����
						} else { // ����û�������û������������
							JOptionPane.showMessageDialog(getContentPane(),
									"�û������������"); // ������ʾ��Ϣ
							userNameTextField.setText(""); // �û����ı�������Ϊ��
							passwordField.setText(""); // �����ı�������Ϊ��
						}
					}
				});
		loginButton.setBounds(253, 116, 93, 64);
		loginPanel.add(loginButton);
	}

	private void addPasswordLabelAndField() {
		JLabel passWordLabel = new JLabel("��  �룺");
		passWordLabel.setBounds(40, 158, 54, 15);
		loginPanel.add(passWordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(92, 155, 139, 25);
		loginPanel.add(passwordField);
	}

	private void addUserNameLabelAndField() {
		JLabel userNameLabel = new JLabel("�û�����");
		userNameLabel.setBounds(40, 116, 54, 15);
		loginPanel.add(userNameLabel);

		userNameTextField = new JTextField();
		userNameTextField.setBounds(92, 113, 139, 25);
		userNameTextField.setColumns(10);
		loginPanel.add(userNameTextField);
	}

	private final class TitleMouseAdapter extends MouseAdapter implements
			Serializable {
		public void mousePressed(java.awt.event.MouseEvent e) {
			spoint = e.getPoint();
		}

	}

}
