package myself;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Background {
	static int flag;
	static File f;
	static String name="E:\\eclipse\\MyselfCalendar\\src\\picture\\0.jpg";
	
	public static void background(){
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "\\src\\picture\\");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		fc.setFileFilter(filter);
		//���ñ����ļ��Ի���ı���
		fc.showDialog(new JLabel(), "��");
		//�������ȷ����ť�����ø��ļ���   
		if (flag == JFileChooser.APPROVE_OPTION) {
			//���������Ҫ������ļ�
			name=fc.getSelectedFile().getPath(); 
		}
	}
	public String getname() {
		// TODO �Զ����ɵķ������
		return name;
	}

}
