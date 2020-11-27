import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class Main extends JFrame {

	JTable table;
	JScrollPane js, jpanel;
	JPanel framePanel;
	DefaultTableModel model;
	JButton resetBtn;
	JButton addBtn;
	JButton deChoiceBtn;
	JButton deLastBtn;
	JButton resultBtn;
	JComboBox<String> Combo;
	MyPanel panel;
	Process process;

	String blank[] = { "", "", "", "" }; // 프로세스 추가할 때 빈칸 생성

	public Main() {

		setTitle("CPU 스케줄링");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String ment[] = { "프로세스ID", "도착시간", "서비스시간", "우선순위" };

		model = new DefaultTableModel(ment, 0); // DefaultTableModel을 통해 table안
		table = new JTable(model); // 메모리 손상없이 삽입,삭제가 자유로움
		table.setFillsViewportHeight(true);

		framePanel = new JPanel();

		js = new JScrollPane(table); // 프로세스가 많아지면 스크롤로 관리 가능
		js.setBounds(10, 25, 540, 250);

		panel = new MyPanel();
		panel.setBackground(Color.WHITE);
		jpanel = new JScrollPane(panel);
		jpanel.setBounds(10, 300, 600, 100);

		String choice[] = { "FCFS", "SJF", "비선점 Priority", "선점 Priority", "RR", "SRT", "HRN" };
		Combo = new JComboBox<>();
		for (int i = 0; i < choice.length; i++) {
			Combo.addItem(choice[i]);
		} // combo 박스를 이용해 스케줄링 방법을 리스트 구현
		Combo.setBounds(560, 240, 100, 30);

		resetBtn = new JButton("메모리 리셋");
		resetBtn.setBounds(10, 280, 100, 25);
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
		addBtn.setBounds(115, 280, 120, 25);
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(blank); // 프로세스 추가 시 자동으로 5열의 빈 행이 생성
			}
		});

		deChoiceBtn = new JButton("프로세스 선택 삭제");
		deChoiceBtn.setBackground(new Color(242, 255, 237));
		deChoiceBtn.setBounds(240, 280, 140, 25);
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
		deLastBtn.setBounds(385, 280, 170, 25);
		deLastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getRowCount(); // 행의 개수 카운트
				if (row > 0) { // 0보다 클 때 마지막 인데스 삭제
					model.removeRow(row - 1);
				}
			}
		});

		resultBtn = new JButton("결과 보기");
		resultBtn.setBackground(new Color(242, 255, 237));
		resultBtn.setBounds(560, 280, 100, 25);
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
					String time=JOptionPane.showInputDialog("타임 슬라이스 (Time Quantum)");
					if (time == null) {
						return;
					}
					scheduling = new FCFS();
					scheduling.setTimeQuantum(Integer.parseInt(time));
					break;

				case "SRT":
					String srttime=JOptionPane.showInputDialog("타임 슬라이스 (Time Quantum)");
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
		framePanel = new JPanel(null);
		framePanel.add(js);
		framePanel.add(Combo);
		framePanel.add(resetBtn);
		framePanel.add(addBtn);
		framePanel.add(deChoiceBtn);
		framePanel.add(deLastBtn);
		framePanel.add(resultBtn);
		/* framePanel.add(panel); */

		setSize(700, 800);
		setVisible(true);
		add(framePanel);
	}

	class MyPanel extends JPanel {
		private List<ChartList> list;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (int i = 0; i < list.size(); i++) {
				ChartList chartList = list.get(i);

				int width = (chartList.getpFinish() - chartList.getpStart()) * 20; // 곱하기 초해서 크기 조정
				int height = 30; // 고정
				int x = 10;
				int y = 20; // 고정
				
				if (i / model.getRowCount() == 0) {
					g.setColor(chartList.getColor());
					g.drawRect(x, y, 10, 10);
					g.fillRect(x, y, 10, 10);
					g.setColor(Color.black);
					g.drawString(chartList.getPid(), (x+width)/2, (y+height)/2);
				}

				g.setColor(chartList.getColor());
				g.drawRect(x, y, 10, 10);
				g.fillRect(x, y, 10, 10);
				g.setColor(Color.black);
				g.drawString(chartList.getPid(), (x+width)/2, (y+height)/2);
				g.drawString(Integer.toString(chartList.getpStart()), x-width, y-5);
				g.setColor(chartList.getColor());
				
				x+=width;
				
				if (i == list.size() - 1)
                {
                    g.drawString(Integer.toString(chartList.getpFinish()), x-width , y -5);
                }
			}
		}

		public void setTimeQuantum(List<ChartList> list) {
			this.list = list;
			repaint();
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}