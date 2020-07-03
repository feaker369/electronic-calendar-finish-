package myself;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tools extends JPanel {
	private static final long serialVersionUID = 5692290911249557954L;
	
	JPanel jPanel1 = new JPanel();
	JPanel dayPanel = new JPanel();
	JPanel calPanel = new HomePanel();
	JButton jietu = new JButton("截图");
	JButton beijing = new JButton("更换背景");

	static Calendar c = Calendar.getInstance();
	static int year = c.get(Calendar.YEAR);
	static int month = c.get(Calendar.MONTH) + 1;
	int monthday = 0;
	JLabel nowLabel = new JLabel(year + "年" + month+ "月" + (c.get(Calendar.DATE)) + "日", JLabel.CENTER);
	JLabel timeLabel = new JLabel("---", JLabel.CENTER);
	public Tools() {
		//时间栏
				Timer timer = new Timer(1000, new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						timeLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
					}
				});
				timer.start();
		jietu.setContentAreaFilled(false);
		jietu.setFocusPainted(false);
		beijing.setContentAreaFilled(false);
		beijing.setFocusPainted(false);
		
		nowLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		nowLabel.setForeground(Color.blue);
		timeLabel.setFont(new java.awt.Font("微软雅黑", Font.BOLD, 36));
		dayPanel.setLayout(new BorderLayout());
		dayPanel.add(timeLabel, BorderLayout.CENTER);
		jPanel1.setLayout(new GridLayout(1, 2));
		jPanel1.add(jietu);
		jPanel1.add(beijing);
		jPanel1.setBackground(Color.white);
		jPanel1.setOpaque(false);
		dayPanel.setBackground(Color.white);
		dayPanel.setOpaque(false);
		calPanel.setLayout(new BorderLayout());
		calPanel.add(jPanel1, BorderLayout.NORTH);
		calPanel.add(dayPanel, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.add(calPanel);
		jietu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 开始截图
				AlarmTools.screenshot();
			}
		});
		beijing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 开始截图
				
				Background.background();
				setVisible(false);
				CalendarMain.main(null);
			}
		});
		
	}

}