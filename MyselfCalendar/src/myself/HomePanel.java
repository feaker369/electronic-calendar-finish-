package myself;
 
 
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
 
//������ҳ����ͼƬ��JPnel��
public class HomePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	ImageIcon icon;
	Image img;
	int flag;
	public HomePanel() {
		Background b = new Background();
		String name=b.getname();
		//  /img/HomeImg.jpg �Ǵ���������ڱ�д����Ŀ��bin�ļ����µ�img�ļ����µ�һ��ͼƬ
		icon=new ImageIcon(name);
		img=icon.getImage();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//����������Ϊ�˱���ͼƬ���Ը��洰�����е�����С�������Լ����óɹ̶���С
		g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
	}
 
}