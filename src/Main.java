
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

	String b[] = { " ", " ", " ", " ", " " };

	public Main() {
		setTitle("test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		String a[] = { "���μ���ID", "�����ð�", "���񽺽ð�", "�켱����", "�ð��Ҵ緮" };

		model = new DefaultTableModel(a, 0);
		table = new JTable(model);

		Panel = new JPanel();
		Panel.setLayout(new BorderLayout());

		js = new JScrollPane(table);
		Panel.add(js, BorderLayout.CENTER);
		
		c.add(Panel);

		String choice[] = { "FCFS", "SJF", "���� Priority", "���� Priority", "RR", "SRT", "HRN" };
		JComboBox<String> Combo = new JComboBox<>();
		for (int i = 0; i < choice.length; i++) {
			Combo.addItem(choice[i]);
		}

		c.add(Combo);
		button1 = new JButton("�޸� ����");
		button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int row = table.getRowCount();
                if (row > -1) {
                	for(int i=row-1;i>-1;i--)
                		model.removeRow(i);
                }
            }
        });
		button2 = new JButton("���μ��� �߰�");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new String[] { "", "", "", "", "", "" });
			}
		});
		
		button3 = new JButton("���μ��� ����");
		button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row > -1) {
                    model.removeRow(row);
                }
            }
        });
		button4 = new JButton("��� â ����");

		c.add(button1);
		c.add(button2);
		c.add(button3);
		c.add(button4);

		setSize(590, 515);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Main();
	}

}
