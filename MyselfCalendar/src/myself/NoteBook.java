package myself;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NoteBook extends JPanel {
	private static final long serialVersionUID = 5692290911249557954L;
	
	JTextArea jTextArea = new JTextArea();
	JScrollPane JScrollPane = new JScrollPane(jTextArea);


	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new HomePanel();
	JPanel jPanel3 = new JPanel();
	JButton newf = new JButton("�½�");
	JButton openf = new JButton("��");
	JButton savef = new JButton("����");
	JButton qingk = new JButton("���");
	JButton ziti = new JButton("����");
	JButton yanse = new JButton("��ɫ");
	private JPopupMenu popupMenu;
	private JMenuItem popM_Cut;
	private JMenuItem popM_Copy;
	private JMenuItem popM_Paste;
	private JMenuItem popM_Delete;
	private JMenuItem popM_SelectAll;
	public Clipboard clipboard = new Clipboard("ϵͳ���а�"); 

	Date day=new Date();
	private int flag;
	private File f;
	JColorChooser jcc1=null;
	Color color=Color.BLACK;

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public NoteBook() {
		
		open();
		jPanel3.setLayout(new GridLayout(1, 6));
		jPanel3.setBackground(Color.white);
		jPanel3.setOpaque(false);
		newf.setContentAreaFilled(false);
		newf.setFocusPainted(false);
		openf.setContentAreaFilled(false);
		openf.setFocusPainted(false);
		savef.setContentAreaFilled(false);
		savef.setFocusPainted(false);
		qingk.setContentAreaFilled(false);
		qingk.setFocusPainted(false);
		ziti.setContentAreaFilled(false);
		ziti.setFocusPainted(false);
		yanse.setContentAreaFilled(false);
		yanse.setFocusPainted(false);
		jTextArea.setFont(new Font("����", 1, 15));
		JScrollPane.setVisible(true);
		jPanel1.setLayout(new BorderLayout());
		jPanel1.add(JScrollPane, BorderLayout.CENTER);
		jPanel1.setOpaque(false);
		jPanel1.setPreferredSize(new Dimension(500, 230));
		jPanel3.add(newf);
		jPanel3.add(openf);
		jPanel3.add(savef);
		jPanel3.add(qingk);
		jPanel3.add(ziti);
		jPanel3.add(yanse);
		popupMenu = new JPopupMenu();
		addPopup(jTextArea, popupMenu);
		popM_Cut = new JMenuItem("����(X)");
		//popM_Cut.addActionListener(this);
		popupMenu.add(popM_Cut);
		popM_Copy = new JMenuItem("����(C)");
		//popM_Copy.addActionListener(this);
		popupMenu.add(popM_Copy);
		popM_Paste = new JMenuItem("ճ��(V)");
		//popM_Paste.addActionListener(this);
		popupMenu.add(popM_Paste);
		popM_Delete = new JMenuItem("ɾ��(D)");
		//popM_Delete.addActionListener(this);
		popupMenu.add(popM_Delete);
		popM_SelectAll = new JMenuItem("ȫѡ(A)");
		//popM_SelectAll.addActionListener(this);
		popupMenu.add(popM_SelectAll);

		jPanel2.setLayout(new BorderLayout());
		jPanel2.setBackground(Color.white);
		jPanel2.setOpaque(false);
		jPanel2.add(jPanel3, BorderLayout.NORTH);
		jPanel2.add(jPanel1, BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.add(jPanel2);

		savef.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					save();
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});
		openf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//���õ����� FileDialog
				
				jTextArea.setText(null);
				JFileChooser jfilechooser1 = new JFileChooser(System.getProperty("user.dir") + "\\source\\");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "�ı��ļ�(*.txt)", "txt");
				jfilechooser1.setFileFilter(filter);
				int value=jfilechooser1.showDialog(new JLabel(), "��");
				if(value==JFileChooser.APPROVE_OPTION){ 
					try {
						File file=jfilechooser1.getSelectedFile();
						BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
						byte[] b = new byte[in.available()];
						in.read(b, 0, b.length);
						jTextArea.append(new String(b, 0, b.length));
						in.close();
						}
					catch (IOException ex) {
						jTextArea.setText("Error opening ");
					}
				}
				}
		});
		qingk.addActionListener( e -> {
			jTextArea.setText(null);
		});
		newf.addActionListener( e -> {
			jTextArea.setText(null);
		});
		ziti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MQFontChooser fontChooser = new MQFontChooser(jTextArea.getFont());
				fontChooser.showFontDialog(new JFrame());
				Font font = fontChooser.getSelectFont();
				// ���������õ�JTextArea��
				jTextArea.setFont(font);
			}
		});
		yanse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jcc1=new JColorChooser();
				JOptionPane.showMessageDialog(new Frame(), jcc1, "ѡ��������ɫ", -1);
				color = jcc1.getColor();
				//String string=textArea.getSelectedText();
				jTextArea.setForeground(color);
			}
		});
		popM_Cut.addActionListener( e -> {
			cut();
		});
		popM_Copy.addActionListener( e -> {
			copy();
		});
		popM_Paste.addActionListener( e -> {
			paste();
		});
		popM_Delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tem=jTextArea.getText().toString();
				jTextArea.setText(tem.substring(0,jTextArea.getSelectionStart()));
			}
		});
		popM_SelectAll.addActionListener( e -> {
			jTextArea.selectAll();
		});
		
        
	}

	 //�򿪵ľ��巽��
	  private void open() {
	    try {
	      jTextArea.setText(null);
	      File file = new File(System.getProperty("user.dir") + "\\source\\note.txt");
	      BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	      byte[] b = new byte[in.available()];
	      in.read(b, 0, b.length);
	      jTextArea.append(new String(b, 0, b.length));
	      in.close();


	    }
	    catch (IOException ex) {
	    	jTextArea.setText("Error opening ");
	    }
	  }
	  
	// �������
	private void save() {
		String fileName;
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "\\source\\");
		//���ñ����ļ��Ի���ı��� 
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "�ı��ļ�(*.txt)", "txt");
		fc.setFileFilter(filter);

		fc.setDialogTitle("����");
		//���ｫ��ʾ�����ļ��ĶԻ���  
		try {
			flag = fc.showSaveDialog(new Frame());
			} catch (HeadlessException he) {
				System.out.println("Save File Dialog ERROR!");
				}
		//�������ȷ����ť�����ø��ļ���   
		if (flag == JFileChooser.APPROVE_OPTION) {
			//���������Ҫ������ļ� 
			f = fc.getSelectedFile();
			//����ļ���  
			fileName = fc.getName(f);
			//Ҳ����ʹ��fileName=f.getName(); 
			//�����û���д���ļ������������ƶ��ĺ�׺������ô���Ǹ������Ϻ�׺
			if(fileName.indexOf(".txt")==-1){
				fileName=fileName+".txt";
				System.out.println("renamed");
			}
			System.out.println(fileName);
			
		try {
			//String fileName=df.format(day);
			String fname="\\source\\"+fileName;
			File file = new File(System.getProperty("user.dir") + fname);
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] b = (jTextArea.getText()).getBytes();
			out.write(b, 0, b.length);
			out.close();
			JOptionPane.showMessageDialog(null, "����ɹ�");
		} catch (IOException ex) {
			jTextArea.setText("Error saving ");
		}
	}}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
					}
				}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
					}
				}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
				}
			});
		}
	public void cut(){
		copy();
		//��ǿ�ʼλ��
		int start = this.jTextArea.getSelectionStart();
		//��ǽ���λ��
		int end = this.jTextArea.getSelectionEnd();
		//ɾ����ѡ��
		this.jTextArea.replaceRange("", start, end);
		}
	public void copy(){
		//�϶�ѡȡ�ı�
		String temp = this.jTextArea.getSelectedText();
		//�ѻ�ȡ�����ݸ��Ƶ������ַ����������̳��˼�����ӿ�
		StringSelection text = new StringSelection(temp);
		//�����ݷ��ڼ�����
		this.clipboard.setContents(text, null);
		}
	public void paste(){
		//Transferable�ӿڣ��Ѽ����������ת��������
		Transferable contents = this.clipboard.getContents(this);
		//DataFalvor���ж��Ƿ��ܰѼ����������ת����������������
		DataFlavor flavor = DataFlavor.stringFlavor;
		//�������ת��
		if(contents.isDataFlavorSupported(flavor)){
			String str;
			try {//��ʼת��
				str=(String)contents.getTransferData(flavor);
				//���Ҫճ��ʱ������Ѿ�ѡ����һЩ�ַ�
				if(this.jTextArea.getSelectedText()!=null){
					//��λ��ѡ���ַ��Ŀ�ʼλ��
					int start = this.jTextArea.getSelectionStart();
					//��λ��ѡ���ַ���ĩβλ��
					int end = this.jTextArea.getSelectionEnd();
					//��ճ���������滻�ɱ�ѡ�е�����
					this.jTextArea.replaceRange(str, start, end);
					}else{
						//��ȡ�������jTextArea��λ��
						int mouse = this.jTextArea.getCaretPosition();
						//��������ڵ�λ��ճ������
						this.jTextArea.insert(str, mouse);
						}
				}
			catch(UnsupportedFlavorException e) {
				e.printStackTrace();
				} 
			catch (IOException e) {
				e.printStackTrace();
				} 
			catch(IllegalArgumentException e){
				e.printStackTrace();
				}
			}
		}
}