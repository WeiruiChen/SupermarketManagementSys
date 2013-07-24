package org.learning.j2ee.supermarket.widget;

import static javax.swing.BorderFactory.createEmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 * �ƶ����
 * 
 * @author
 */
public class SmallScrollPanel extends BGPanel {
	private static final long serialVersionUID = 3592329256836525981L;
	private AlphaScrollPane alphaScrollPane;// �������
	private JButton leftScrollButton = null;// ���΢����ť
	private JButton rightScrollButton = null;// �Ҳ�΢����ť
	private ScrollMouseAdapter scrollMouseAdapter; // �����¼�������
	private ImageIcon icon1;
	private ImageIcon icon2;

	/**
	 * ���췽��
	 */
	public SmallScrollPanel() {
		scrollMouseAdapter = new ScrollMouseAdapter();// ��ʼ��������
		// ��ʼ��������ͼ
		icon1 = new ImageIcon(getClass().getResource("/org/learning/j2ee/supermarket/util/buttonIcons/top01.png"));
		icon2 = new ImageIcon(getClass().getResource("/org/learning/j2ee/supermarket/util/buttonIcons/top01.png"));
		setIcon(icon1);// ������ͼ
		setIconFill(BOTH_FILL);// ��ͼ��������Ӧ�����С
		initialize();// ���ó�ʼ������
	}

	/**
	 * ��ʼ���������ķ���
	 */
	private void initialize() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(0);
		this.setLayout(borderLayout);// ���ò��ֹ�����
		this.setSize(new Dimension(300, 84));
		this.setOpaque(true);// ʹ�ؼ�͸��
		// ��ӹ�����嵽�������λ��
		this.add(getAlphaScrollPanel(), BorderLayout.CENTER);
		// ������΢����ť
		this.add(getLeftScrollButton(), BorderLayout.WEST);
		// ����Ҳ�΢����ť
		this.add(getRightScrollButton(), BorderLayout.EAST);
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	public AlphaScrollPane getAlphaScrollPanel() {
		if (alphaScrollPane == null) {
			alphaScrollPane = new AlphaScrollPane();
			// ���ó�ʼ��С
			alphaScrollPane.setPreferredSize(new Dimension(564, 69));
			// ����ʾ��ֱ������
			alphaScrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			// ����ʾˮƽ������
			alphaScrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			// ȡ���������߿�
			alphaScrollPane.setBorderPaint(false);
			// ����¼�������
			alphaScrollPane
					.addComponentListener(new ScrollButtonShowListener());
		}
		return alphaScrollPane;
	}

	public void setViewportView(Component view) {
		alphaScrollPane.setViewportView(view);
	}

	/**
	 * ����΢��������
	 * 
	 * @author
	 */
	private class ScrollButtonShowListener extends ComponentAdapter implements
			Serializable {
		private static final long serialVersionUID = 814596372430146361L;

		@Override
		public void componentResized(ComponentEvent e) {
			// ��ȡ���������
			JScrollBar scrollBar = alphaScrollPane.getHorizontalScrollBar();
			// ��ȡ��Χ���Ʋ���
			int scrollWidth = scrollBar.getMaximum();
			int paneWidth = alphaScrollPane.getWidth();
			// ���������ڰ������ݵ�ʱ����������΢����ť
			if (paneWidth >= scrollWidth) {
				getLeftScrollButton().setVisible(false);
				getRightScrollButton().setVisible(false);
			}
			// ������С�ڰ������ݵ�ʱ����ʾ����΢����ť
			if (paneWidth < scrollWidth) {
				getLeftScrollButton().setVisible(true);
				getRightScrollButton().setVisible(true);
			}
		}
	}

