
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main extends JFrame {
	JTable table;
	JScrollPane js;
	JPanel Panel;
	DefaultTableModel model;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	
	public Main() {
		setTitle("test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c=getContentPane();
		c.setLayout(new FlowLayout());
		
		String a[] = { "���μ���ID", "�����ð�", "���񽺽ð�", "�켱����", "�ð��Ҵ緮" };
		
		model = new DefaultTableModel(a,0);
		table = new JTable(model);
		
		Panel=new JPanel();
		Panel.setLayout(new BorderLayout());
		
		js=new JScrollPane(table);
		Panel.add(js,BorderLayout.CENTER);
		
		String b[]= {" "," "," "," "," "};
		model.addRow(b);
		c.add(Panel);
		
		String choice[]= {"FCFS","SJF","���� Priority","���� Priority","RR","SRT","HRN"};
		JComboBox<String> Combo=new JComboBox<>();
		for(int i=0;i<choice.length;i++) {
			Combo.addItem(choice[i]);
		}
		
		c.add(Combo);
		button1=new JButton("�޸� ����");
		button2=new JButton("���μ��� �߰�");
		button3=new JButton("���μ��� ����");
		button4=new JButton("��� â ����");
		
		c.add(button1);
		c.add(button2);
		c.add(button3);
		c.add(button4);
		
		setSize(590,515);
		setVisible(true);
		
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
