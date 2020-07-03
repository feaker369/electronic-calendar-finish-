package myself;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;


public class CalendarsView extends JFrame {
	private static final long serialVersionUID = 1L;
	//private String[] a = new String[42];
	private JButton[] button_day = new JButton[42];
	private JButton[] button_week = new JButton[7];
	
	private Date now_date = new Date();
	Calendar cal = Calendar.getInstance();
	private int now_year = cal.get(Calendar.YEAR);
	private int now_month = cal.get(Calendar.MONTH)+1;
	
	private int month_int; 
	
	//private String[] lunar_day = new String[42];
	static Calendar c = Calendar.getInstance();
	static int year = c.get(Calendar.YEAR);
	static int month = c.get(Calendar.MONTH) + 1;
	int monthday = 0;
	
	private boolean todayFlag;
	String[] weekdaystr = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
	Object[][] monthview = new String[6][7];
	JPanel dayPanel = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel calPanel = new HomePanel();
	JPanel alarmDemo = new AlarmDemo();
	JPanel noteBook = new NoteBook();
	JPanel gongju = new Tools();
	JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	JButton Prebutton = new JButton("��һ��");
	JButton Nextbutton = new JButton("��һ��");
	JButton PreYear = new JButton("��һ��");
	JButton NextYear = new JButton("��һ��");
	JButton Change = new JButton(year + "��" + month + "��");

	JLabel nowLabel = new JLabel(year + "��" + month+ "��" + (c.get(Calendar.DATE)) + "��", JLabel.CENTER);
	JLabel timeLabel = new JLabel("---", JLabel.CENTER);
	
	DefaultTableModel model = new DefaultTableModel();
	JTable JTable;
	JScrollPane JScrollPane;

