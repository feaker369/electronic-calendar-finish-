package myself;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class ChooseDay extends JDialog {
	private static final long serialVersionUID = -3230014153625851275L;
	
	private JButton sure = new JButton("���");
	private JButton cancel = new JButton("ȡ��");

	public ChooseDay(CalendarsView calendarsView, int year, int mouth) {
		super(calendarsView, "��������", true);
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JSpinner spinner1 = new JSpinner();
		JSpinner spinner2 = new JSpinner();
		spinner1.setModel(new SpinnerNumberModel(year, 0, 9999, 1));
		spinner2.setModel(new SpinnerNumberModel(mouth, 1, 12, 1));
		//��������
		setSpinners(spinner1);
		setSpinners(spinner2);
		
		panel1.add(spinner1);
		panel1.add(spinner2);

		sure.setContentAreaFilled(false);
		sure.setFocusPainted(false);
		cancel.setContentAreaFilled(false);
		cancel.setFocusPainted(false);
		panel2.setLayout(new GridLayout(1, 2));
		panel2.add(cancel);
		panel2.add(sure);

		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.SOUTH);

		pack(); // ���������С
		setResizable(false); // ���ɵ��ڴ��ڴ�С
		setLocationRelativeTo(null); // ������ʾ����

		sure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
				CalendarsView.year = (int) spinner1.getValue();
				CalendarsView.month = (int) spinner2.getValue();

			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose(); // �رյ�ǰ����
			}
		});
		
	}
	
	public void setSpinners(JSpinner spinner){
		//�������� 
				JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0");
				spinner.setEditor(editor);
				JFormattedTextField textField = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
				textField.setFont(new java.awt.Font("Dialog", 1, 30));
				textField.setEditable(true);
				DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
				NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
				formatter.setAllowsInvalid(false);
	}
	
}