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
		//设置保存文件对话框的标题
		fc.showDialog(new JLabel(), "打开");
		//如果按下确定按钮，则获得该文件。   
		if (flag == JFileChooser.APPROVE_OPTION) {
			//获得你输入要保存的文件
			name=fc.getSelectedFile().getPath(); 
		}
	}
	public String getname() {
		// TODO 自动生成的方法存根
		return name;
	}

}