	TrayIcon trayIcon;//����ͼ��
    SystemTray systemTray;//ϵͳ����
	public CalendarsView() {
      
		//ʱ����
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timeLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()));
			}
		});
		timer.start();
		
		nowLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		nowLabel.setForeground(Color.blue);
		dataOfDay();
		timeLabel.setFont(new java.awt.Font("΢���ź�", Font.BOLD, 36));
		dayPanel.setLayout(new BorderLayout());
		dayPanel.add(timeLabel, BorderLayout.CENTER);
		//dayPanel.add(JScrollPane, BorderLayout.CENTER);
		// �Ϸ���ť
		panel1.setLayout(new GridLayout(1, 5));
		panel1.setBackground(Color.white);
		panel1.setOpaque(false);
		PreYear.setContentAreaFilled(false);
		PreYear.setFocusPainted(false);
		Prebutton.setContentAreaFilled(false);
		Prebutton.setFocusPainted(false);
		Change.setContentAreaFilled(false);
		Change.setFocusPainted(false);
		Nextbutton.setContentAreaFilled(false);
		Nextbutton.setFocusPainted(false);
		NextYear.setContentAreaFilled(false);
		NextYear.setFocusPainted(false);

		panel1.add(PreYear);
		panel1.add(Prebutton);
		panel1.add(Change);
		panel1.add(Nextbutton);
		panel1.add(NextYear);
		
		dayPanel.setBackground(Color.white);
		dayPanel.setOpaque(false);
		calPanel.setLayout(new BorderLayout());
		calPanel.add(panel1, BorderLayout.NORTH);
		calPanel.add(dayPanel, BorderLayout.CENTER);
		
		jTabbedPane.add(calPanel, "����");
		jTabbedPane.add(alarmDemo,"����"); 
		jTabbedPane.add(noteBook,"���±�"); 
		jTabbedPane.add(gongju,"����"); 
		add(jTabbedPane, BorderLayout.NORTH);

		// ��ť������
		timeLabel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				// ���������
				month=now_month;
				year=now_year;
				dataOfDay();
				Change.setText(year + "��" + month + "��");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		PreYear.addActionListener(new PressButton());
		NextYear.addActionListener(new PressButton());
		Prebutton.addActionListener(new PressButton());
		Nextbutton.addActionListener(new PressButton());
	}

	public void dataOfDay() {
		JPanel panel_day = new HomePanel();
		panel_day.setLayout(new GridLayout(7, 7, 3, 3));
		for(int i = 0; i < 7; i++) {
			button_week[i] = new JButton("");
			button_week[i].setBorderPainted(false);
			button_week[i].setContentAreaFilled(false);
			button_week[i].setText(weekdaystr[i]);
			button_week[i].setForeground(Color.red);
			panel_day.add(button_week[i]);
			
		}
		button_week[0].setForeground(Color.red);
		button_week[6].setForeground(Color.red);
		for(int i = 0; i < 42; i++) {
			button_day[i] = new JButton("");
			button_day[i].setBorderPainted(false);
			button_day[i].setContentAreaFilled(false);
			panel_day.add(button_day[i]);
		}  //������ڣ��ŵ��������
		this.paintDay();
		JPanel panel_main = new JPanel();  //���������������
		panel_main.setLayout(new BorderLayout());  //�߽粼�ֹ�����
		panel_main.add(panel_day,BorderLayout.SOUTH);
		getContentPane().add(panel_main);
	}
	@SuppressWarnings("deprecation")
	private void paintDay() {
	
		if(this.todayFlag) {
			month_int = now_month;//Ҫ����ʾ��������� 
		} else {
			JButton button = new JButton();
			if (button == Prebutton) {
				month_int = month - 1;
				if (month <= 0) {
					year--;
					month_int = month + 12;
				}
			} 
			else if(button ==PreYear)
			{
				year--;
			}
			else if(button ==NextYear)
			{
				year++;
			}
			else {
				month_int = month + 1;
				if (month > 12) {
					year++;
					month_int = month - 12;
				}
			}
		}
		Date firstDay = new Date(now_year, month_int, -2);//������µĵ�һ��
		GregorianCalendar cal = new GregorianCalendar();//����һ��Calendar��ʵ��
		cal.setTime(firstDay);
		
		int days = 0;//���ĳ���·ݵ�����
		int day_week = 0;//���ĳ���·ݵĵ�һ��ʹ���ڼ�����ֵ
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10  || month == 12) {
			days = 31; 
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = 30;
		} else {
			if(cal.isLeapYear(now_year)){
				days = 29;
			} else {
				days = 28;
			}//���£�������꣬����29�죬������28��
		}//�ж��Ǽ��·ݣ����������趨day��ֵ�����ж��·�Ҫ�ж��Ƿ�����
		day_week = firstDay.getDay();
		int count = 1;
  /**
   * ���ư�ť������������Ҫ����ѡ�����·ݵĵ�һ�������ڼ���ȷ�����ư�ť����ʼλ��
   * ����day_week��������Ҫ���Ƶ���ʼλ�ã�������Щû����ֵ������ʾ�İ�ťҪ�ÿ�
   */
		String[] LunarDate=new String[42];
		Lunar lunar = new Lunar();  
		
		for(int i = day_week; i < day_week + days; count++, i++){
			LunarDate[i]=lunar.getLunarDate( year,  month_int - 1, i); 
			if(i%7 == 0|| i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41) {
    //����Ǳ߽��ϵİ�ť�������ú�ɫ��������ʾ��ĩ
				if(i == day_week + now_date.getDate() - 1) {
					button_day[i].setForeground(Color.blue);//�������һ������������ɫ��ʾ
					button_day[i].setText(count + LunarDate[i]);
				} else {
					button_day[i].setForeground(Color.red);//�����߽��ϵİ�ť�е������ú�ɫ
					button_day[i].setText(count + LunarDate[i]);
				}
			} else {
				if(i == day_week + now_date.getDate() - 1){
					button_day[i].setForeground(Color.blue);//�������һ������������ɫ��ʾ
					button_day[i].setText(count + LunarDate[i]);
				} else {
					button_day[i].setForeground(Color.black);//һ��λ�õİ�ť�ϵ������ú�ɫ��ʾ
					button_day[i].setText(count + LunarDate[i]);    
				}
			}
		}
			if(day_week == 0){
				//����û��������ֵ��ʾ�İ�ť�����ÿմ���
				for(int i =days; i < 42; i++){
					button_day[i].setText("");//�����һ�������գ��򽫵�һ��ǰ��İ�ť�ÿ�
				}
			} else{
		  for(int i = 0; i < day_week; i++){
			  button_day[i].setText("");//�����һ�첻�����գ��򽫵�һ��ǰ��İ�ť�ÿ�
		  }  
	  }
			/**/
	}

	class PressButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (button == Prebutton) {
				month--;
				if (month <= 0) {
					year--;
					month += 12;
				}
			}
			else if(button ==PreYear)
			{
				year--;
			}
			else if(button ==NextYear)
			{
				year++;
			}
			else {
				month++;
				if (month > 12) {
					year++;
					month -= 12;
				}
			}
			dataOfDay();
			Change.setText(year + "��" + month + "��");
		}
	}
}
