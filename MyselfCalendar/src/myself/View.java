package myself;

import javax.swing.JFrame;

public class View {
	public void showview(){
		CalendarsView frame = new CalendarsView();
		frame.setTitle("��������");
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
