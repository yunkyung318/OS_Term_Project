
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

		String a[] = { "프로세스ID", "도착시간", "서비스시간", "우선순위", "시간할당량" };

		model = new DefaultTableModel(a, 0);
		table = new JTable(model);

		Panel = new JPanel();
		Panel.setLayout(new BorderLayout());

		js = new JScrollPane(table);
		Panel.add(js, BorderLayout.CENTER);
		
		c.add(Panel);

		String choice[] = { "FCFS", "SJF", "비선점 Priority", "선점 Priority", "RR", "SRT", "HRN" };
		JComboBox<String> Combo = new JComboBox<>();
		for (int i = 0; i < choice.length; i++) {
			Combo.addItem(choice[i]);
		}

		c.add(Combo);
		button1 = new JButton("메모리 리셋");
		button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int row = table.getRowCount();
                if (row > -1) {
                	for(int i=row-1;i>-1;i--)
                		model.removeRow(i);
                }
            }
        });
		button2 = new JButton("프로세스 추가");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new String[] { "", "", "", "", "", "" });
			}
		});
		
		button3 = new JButton("프로세스 삭제");
		button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row > -1) {
                    model.removeRow(row);
                }
            }
        });
		button4 = new JButton("결과 창 보기");

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
