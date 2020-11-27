import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

class InputFrame extends JFrame implements MouseListener {
	JTable table;
	JScrollPane js, jpanel;
	JPanel tablePanel;
	DefaultTableModel model;
	JButton resetBtn;
	JButton addBtn;
	JButton deChoiceBtn;
	JButton deLastBtn;
	JButton resultBtn;
	JComboBox<String> Combo;
	MyPanel panel;
	Process process;
	ChartList chartList;
	MyDialog dialog;
	String blank[] = { "", "", "", "" }; // 프로세스 추가할 때 빈칸 생성

	InputFrame() {
		super("CPU 스케줄링");
		this.setVisible(true);
		this.setLayout(new FlowLayout());

		String ment[] = { "프로세스ID", "도착시간", "서비스시간", "우선순위" };

		model = new DefaultTableModel(ment, 0); // DefaultTableModel을 통해 table안
		table = new JTable(model); // 메모리 손상없이 삽입,삭제가 자유로움
		tablePanel = new JPanel();

		js = new JScrollPane(table); // 프로세스가 많아지면 스크롤로 관리 가능
		tablePanel.add(js);

		panel = new MyPanel();
		panel.setBackground(Color.WHITE);
		jpanel = new JScrollPane(panel);

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

		dialog = new MyDialog(this, "타임슬라이스(Time Quantum)");

		resultBtn = new JButton("결과 창 보기");
		resultBtn.setBackground(new Color(242, 255, 237));
		resultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) Combo.getSelectedItem();
				SchedulingManager scheduling;

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

				case "HRN":
					scheduling = new FCFS();
					break;

				case "RR":
					dialog.setVisible(true);
					String time = dialog.getInput();

					if (time == null) {
						return;
					}
					scheduling = new FCFS();
					scheduling.setTimeQuantum(Integer.parseInt(time));
					break;

				case "SRT":
					dialog.setVisible(true);
					String srttime = dialog.getInput();

					if (srttime == null) {
						return;
					}
					scheduling = new FCFS();
					scheduling.setTimeQuantum(Integer.parseInt(srttime));
					break;

				default:
					return;
				}

				for (int i = 0; i < model.getRowCount(); i++) {
					String pid = (String) model.getValueAt(i, 0);
					int arrive = Integer.parseInt((String) model.getValueAt(i, 1));
					int burst = Integer.parseInt((String) model.getValueAt(i, 2));
					int priority = Integer.parseInt((String) model.getValueAt(i, 3));
					process = new Process(pid, arrive, burst, priority);
					scheduling.addProcess(process);
				}

				scheduling.scheduling();
				panel.setTimeQuantum(scheduling.getCLists());
			}
		});

		resultBtn.addMouseListener(this);

		add(tablePanel);
		add(Combo);
		add(resetBtn);
		add(addBtn);
		add(deChoiceBtn);
		add(deLastBtn);
		add(resultBtn);

		setResizable(false); // 확장 비활성화
		setSize(680, 515);
	}

	public void mouseClicked(MouseEvent e) {
		new OutputFrame();
		this.setVisible(false);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}

class OutputFrame extends JFrame implements MouseListener {
	
	JButton returnbutton;
	JButton exitbutton;
	JTextField waiting;
	JTextField avWating;
	JTextField response;
	JTextField avResponse;
	JTextField returnT;
	JTextField avReturnT;
	
	public OutputFrame() {
		super("CPU 스케줄링 결과");
		this.setVisible(true);
		this.setLayout(new FlowLayout());
		
		returnbutton = new JButton("다시하기");
		returnbutton.setBackground(new Color(242, 255, 237));

		exitbutton = new JButton("종료");
		exitbutton.setBackground(new Color(242, 255, 237));

		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		waiting=new JTextField();
		JTextField avWating;
		JTextField response;
		JTextField avResponse;
		JTextField returnT;
		JTextField avReturnT;
		add(new JLabel("각 프로세스 별 대기 시간"));
		
		
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}

class MyPanel extends JPanel {
	private List<ChartList> list;

	public void paintComponent(Graphics g) {

		for (int i = 0; i < list.size(); i++) {
			int x = 10;
			int y = 20; // 고정
			int width = (chartList.getpFinish() - chartList.getpStart()) * 10; // 곱하기 초해서 크기 조정
			int height = 60; // 고정

			super.paintComponent(g);

			g.setColor(chartList.getColor());
			g.drawRect(x, y, width, height);

			g.setColor(Color.BLACK);
			g.drawString(chartList.getPid(), (x + width) / 2, (y + height) / 2);
		}
	}

	public void setTimeQuantum(List<ChartList> list) {
		this.list = list;
		repaint();
	}
}

class MyDialog extends JDialog {
	private JTextField text = new JTextField(10);
	private JButton okbutton = new JButton("OK");

	public MyDialog(JFrame frame, String title) {
		super(frame, title, true);
		setLayout(new FlowLayout());
		add(text);
		add(okbutton);
		setSize(200, 100);

		okbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	public String getInput() {
		if (text.getText().length() == 0)
			return null;
		else
			return text.getText();
	}

}

public class Main extends JFrame {
	public static void main(String[] args) {
		new InputFrame();
	}

}