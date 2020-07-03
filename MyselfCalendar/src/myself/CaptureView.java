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
	private int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 1, y2 = 1; // 坐标原点坐标,选区左上角和右下角坐标
	private int point_x, point_y; // 鼠标点坐标
	private Color point_color; // 鼠标点颜色
	private DesktopCapture window; // 所属截图工具主窗体
	private ImagePanel toolPanel; // 提示整框
	private final int TOOLPANEL_WIDTH = 200, TOOLPANEL_HEIGHT = 300,
			HALF_PICK_IMG = 40;
	private JTextArea infoArea; // 提示区
	private ToolImagePanel pickImgPanel; // 放大镜

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
		infoArea.setFont(new Font("楷体", Font.PLAIN, 11));
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

	// 桌面屏幕更新
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
				// 截图辅助十字线
				g.drawLine(point_x, 0, point_x, getHeight());
				g.drawLine(0, point_y, getWidth(), point_y);
			}
			confirmArea(); // 确定截图选区的左上角坐标(x1,y1)和右下角坐标(x2,y2)
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

	// 确定提示框位置并重画
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
		// 计算point的状态
		point_color = new Color(desktopImg.getRGB(point_x, point_y));

		refreshInfoText(); // 更新提示框内容

	}

	// 提示框状态信息的刷新
	public void refreshInfoText() {

		// 文本信息
		String text = new String(
				"操作提示: By MaiLingFeng  =^-^=\n1.单击托盘图标->进入截图状态\n2.双击右键------>退出截图状态\n3.双击左键------>保存截图\n4.单击右键---->重新选择截图区域");
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
				+ captureWidth + "*" + captureHeight + "\n当前RBG:("
				+ point_color.getRed() + "," + point_color.getGreen() + ","
				+ point_color.getBlue() + ")\n" + text;
		infoArea.setText(infoString);

		// 放大镜信息
		int pick_x1, pick_y1, pick_x2, pick_y2, pickImg_x, pickImg_y;

		if (point_x - HALF_PICK_IMG < 0) { // 获得放大图片的捡取左上角和右下角坐标,以及在放大镜中的左上角位置坐标
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

	// 确定区域的左上点和右下角坐标
	public void confirmArea() {
		int temp;
		// 以x,y为截图选区左上角坐标初值,计算左上角x1,y1和右下角x2,y2的坐标
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
		// 双击左键,确定选择图像选区,保存截图
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
		// 左键在未选区完毕时单击,确定选区起点
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (captured == false) {
				point_x = e.getX();
				point_y = e.getY();
				x = point_x; // 选区起点坐标
				y = point_y;
				draging = true;
				captured = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// 左键释放,确定截图选区
		// 右键释放,重新选区
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
		// 拖拉截图时画矩形
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
		// 未决定截图时,画交叉线
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