	/**
	 * �������΢����ť
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLeftScrollButton() {
		if (leftScrollButton == null) {
			leftScrollButton = new JButton();
			leftScrollButton.setOpaque(true);// ��ť͸��
			// ���ñ߿�
			leftScrollButton.setBorder(createEmptyBorder(0, 10, 0, 0));
			// ���ð�ťͼ��
			leftScrollButton
					.setIcon(new ImageIcon(
							SmallScrollPanel.class
									.getResource("/org/learning/j2ee/supermarket/util/buttonIcons/zuoyidongoff.png")));
			leftScrollButton
					.setPressedIcon(new ImageIcon(
							SmallScrollPanel.class
									.getResource("/org/learning/j2ee/supermarket/util/buttonIcons/zuoyidongon.png")));
			leftScrollButton
					.setRolloverIcon(new ImageIcon(
							SmallScrollPanel.class
									.getResource("/org/learning/j2ee/supermarket/util/buttonIcons/zuoyidongon.png")));
			// ȡ����ť�������
			leftScrollButton.setContentAreaFilled(false);
			// ���ó�ʼ��С
			leftScrollButton.setPreferredSize(new Dimension(38, 0));
			// ȡ����ť���㹦��
			leftScrollButton.setFocusable(false);
			// ��ӹ����¼�������
			leftScrollButton.addMouseListener(scrollMouseAdapter);
		}
		return leftScrollButton;
	}

	/**
	 * �����Ҳ����΢����ť
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRightScrollButton() {
		if (rightScrollButton == null) {
			rightScrollButton = new JButton();
			// ��ť͸��
			rightScrollButton.setOpaque(false);
			// ���ñ߿�
			rightScrollButton.setBorder(createEmptyBorder(0, 0, 0, 10));
			rightScrollButton
					.setIcon(new ImageIcon(
							SmallScrollPanel.class
									.getResource("/org/learning/j2ee/supermarket/util/buttonIcons/youyidongoff.png")));// ���ð�ťͼ��
			rightScrollButton
					.setPressedIcon(new ImageIcon(
							SmallScrollPanel.class
									.getResource("/org/learning/j2ee/supermarket/util/buttonIcons/youyidongon.png")));
			rightScrollButton
					.setRolloverIcon(new ImageIcon(
							SmallScrollPanel.class
									.getResource("/org/learning/j2ee/supermarket/util/buttonIcons/youyidongon.png")));
			// ȡ����ť���ݻ���
			rightScrollButton.setContentAreaFilled(false);
			// ���ð�ť��ʼ��С
			rightScrollButton.setPreferredSize(new Dimension(38, 92));
			// ȡ����ť���㹦��
			rightScrollButton.setFocusable(false);
			// ��ӹ����¼�������
			rightScrollButton.addMouseListener(scrollMouseAdapter);
		}
		return rightScrollButton;
	}

	/**
	 * ����΢����ť���¼�������
	 * 
	 * @author
	 */
	private final class ScrollMouseAdapter extends MouseAdapter implements
			Serializable {
		private static final long serialVersionUID = 5589204752770150732L;
		// ��ȡ��������ˮƽ������
		JScrollBar scrollBar = getAlphaScrollPanel().getHorizontalScrollBar();
		// �����߳̿��Ʊ���
		private boolean isPressed = true;

		public void mousePressed(MouseEvent e) {
			Object source = e.getSource();// ��ȡ�¼�Դ
			isPressed = true;
			// �ж��¼�Դ����ఴť�����Ҳఴť����ִ����Ӧ����
			if (source == getLeftScrollButton()) {
				scrollMoved(-1);
			} else {
				scrollMoved(1);
			}
		}

		/**
		 * �ƶ��������ķ���
		 * 
		 * @param orientation
		 *            �ƶ����� -1��������ƶ���1���һ����ƶ�
		 */
		private void scrollMoved(final int orientation) {
			new Thread() {// �����µ��߳�
				// ����ԭ�й�������ֵ
				private int oldValue = scrollBar.getValue();

				public void run() {
					while (isPressed) {// ѭ���ƶ����
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						// ��ȡ��������ǰֵ
						oldValue = scrollBar.getValue();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								// ���ù������ƶ�3������
								scrollBar.setValue(oldValue + 3 * orientation);
							}
						});
					}
				}
			}.start();
		}

		public void mouseExited(java.awt.event.MouseEvent e) {
			isPressed = false;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isPressed = false;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// ������������ͼƬ
		g.drawImage(icon2.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
