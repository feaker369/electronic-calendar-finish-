package myself;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;



public class AlarmDemo extends JPanel implements Runnable{
	private static final long serialVersionUID = -7066237267822356675L;
	Thread alarm;
	Calendar c = Calendar.getInstance();
	JButton sure = new JButton("ȷ��");
	JButton open = new JButton("ѡ����ϲ��������");

	JLabel jLabel = new JLabel("���� ʱ��", JLabel.CENTER);
	JLabel dayLabel = new JLabel("��", JLabel.CENTER);
	JLabel hourLabel = new JLabel("ʱ", JLabel.CENTER);
	JLabel minuteLabel = new JLabel("��", JLabel.CENTER);

	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel5 = new HomePanel();
	JSpinner spinner1 = new JSpinner();
	JSpinner spinner2 = new JSpinner();
	JSpinner spinner3 = new JSpinner();
	JTextField music = new JTextField(System.getProperty("user.dir")+"\\source\\alarm0.wav");

	int y = 0, m = 0, h = 0, mi = 0, d = 0;
	boolean fo = false;

	public AlarmDemo() {
		music.setEditable(false);
		// ���ñ�ǩ��ʽ
		dayLabel.setFont(new Font("Dialog", 1, 30));
		hourLabel.setFont(new Font("Dialog", 1, 30));
		minuteLabel.setFont(new Font("Dialog", 1, 30));
		jLabel.setFont(new Font("Dialog", 1, 30));
		
		//��ť����
		sure.setContentAreaFilled(false);
		sure.setFocusPainted(false);
		open.setContentAreaFilled(false);
		open.setFocusPainted(false);



		spinner3.setModel(new SpinnerNumberModel(c.get(Calendar.DATE), 1, 31, 1));
		spinner1.setModel(new SpinnerNumberModel(c.get(Calendar.HOUR_OF_DAY),0, 23, 1));
		spinner2.setModel(new SpinnerNumberModel(c.get(Calendar.MINUTE), 0, 59, 1));
		// ��������
		setSpinners(spinner1);
		setSpinners(spinner2);
		setSpinners(spinner3);
		jPanel1.setBackground(Color.white);
		jPanel1.setOpaque(false);
		jPanel2.setBackground(Color.white);
		jPanel2.setOpaque(false);
		jPanel3.setBackground(Color.white);
		jPanel3.setOpaque(false);
		jPanel4.setBackground(Color.white);
		jPanel4.setOpaque(false);
		jPanel4.add(jLabel);
		jPanel4.setPreferredSize(new Dimension(500, 100));
		jPanel1.setLayout(new GridLayout(1, 6));
		jPanel1.add(spinner3);
		jPanel1.add(dayLabel);
		jPanel1.add(spinner1);
		jPanel1.add(hourLabel);
		jPanel1.add(spinner2);
		jPanel1.add(minuteLabel);
		jPanel1.setPreferredSize(new Dimension(500, 100));

		jPanel2.setLayout(new BorderLayout());
		jPanel2.add(jPanel4, BorderLayout.NORTH);
		jPanel2.add(music, BorderLayout.CENTER);
		jPanel2.add(open, BorderLayout.EAST);

		jPanel3.setLayout(new GridLayout(1, 1));
		jPanel3.add(sure);

		jPanel5.setLayout(new BorderLayout());
		jPanel5.add(jPanel1, BorderLayout.NORTH);
		jPanel5.add(jPanel2, BorderLayout.CENTER);
		jPanel5.add(jPanel3, BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.add(jPanel5);

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser(); // ʵ�����ļ�ѡ����
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // �����ļ�ѡ��ģʽ,�˴�Ϊ�ļ���Ŀ¼����
				fileChooser.setCurrentDirectory(new File(".")); // �����ļ�ѡ������ǰĿ¼
				fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
						public boolean accept(File file) { // �ɽ��ܵ��ļ�����
								String name = file.getName().toLowerCase();
								return name.endsWith(".wav") || name.endsWith(".au") || file.isDirectory();
							}

							public String getDescription() { // �ļ�����
								return "�����ļ�(*.wav,*.au)";
							}
						});
				if (fileChooser.showOpenDialog(AlarmDemo.this) == JFileChooser.APPROVE_OPTION) { // �����ļ�ѡ����,���ж��Ƿ����˴򿪰�ť
					String fileName = fileChooser.getSelectedFile().getAbsolutePath(); // �õ�ѡ���ļ���Ŀ¼�ľ���·��
					music.setText(fileName);
				}
			}
		});
		sure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (sure.getText().equals("ȷ��")) {
					try {
						d = (int) spinner3.getValue();
						h = (int) spinner1.getValue();
						mi = (int) spinner2.getValue();
						if (1 <= d && d <= 31 && 0 <= h && h <= 23 && 0 <= mi && mi <= 59) {
							fo = true;
							jLabel.setText("�������ӳɹ�");
							spinner1.setEnabled(false);
							spinner2.setEnabled(false);
							spinner3.setEnabled(false);
							start();
						} else
							JOptionPane.showMessageDialog(null, "����ʱ�����");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "��������ȷ��ʱ��");
					}

				} else {
					spinner1.setEnabled(true);
					spinner2.setEnabled(true);
					spinner3.setEnabled(true);
					fo = false;
					stop();
					sure.setText("ȷ��");
					jLabel.setText("���� ʱ��  ��-ʱ-��");

				}
			}
		});

	}
	public void start() {
		if (alarm == null) {
			alarm = new Thread(this);
			alarm.start();
		}
	}

	public void stop()// ֹͣ�߳�
	{
		alarm = null;
	}
	
	public void executeSound() {
		
		Date now = new Date();
		
		if (fo) {
			// ��ȡϵͳʱ���֣���
			sure.setText("�ر�");
			SimpleDateFormat ri = new SimpleDateFormat("dd"); // ��װ Ϊ�˻�ȡ����
			SimpleDateFormat shi = new SimpleDateFormat("H"); // ��װ Ϊ�˻�ȡСʱ
			SimpleDateFormat fen = new SimpleDateFormat("mm"); // ��װ Ϊ�˻�ȡ��
			int riqi = Integer.parseInt(ri.format(now)); // ��ȡ����
			int shizhong = Integer.parseInt(shi.format(now)); // ��ȡСʱ
			int fenzhong = Integer.parseInt(fen.format(now)); // ��ȡ����
			if (riqi == d && shizhong == h && fenzhong == mi) // �ж�����
			{
				// ����������Ϊ�ɼ�
				setVisible(true);
				// ��������
				Thread t=new Thread(new AlarmSound(music.getText()));
				t.start();
				fo = false;
				JOptionPane.showMessageDialog(null, "ʱ�䵽����˯�����ˣ�����");
				t.stop();
				
			}
		}
		
	}

	

	public void setSpinners(JSpinner spinner) {
		// ��������
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0");
		spinner.setEditor(editor);
		JFormattedTextField textField = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
		textField.setFont(new Font("Dialog", 1, 45));
		textField.setEditable(true);
		DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
		NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
		formatter.setAllowsInvalid(false);
	}
	
	public void run() {
	  while (true) {
		  try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			executeSound();// ��������

	  }

	 }
}
