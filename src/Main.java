import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main extends JFrame {
	/* private MyPanel mp = new MyPanel(); */

	JTable table;
	JScrollPane js;
	JPanel Panel;
	DefaultTableModel model;
	JButton resetBtn;
	JButton addBtn;
	JButton deChoiceBtn;
	JButton deLastBtn;
	JButton resultBtn;
	JComboBox<String> Combo;

	private Color color[] = { Color.ORANGE, Color.cyan, Color.YELLOW, Color.MAGENTA, Color.LIGHT_GRAY, Color.gray,
			Color.green };

	String blank[] = { "", "", "", "" }; // 프로세스 추가할 때 빈칸 생성

	public Main() {

		setTitle("test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		String ment[] = { "프로세스ID", "도착시간", "서비스시간", "우선순위" };

		model = new DefaultTableModel(ment, 0); // DefaultTableModel을 통해 table안
		table = new JTable(model); // 메모리 손상없이 삽입,삭제가 자유로움
		Panel = new JPanel();

		js = new JScrollPane(table); // 프로세스가 많아지면 스크롤로 관리 가능
		Panel.add(js);

		String choice[] = { "FCFS", "SJF", "비선점 Priority", "선점 Priority", "RR", "SRT", "HRN" };
		Combo = new JComboBox<>();
		for (int i = 0; i < choice.length; i++) {
			Combo.addItem(choice[i]);
		} // combo 박스를 이용해 스케줄링 방법을 리스트 구현

		resetBtn = new JButton("메모리 리셋");
		resetBtn.setBackground(new Color(242, 255, 237));
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getRowCount(); // 행의 개수를 카운트해서 row변수에 저장
				if (row > 0) {
					for (int i = row - 1; i > -1; i--) // (행의 개수-1)부터 --해서 모든 메모리를 삭제
						model.removeRow(i);
				}
			}
		});

		addBtn = new JButton("프로세스 추가");
		addBtn.setBackground(new Color(242, 255, 237));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(blank); // 프로세스 추가 시 자동으로 5열의 빈 행이 생성
			}
		});

		deChoiceBtn = new JButton("프로세스 선택 삭제");
		deChoiceBtn.setBackground(new Color(242, 255, 237));
		deChoiceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); // 선택한 행의 번호를 알려줌
				if (row > -1) { // -1보다 클 때 (인덱스 0부터 시작)
					model.removeRow(row); // 선택한 행 삭제
				}
			}
		});

		deLastBtn = new JButton("프로세스 마지막 줄 삭제");
		deLastBtn.setBackground(new Color(242, 255, 237));
		deLastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getRowCount(); // 행의 개수 카운트
				if (row > 0) { // 0보다 클 때 마지막 인데스 삭제
					model.removeRow(row - 1);
				}
			}
		});

		resultBtn = new JButton("결과 창 보기");
		resultBtn.setBackground(new Color(242, 255, 237));
		resultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) Combo.getSelectedItem();
				SchedulingManager scheduling;
				String tqSize = JOptionPane.showInputDialog("타임 슬라이스 크기(Time Quantum)");
				
				switch (selected) {
				case "FCFS":
					scheduling = new FCFS();
					break;
				case "SJF":
					scheduling = new FCFS();
					break;
				case "PSN":
					scheduling = new FCFS();
					break;
				case "PSP":
					scheduling = new FCFS();
					break;
				case "RR":
					if (tqSize == null) {
						return;
					}
					scheduling = new FCFS();
					scheduling.setTimeQuantum(Integer.parseInt(tqSize));
					break;
				case "SRT":
					if (tqSize == null) {
						return;
					}
					scheduling = new FCFS();
					scheduling.setTimeQuantum(Integer.parseInt(tqSize));
					break;
				case "HRN":
					scheduling = new FCFS();
					break;
				default:
					return;
				}
			}
		});

		c.add(Panel);
		c.add(Combo);
		c.add(resetBtn);
		c.add(addBtn);
		c.add(deChoiceBtn);
		c.add(deLastBtn);
		c.add(resultBtn);
		/* setContentPane(mp); */

		setResizable(false); // 확장 비활성화
		setSize(680, 515);
		setVisible(true);

	}

	class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			int x = 10;
			int y = 40;
			int width = 10;
			int height = 40;

			super.paintComponent(g);
			g.setColor(Color.BLUE);
			g.fillRect(10, 10, 50, 40);
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
