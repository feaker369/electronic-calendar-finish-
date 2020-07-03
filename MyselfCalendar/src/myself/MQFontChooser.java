package myself;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
 
/**
 * �����Լ��ҵ�һ������ѡ����������
 *
 */
public class MQFontChooser extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ѡ��ȡ����ť�ķ���ֵ
	 */
	public static final int CANCEL_OPTION = 0;
	/**
	 * ѡ��ȷ����ť�ķ���ֵ
	 */
	public static final int APPROVE_OPTION = 1;
	/**
	 * ����Ԥ�����ַ���
	 */
	private static final String CHINA_STRING = "�����Ǹ��ƣ�";
	/**
	 * Ӣ��Ԥ�����ַ���
	 */
	private static final String ENGLISH_STRING = "Hello Kitty��";
	/**
	 * ����Ԥ�����ַ���
	 */
	private static final String NUMBER_STRING = "0123456789";
	// Ԥ�����壬Ҳ�ǽ���Ҫ���ص�����
	private Font font = null;
	// ����ѡ�����������
	private Box box = null;
	// �����ı���
	private JTextField fontText = null;
	// ��ʽ�ı���
	private JTextField styleText = null;
	// ���ִ�С�ı���
	private JTextField sizeText = null;
	// Ԥ���ı���
	private JTextField previewText = null;
	// ����Ԥ��
	private JRadioButton chinaButton = null;
	// Ӣ��Ԥ��
	private JRadioButton englishButton = null;
	// ����Ԥ��
	private JRadioButton numberButton = null;
	// ����ѡ���
	private JList<?> fontList = null;
	// ��ʽѡ����
	private JList<?> styleList = null;
	// ���ִ�Сѡ����
	private JList<?> sizeList = null;
	// ȷ����ť
	private JButton approveButton = null;
	// ȡ����ť
	private JButton cancelButton = null;
	// ��������
	private String [] fontArray = null;
	// ������ʽ
	private String [] styleArray = {"����", "����", "б��", "��б��"};
	// ����Ԥ�������С
	private String [] sizeArray = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "����", "С��", "һ��", "Сһ", "����", "С��", "����", "С��", "�ĺ�", "С��", "���", "С��", "����", "С��", "�ߺ�", "�˺�"};
	// ���������ж�Ӧ�������С
	private int [] sizeIntArray = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};
	// ���ص���ֵ��Ĭ��ȡ��
	private int returnValue = CANCEL_OPTION;
	/**
	 * �幹��һ������ѡ����
	 */
	public MQFontChooser() {
		this(new Font("����", Font.PLAIN, 12));
	}
	/**
	 * ʹ�ø�����Ԥ�����幹��һ������ѡ����
	 * @param font ����
	 */
	public MQFontChooser(Font font) {
		setTitle("����ѡ����");
		this.font = font;
		// ��ʼ��UI���
		init();
		// ��Ӽ�����
		addListener();
		// ����Ԥ��������ʾ
		setup();
		// ��������
		setModal(true);
		setResizable(false);
		// ����Ӧ��С
		pack();
	}
	/**
	 * ��ʼ�����
	 */
	private void init(){
		// ���ϵͳ����
		GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontArray = eq.getAvailableFontFamilyNames();
		// ������
		box = Box.createVerticalBox();
		box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		fontText = new JTextField();
		fontText.setEditable(false);
		fontText.setBackground(Color.WHITE);
		styleText = new JTextField();
		styleText.setEditable(false);
		styleText.setBackground(Color.WHITE);
		sizeText = new JTextField("12");
		// �����ִ�С�ı���ʹ�õ�Document�ĵ����ƶ���һЩ�����ַ��Ĺ���
		Document doc = new PlainDocument(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str == null) {
					return;
				}
				if (getLength() >= 3) {
					return;
				}
				if (!str.matches("[0-9]+") && !str.equals("����") && !str.equals("С��") && !str.equals("һ��") && !str.equals("Сһ") && !str.equals("����") && !str.equals("С��") && !str.equals("����") && !str.equals("С��") && !str.equals("�ĺ�") && !str.equals("С��") && !str.equals("���") && !str.equals("С��") && !str.equals("����") && !str.equals("С��") && !str.equals("�ߺ�") && !str.equals("�˺�")) {
					return;
				}
				super.insertString(offs, str, a);
				sizeList.setSelectedValue(sizeText.getText(), true);
			}
		};
		sizeText.setDocument(doc);
		previewText = new JTextField(20);
		previewText.setHorizontalAlignment(JTextField.CENTER);
		previewText.setEditable(false);
		previewText.setBackground(Color.WHITE);
		chinaButton = new JRadioButton("����Ԥ��", true);
		englishButton = new JRadioButton("Ӣ��Ԥ��");
		numberButton = new JRadioButton("����Ԥ��");
		ButtonGroup bg = new ButtonGroup();
		bg.add(chinaButton);
		bg.add(englishButton);
		bg.add(numberButton);
		fontList = new JList<Object>(fontArray);
		styleList = new JList<Object>(styleArray);
		sizeList = new JList<Object>(sizeArray);
		approveButton = new JButton("ȷ��");
		cancelButton = new JButton("ȡ��");
		Box box1 = Box.createHorizontalBox();
		JLabel l1 = new JLabel("����:");
		JLabel l2 = new JLabel("����:");
		JLabel l3 = new JLabel("��С:");
		l1.setPreferredSize(new Dimension(165, 14));
		l1.setMaximumSize(new Dimension(165, 14));
		l1.setMinimumSize(new Dimension(165, 14));
		l2.setPreferredSize(new Dimension(95, 14));
		l2.setMaximumSize(new Dimension(95, 14));
		l2.setMinimumSize(new Dimension(95, 14));
		l3.setPreferredSize(new Dimension(80, 14));
		l3.setMaximumSize(new Dimension(80, 14));
		l3.setMinimumSize(new Dimension(80, 14));
		box1.add(l1);
		box1.add(l2);
		box1.add(l3);
		Box box2 = Box.createHorizontalBox();
		fontText.setPreferredSize(new Dimension(160, 20));
		fontText.setMaximumSize(new Dimension(160, 20));
		fontText.setMinimumSize(new Dimension(160, 20));
		box2.add(fontText);
		box2.add(Box.createHorizontalStrut(5));
		styleText.setPreferredSize(new Dimension(90, 20));
		styleText.setMaximumSize(new Dimension(90, 20));
		styleText.setMinimumSize(new Dimension(90, 20));
		box2.add(styleText);
		box2.add(Box.createHorizontalStrut(5));
		sizeText.setPreferredSize(new Dimension(80, 20));
		sizeText.setMaximumSize(new Dimension(80, 20));
		sizeText.setMinimumSize(new Dimension(80, 20));
		box2.add(sizeText);
		Box box3 = Box.createHorizontalBox();
		JScrollPane sp1 = new JScrollPane(fontList);
		sp1.setPreferredSize(new Dimension(160, 100));
		sp1.setMaximumSize(new Dimension(160, 100));
		sp1.setMaximumSize(new Dimension(160, 100));
		box3.add(sp1);
		box3.add(Box.createHorizontalStrut(5));
		JScrollPane sp2 = new JScrollPane(styleList);
		sp2.setPreferredSize(new Dimension(90, 100));
		sp2.setMaximumSize(new Dimension(90, 100));
		sp2.setMinimumSize(new Dimension(90, 100));
		box3.add(sp2);
		box3.add(Box.createHorizontalStrut(5));
		JScrollPane sp3 = new JScrollPane(sizeList);
		sp3.setPreferredSize(new Dimension(80, 100));
		sp3.setMaximumSize(new Dimension(80, 100));
		sp3.setMinimumSize(new Dimension(80, 100));
		box3.add(sp3);
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createVerticalBox();
		JPanel box6 = new JPanel(new BorderLayout());
		box5.setBorder(BorderFactory.createTitledBorder("�ַ���"));
		box6.setBorder(BorderFactory.createTitledBorder("ʾ��"));
		box5.add(chinaButton);
		box5.add(englishButton);
		box5.add(numberButton);
		box5.setPreferredSize(new Dimension(90, 95));
		box5.setMaximumSize(new Dimension(90, 95));
		box5.setMinimumSize(new Dimension(90, 95));
		box6.add(previewText);
		box6.setPreferredSize(new Dimension(250, 95));
		box6.setMaximumSize(new Dimension(250, 95));
		box6.setMinimumSize(new Dimension(250, 95));
		box4.add(box5);
		box4.add(Box.createHorizontalStrut(4));
		box4.add(box6);
		Box box7 = Box.createHorizontalBox();
		box7.add(Box.createHorizontalGlue());
		box7.add(approveButton);
		box7.add(Box.createHorizontalStrut(5));
		box7.add(cancelButton);
		box.add(box1);
		box.add(box2);
		box.add(box3);
		box.add(Box.createVerticalStrut(5));
		box.add(box4);
		box.add(Box.createVerticalStrut(5));
		box.add(box7);
		getContentPane().add(box);
	}
	/**
	 * ����Ԥ��������ʾ
	 */
	private void setup() {
		String fontName = font.getFamily();
		int fontStyle = font.getStyle();
		int fontSize = font.getSize();
		/*
		 * ���Ԥ������ִ�С��ѡ���б��У���ͨ��ѡ����б��е�ĳ�������ֵ������ֱ�ӽ�Ԥ�����ִ�Сд���ı���
		 */
		boolean b = false;
		for (int i = 0; i < sizeArray.length; i++) {
			if (sizeArray[i].equals(String.valueOf(fontSize))) {
				b = true;
				break;
			}
		}
		if(b){
			// ѡ�����ִ�С�б��е�ĳ��
			sizeList.setSelectedValue(String.valueOf(fontSize), true);
		}else{
			sizeText.setText(String.valueOf(fontSize));
		}
		// ѡ�������б��е�ĳ��
		fontList.setSelectedValue(fontName, true);
		// ѡ����ʽ�б��е�ĳ��
		styleList.setSelectedIndex(fontStyle);
		// Ԥ��Ĭ����ʾ�����ַ�
		chinaButton.doClick();
		// ��ʾԤ��
		setPreview();
	}
	/**
	 * ���������¼�������
	 */
	private void addListener() {
		sizeText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				setPreview();
			}
			public void focusGained(FocusEvent e) {
				sizeText.selectAll();
			}
		});
		// �����б���ѡ���¼��ļ�����
		fontList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					fontText.setText(String.valueOf(fontList.getSelectedValue()));
					// ����Ԥ��
					setPreview();
				}
			}
		});
		styleList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					styleText.setText(String.valueOf(styleList.getSelectedValue()));
					// ����Ԥ��
					setPreview();
				}
			}
		});
		sizeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if(!sizeText.isFocusOwner()){
						sizeText.setText(String.valueOf(sizeList.getSelectedValue()));
					}
					// ����Ԥ��
					setPreview();
				}
			}
		});
		// ���������
		EncodeAction ea = new EncodeAction();
		chinaButton.addActionListener(ea);
		englishButton.addActionListener(ea);
		numberButton.addActionListener(ea);
		// ȷ����ť���¼�����
		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �������
				font = groupFont();
				// ���÷���ֵ
				returnValue = APPROVE_OPTION;
				// �رմ���
				disposeDialog();
			}
		});
		// ȡ����ť�¼�����
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disposeDialog();
			}
		});
	}
	/**
	 * ��ʾ����ѡ����
	 * @param owner �ϲ�������
	 * @return �����η���ֵ��ʾ�û����������ѡ������ȷ����ť��ȡ����ť���ο����ೣ���ֶ�APPROVE_OPTION��CANCEL_OPTION
	 */
	public final int showFontDialog(JFrame owner) {
		setLocationRelativeTo(owner);
		setVisible(true);
		return returnValue;
	}
	/**
	 * ����ѡ����������
	 * @return �������
	 */
	public final Font getSelectFont() {
		return font;
	}
	/**
	 * �رմ���
	 */
	private void disposeDialog() {
		MQFontChooser.this.removeAll();
		MQFontChooser.this.dispose();
	}
	
	/**
	 * ��ʾ������Ϣ
	 * @param errorMessage ������Ϣ
	 */
	private void showErrorDialog(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "����", JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * ����Ԥ��
	 */
	private void setPreview() {
		Font f = groupFont();
		previewText.setFont(f);
	}
	/**
	 * ����ѡ���������
	 * @return ����
	 */
	private Font groupFont() {
		String fontName = fontText.getText();
		int fontStyle = styleList.getSelectedIndex();
		String sizeStr = sizeText.getText().trim();
		// ���û������
		if(sizeStr.length() == 0) {
			showErrorDialog("���壨��С����������Ч����ֵ��");
			return null;
		}
		int fontSize = 0;
		// ͨ��ѭ���Ա����ִ�С�����Ƿ��������б���
		for (int i = 0; i < sizeArray.length; i++) {
			if(sizeStr.equals(sizeArray[i])){
				fontSize = sizeIntArray[i];
				break;
			}
		}
		// û�����б���
		if (fontSize == 0) {
			try{
				fontSize = Integer.parseInt(sizeStr);
				if(fontSize < 1){
					showErrorDialog("���壨��С����������Ч����ֵ����");
					return null;
				}
			}catch (NumberFormatException nfe) {
				showErrorDialog("���壨��С����������Ч����ֵ����");
				return null;
			}
		}
		return new Font(fontName, fontStyle, fontSize);
	}
	
	/**
	 * ����ѡ���¼��ļ�������
	 *
	 */
	class EncodeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(chinaButton)) {
				previewText.setText(CHINA_STRING);
			} else if (e.getSource().equals(englishButton)) {
				previewText.setText(ENGLISH_STRING);
			} else {
				previewText.setText(NUMBER_STRING);
			}
		}
	}
}