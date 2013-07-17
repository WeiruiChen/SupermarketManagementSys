package org.learning.j2ee.supermarket.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import org.learning.j2ee.supermarket.panel.ClockPanel;

/**
 * �������
 * 
 * @author
 */
public class BackgroundPanel extends JPanel {
	private Image image;// ����ͼƬ

	public BackgroundPanel() {
		setOpaque(false);
		setLayout(null);// ʹ�þ��Զ�λ���ֿؼ�
	}

	public void config() {
		setOpaque(false);// ���͸��
		setImage(getToolkit().getImage(getClass().getResource("login.png")));// ������屳��ͼƬ

		setLayout(null);

		JPanel clockPanel = new ClockPanel();
		clockPanel.setBounds(377, 54, 151, 142);
		add(clockPanel);
	}

	/**
	 * ���ñ���ͼƬ����ķ���
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * ��������
	 */
	protected void paintComponent(Graphics g) {
		if (image != null) {// ���ͼƬ�Ѿ���ʼ��
			// ����ͼƬ
			g.drawImage(image, 0, 0, this);
		}
		super.paintComponent(g);
	}

}
