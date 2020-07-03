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
	String[] weekdaystr = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	Object[][] monthview = new String[6][7];
	JPanel dayPanel = new JPanel();
	JPanel panel1 = new JPanel();
	JPanel calPanel = new HomePanel();
	JPanel alarmDemo = new AlarmDemo();
	JPanel noteBook = new NoteBook();
	JPanel gongju = new Tools();
	JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	JButton Prebutton = new JButton("上一月");
	JButton Nextbutton = new JButton("下一月");
	JButton PreYear = new JButton("上一年");
	JButton NextYear = new JButton("下一年");
	JButton Change = new JButton(year + "年" + month + "月");

	JLabel nowLabel = new JLabel(year + "年" + month+ "月" + (c.get(Calendar.DATE)) + "日", JLabel.CENTER);
	JLabel timeLabel = new JLabel("---", JLabel.CENTER);
	
	DefaultTableModel model = new DefaultTableModel();
	JTable JTable;
	JScrollPane JScrollPane;

	TrayIcon trayIcon;//托盘图标
    SystemTray systemTray;//系统托盘
	public CalendarsView() {
      
		//时间栏
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
		timeLabel.setFont(new java.awt.Font("微软雅黑", Font.BOLD, 36));
		dayPanel.setLayout(new BorderLayout());
		dayPanel.add(timeLabel, BorderLayout.CENTER);
		//dayPanel.add(JScrollPane, BorderLayout.CENTER);
		// 上方按钮
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
		
		jTabbedPane.add(calPanel, "日历");
		jTabbedPane.add(alarmDemo,"闹钟"); 
		jTabbedPane.add(noteBook,"记事本"); 
		jTabbedPane.add(gongju,"工具"); 
		add(jTabbedPane, BorderLayout.NORTH);

		// 按钮监听器
		timeLabel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				// 处理鼠标点击
				month=now_month;
				year=now_year;
				dataOfDay();
				Change.setText(year + "年" + month + "月");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
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
		}  //添加日期，放到面板里面
		this.paintDay();
		JPanel panel_main = new JPanel();  //放置以上两个面板
		panel_main.setLayout(new BorderLayout());  //边界布局管理器
		panel_main.add(panel_day,BorderLayout.SOUTH);
		getContentPane().add(panel_main);
	}
	@SuppressWarnings("deprecation")
	private void paintDay() {
	
		if(this.todayFlag) {
			month_int = now_month;//要求显示今天的日期 
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
		Date firstDay = new Date(now_year, month_int, -2);//构造该月的第一天
		GregorianCalendar cal = new GregorianCalendar();//创建一个Calendar的实例
		cal.setTime(firstDay);
		
		int days = 0;//存放某个月份的天数
		int day_week = 0;//存放某个月份的第一天使星期几的数值
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10  || month == 12) {
			days = 31; 
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = 30;
		} else {
			if(cal.isLeapYear(now_year)){
				days = 29;
			} else {
				days = 28;
			}//二月，如果闰年，则有29天，否则有28天
		}//判断是几月份，根据它来设定day的值，其中二月份要判断是否闰年
		day_week = firstDay.getDay();
		int count = 1;
  /**
   * 绘制按钮。在这里首先要根据选定的月份的第一天是星期几来确定绘制按钮的起始位置
   * 其中day_week就是我们要绘制的起始位置，对于那些没有数值可以显示的按钮要置空
   */
		String[] LunarDate=new String[42];
		Lunar lunar = new Lunar();  
		
		for(int i = day_week; i < day_week + days; count++, i++){
			LunarDate[i]=lunar.getLunarDate( year,  month_int - 1, i); 
			if(i%7 == 0|| i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41) {
    //如果是边界上的按钮，文字用红色，以来标示周末
				if(i == day_week + now_date.getDate() - 1) {
					button_day[i].setForeground(Color.blue);//将与今天一样的日期用蓝色标示
					button_day[i].setText(count + LunarDate[i]);
				} else {
					button_day[i].setForeground(Color.red);//其它边界上的按钮中的文字用红色
					button_day[i].setText(count + LunarDate[i]);
				}
			} else {
				if(i == day_week + now_date.getDate() - 1){
					button_day[i].setForeground(Color.blue);//将与今天一样的日期用蓝色标示
					button_day[i].setText(count + LunarDate[i]);
				} else {
					button_day[i].setForeground(Color.black);//一般位置的按钮上的文字用黑色标示
					button_day[i].setText(count + LunarDate[i]);    
				}
			}
		}
			if(day_week == 0){
				//对于没有日期数值显示的按钮进行置空处理
				for(int i =days; i < 42; i++){
					button_day[i].setText("");//如果第一天是周日，则将第一天前面的按钮置空
				}
			} else{
		  for(int i = 0; i < day_week; i++){
			  button_day[i].setText("");//如果第一天不是周日，则将第一天前面的按钮置空
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
			Change.setText(year + "年" + month + "月");
		}
	}
}
