package myself;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JTextArea;
import javax.swing.JWindow;

public class CaptureView extends JWindow implements MouseListener, KeyListener,
		MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage desktopImg;
	private boolean captured = false, draging = false, toolPanelAtRight = true;
	private int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 1, y2 = 1; // ����ԭ������,ѡ�����ϽǺ����½�����
	private int point_x, point_y; // ��������
	private Color point_color; // ������ɫ
	private DesktopCapture window; // ������ͼ����������
	private ImagePanel toolPanel; // ��ʾ����
	private final int TOOLPANEL_WIDTH = 200, TOOLPANEL_HEIGHT = 300,
			HALF_PICK_IMG = 40;
	private JTextArea infoArea; // ��ʾ��
	private ToolImagePanel pickImgPanel; // �Ŵ�

	CaptureView(DesktopCapture window, BufferedImage img) {
		super(window);
		this.window = window;
		this.desktopImg = img;
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		init();
		setVisible(true);
		setAlwaysOnTop(true);
		this.requestFocus();
	}

	void init() {
		this.setContentPane(new BackGroundPanel(desktopImg));
		setLayout(null);
		toolPanel = new ImagePanel("image/weixin_bg.jpg");
		toolPanel.setLayout(new BorderLayout());
		pickImgPanel = new ToolImagePanel();
		infoArea = new JTextArea();
		infoArea.setOpaque(false);
		infoArea.setEditable(false);
		infoArea.setForeground(Color.WHITE);
		infoArea.setFont(new Font("����", Font.PLAIN, 11));
		infoArea.setText("");
		toolPanel.add(pickImgPanel, BorderLayout.CENTER);
		toolPanel.add(infoArea, BorderLayout.SOUTH);
		toolPanel.setLocation(getWidth() - TOOLPANEL_WIDTH, 0);
		toolPanel.setSize(TOOLPANEL_WIDTH, TOOLPANEL_HEIGHT);
		this.getLayeredPane().add(toolPanel, 300);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	// ������Ļ����
	public void refreshBackGround(BufferedImage img) {
		this.desktopImg = img;
		this.setContentPane(new BackGroundPanel(desktopImg));
		setVisible(true);
		setAlwaysOnTop(true);
		this.requestFocus();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		if (captured == true) {
			if (draging) {
				// ��ͼ����ʮ����
				g.drawLine(point_x, 0, point_x, getHeight());
				g.drawLine(0, point_y, getWidth(), point_y);
			}
			confirmArea(); // ȷ����ͼѡ�������Ͻ�����(x1,y1)�����½�����(x2,y2)
			if (x1 < x2 && y1 < y2)
				g.drawImage(desktopImg.getSubimage(x1, y1, Math.abs(x2 - x1),
						Math.abs(y2 - y1)), x1, y1, null);
			g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
		} else {
			g.drawLine(point_x, 0, point_x, getHeight());
			g.drawLine(0, point_y, getWidth(), point_y);
		}
		repaintToolPanel();
	}

	// ȷ����ʾ��λ�ò��ػ�
	public void repaintToolPanel() {

		if (toolPanelAtRight == true) {
			if (point_x > (getWidth() - TOOLPANEL_WIDTH - 100)
					&& point_y < (TOOLPANEL_HEIGHT + 100)) {
				toolPanel.setLocation(0, 0);
				toolPanelAtRight = false;
			}
		} else {
			if (point_x < (TOOLPANEL_WIDTH + 100)
					&& point_y < (TOOLPANEL_HEIGHT + 100)) {
				toolPanel.setLocation(getWidth() - TOOLPANEL_WIDTH, 0);
				toolPanelAtRight = true;
			}
		}
		// ����point��״̬
		point_color = new Color(desktopImg.getRGB(point_x, point_y));

		refreshInfoText(); // ������ʾ������

	}

	// ��ʾ��״̬��Ϣ��ˢ��
	public void refreshInfoText() {

		// �ı���Ϣ
		String text = new String(
				"������ʾ: By MaiLingFeng  =^-^=\n1.��������ͼ��->�����ͼ״̬\n2.˫���Ҽ�------>�˳���ͼ״̬\n3.˫�����------>�����ͼ\n4.�����Ҽ�---->����ѡ���ͼ����");
		String infoString;
		int captureWidth, captureHeight;
		if (captured == true) {
			captureWidth = x2 - x;
			captureHeight = y2 - y;
		} else {
			captureWidth = 0;
			captureHeight = 0;
		}
		infoString = "X,Y : " + point_x + "," + point_y + "    W*H : "
				+ captureWidth + "*" + captureHeight + "\n��ǰRBG:("
				+ point_color.getRed() + "," + point_color.getGreen() + ","
				+ point_color.getBlue() + ")\n" + text;
		infoArea.setText(infoString);

		// �Ŵ���Ϣ
		int pick_x1, pick_y1, pick_x2, pick_y2, pickImg_x, pickImg_y;

		if (point_x - HALF_PICK_IMG < 0) { // ��÷Ŵ�ͼƬ�ļ�ȡ���ϽǺ����½�����,�Լ��ڷŴ��е����Ͻ�λ������
			pick_x1 = 0;
			pick_x2 = point_x + HALF_PICK_IMG;
			pickImg_x = HALF_PICK_IMG - point_x;
		} else if (point_x + HALF_PICK_IMG > this.getWidth()) {
			pick_x1 = point_x - HALF_PICK_IMG;
			pick_x2 = this.getWidth();
			pickImg_x = 0;
		} else {
			pick_x1 = point_x - HALF_PICK_IMG;
			pick_x2 = point_x + HALF_PICK_IMG;
			pickImg_x = 0;
		}

		if (point_y - HALF_PICK_IMG < 0) {
			pick_y1 = 0;
			pick_y2 = point_y + HALF_PICK_IMG;
			pickImg_y = HALF_PICK_IMG - point_y;
		} else if (point_y + HALF_PICK_IMG > this.getHeight()) {
			pick_y1 = point_y - HALF_PICK_IMG;
			pick_y2 = this.getHeight();
			pickImg_y = 0;
		} else {
			pick_y1 = point_y - HALF_PICK_IMG;
			pick_y2 = point_y + HALF_PICK_IMG;
			pickImg_y = 0;
		}

		BufferedImage pickImg = new BufferedImage(HALF_PICK_IMG * 2,
				HALF_PICK_IMG * 2, BufferedImage.TYPE_INT_RGB);
		Graphics pickGraphics = pickImg.getGraphics();
		pickGraphics.drawImage(desktopImg.getSubimage(pick_x1, pick_y1, pick_x2
				- pick_x1, pick_y2 - pick_y1), pickImg_x, pickImg_y,
				Color.black, null);
		pickImgPanel.refreshImg(pickImg.getScaledInstance(TOOLPANEL_WIDTH,
				TOOLPANEL_WIDTH, BufferedImage.SCALE_AREA_AVERAGING));
		toolPanel.validate();
	}

	// ȷ����������ϵ�����½�����
	public void confirmArea() {
		int temp;
		// ��x,yΪ��ͼѡ�����Ͻ������ֵ,�������Ͻ�x1,y1�����½�x2,y2������
		x1 = x;
		y1 = y;
		if (x2 < x1) {// 2,3
			if (y2 < y1) { // 2
				temp = x1;
				x1 = x2;
				x2 = temp;
				temp = y1;
				y1 = y2;
				y2 = temp;
			} else { // 4
				temp = x1;
				x1 = x2;
				x2 = temp;
			}
		} else { // 1,4
			if (y2 < y1) { // 1
				temp = y1;
				y1 = y2;
				y2 = temp;
			}
		}
	}

	public void exit() {

		x = 0;
		y = 0;
		x1 = 0;
		y1 = 0;
		x2 = 1;
		y2 = 1;
		point_x = 0;
		point_y = 0;
		captured = false;
		draging = false;
		toolPanel.setLocation(getWidth() - TOOLPANEL_WIDTH, 0);
		toolPanelAtRight = true;
		this.setVisible(false);
		// this.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// ˫�����,ȷ��ѡ��ͼ��ѡ��,�����ͼ
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getClickCount() == 2) {
				this.setVisible(false);
				window.toFront();
				window.saveCapture(x1, y1, x2, y2);
				if (!window.iconed)
					window.setVisible(false);
					//window.setVisible(true);
				exit();
			}
		} else if (e.getClickCount() == 2) {
			if (!window.iconed) {
				// window.setVisible(true);
				window.setVisible(false);
				window.toFront();
			}
			exit();
		}
		if (window.isActive()) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// �����δѡ�����ʱ����,ȷ��ѡ�����
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (captured == false) {
				point_x = e.getX();
				point_y = e.getY();
				x = point_x; // ѡ���������
				y = point_y;
				draging = true;
				captured = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// ����ͷ�,ȷ����ͼѡ��
		// �Ҽ��ͷ�,����ѡ��
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (draging == true) {
				point_x = e.getX();
				point_y = e.getY();
				x2 = point_x;
				y2 = point_y;
				repaint();
				draging = false;
			}
		} else {
			draging = false;
			captured = false;
			point_x = e.getX();
			point_y = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		// ������ͼʱ������
		if (draging == true) {
			point_x = e.getX();
			point_y = e.getY();
			x2 = point_x;
			y2 = point_y;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// δ������ͼʱ,��������
		if (!captured) {
			point_x = e.getX();
			point_y = e.getY();
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == KeyEvent.VK_CANCEL) {
			this.setVisible(false);
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("aaa");
		if (e.getKeyCode() == KeyEvent.VK_A) {
			this.setVisible(false);
			this.dispose();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
			this.dispose();
		}
	}

}

