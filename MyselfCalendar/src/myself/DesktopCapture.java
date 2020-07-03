package myself;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DesktopCapture extends JFrame implements ActionListener {

	/**
	 * 1.�ı�˫�����̽�ͼΪ�������̽�ͼ 2.�޸������̺��޷�ӦBUG 3.���Ŵ���ʾ���������ϽǺ����Ͻ�
	 */
	private static final long serialVersionUID = 1L;
	JButton confirm;
	BufferedImage desktopImg;
	CaptureView view;
	boolean iconed = false;

	public DesktopCapture() {
		capture();
	}

	void init() {
		setContentPane(new ImagePanel("image/weixin_bg.jpg"));
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		confirm = new JButton("��ͼ");
		confirm.addActionListener(this);
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		jp.setOpaque(false);
		jp.add(new ImageLabel("image/blackCat.png"));
		jp.add(confirm);
		jp.add(new ImageLabel("image/whiteCat.png"));
		JTextArea jta = new JTextArea();
		jta.setOpaque(false);
		jta.setEditable(false);
		jta.setForeground(Color.WHITE);
		jta.setFont(new Font("����", Font.PLAIN, 11));
		jta
				.setText("������ʾ:--> 1.��������ͼ��-->�����ͼ״̬\n\t2.˫���Ҽ�------->�˳���ͼ״̬\n\t3.˫�����------->�����ͼ\n\t4.�����Ҽ�---->����ѡ���ͼ����   ^_^");
		con.add(jp, BorderLayout.CENTER);
		con.add(jta, BorderLayout.SOUTH);

	}

	// �����ĻͼƬ
	public void captureDesktop() throws Exception {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(d);
		desktopImg = new BufferedImage((int) d.getWidth(), (int) d.getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		Robot robot = new Robot(device);
		desktopImg = robot.createScreenCapture(rect);
	}

	// ��ͼ
	public void capture() {
		this.setVisible(false);
		try {
			Thread.sleep(200);
			captureDesktop();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (view == null) {
			view = new CaptureView(this, desktopImg);
		} else {
			view.refreshBackGround(desktopImg);
		}
	}

	// �����ͼ
	public void saveCapture(int x1, int y1, int x2, int y2) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("*.gif", "gif"));
		chooser.setFileFilter(new FileNameExtensionFilter("*.bmp", "bmp"));
		chooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
		chooser.setSelectedFile(new File(chooser.getCurrentDirectory(), "��ͼ"));
		int state = chooser.showSaveDialog(this);
		if (state == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			String descrition = filter.getDescription();
			String endWith = descrition.substring(2);
			captureToFile(desktopImg.getSubimage(x1, y1, Math.abs(x2 - x1),
					Math.abs(y2 - y1)), endWith, new File(file
					.getAbsoluteFile()
					+ "." + endWith));
		}
	}

	// ��ͼ������ļ�
	public void captureToFile(BufferedImage img, String endWith, File file) {
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			ImageIO.write(img, endWith, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "��ͼ�������...");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("��ͼ")) {
			capture();
		}
	}

}