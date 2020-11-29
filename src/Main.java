import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class Main extends JFrame {
	JPanel framePanel;

	JTable table;
	JTable resulttable;

	JScrollPane js;
	JScrollPane resultPane;
	JScrollPane chartPane;

	DefaultTableModel model;
	DefaultTableModel resultmodel;

	JButton resetBtn;
	JButton addBtn;
	JButton deChoiceBtn;
	JButton deLastBtn;
	JButton resultBtn;

	JComboBox<String> Combo;

	JButton returnBtn;
	JButton exitBtn;

	MyPanel drawChart;

	String blank[] = { "", "", "", "" }; // 프로세스 추가할 때 빈칸 생성
	int X = 20;

	public Main() {

		setTitle("CPU 스케줄링");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String ment[] = { "프로세스ID", "도착시간", "서비스시간", "우선순위" };
		model = new DefaultTableModel(ment, 0); // DefaultTableModel을 통해 table안

		table = new JTable(model); // 메모리 손상없이 삽입,삭제가 자유로움
		table.setFillsViewportHeight(true);

		js = new JScrollPane(table); // 프로세스가 많아지면 스크롤로 관리 가능
		js.setBounds(10, 25, 540, 250);

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

		drawChart = new MyPanel();
		drawChart.setBackground(Color.WHITE);
		chartPane = new JScrollPane(drawChart);
		chartPane.setBounds(10, 320, 800,100);

		resultmodel = new DefaultTableModel(
				new String[] { "PID", "대기시간", "평균 대기시간", "응답시간", "평균 응답시간", "반환시간", "평균 반환시간" }, 0);
		resulttable = new JTable(resultmodel);
		resulttable.setFillsViewportHeight(true);
		resultPane = new JScrollPane(resulttable);
		resultPane.setBounds(10, 430, 550, 300);

		resultBtn = new JButton("결과 보기");
		resultBtn.setBackground(new Color(242, 255, 237));
		resultBtn.setBounds(560, 280, 100, 25);
		resultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) Combo.getSelectedItem();
				SchedulingManager scheduling;

				resetBtn.setEnabled(false);
				addBtn.setEnabled(false);
				deChoiceBtn.setEnabled(false);
				deLastBtn.setEnabled(false);
				resultBtn.setEnabled(false);

				returnBtn.setEnabled(true);
				exitBtn.setEnabled(true);

				switch (selected) {
				case "FCFS":
					scheduling = new FCFS();
					break;

				case "SJF":
					scheduling = new SJF();
					break;

				case "비선점 Priority":
					scheduling = new NPRE();
					break;

				case "선점 Priority":
					scheduling = new PRE();
					break;

				case "HRN":
					scheduling = new HRN();
					break;

				case "RR":
					String time = JOptionPane.showInputDialog("타임 슬라이스 (Time Quantum)");
					if (time == null) {
						return;
					}
					scheduling = new RR();
					scheduling.setTimeQuantum(Integer.parseInt(time));
					break;

				case "SRT":
					String srttime = JOptionPane.showInputDialog("타임 슬라이스 (Time Quantum)");
					if (srttime == null) {
						return;
					}
					scheduling = new SRT();
					scheduling.setTimeQuantum(Integer.parseInt(srttime));
					break;

				default:
					return;
				}

				for (int i = 0; i <= model.getRowCount(); i++) {
					resultmodel.addRow(blank);
				}

				for (int i = 0; i < model.getRowCount(); i++) {
					String pid = (String) model.getValueAt(i, 0);
					int arrive = Integer.parseInt((String) model.getValueAt(i, 1));
					int burst = Integer.parseInt((String) model.getValueAt(i, 2));
					int priority = Integer.parseInt((String) model.getValueAt(i, 3));
					scheduling.addProcess(new Process(pid, arrive, burst, priority));
				}

				scheduling.scheduling();

				for (int i = 0; i < model.getRowCount(); i++) {
					String pid = (String) model.getValueAt(i, 0);
					Process process = scheduling.getProcess(pid);
					resultmodel.setValueAt(pid, i, 0);
					resultmodel.setValueAt(process.getWaitingTime(), i, 1);
					resultmodel.setValueAt(process.getResponseTime(), i, 3);
					resultmodel.setValueAt(process.getTurnAroundTime(), i, 5);
				}

				resultmodel.setValueAt(scheduling.getAverageWaitingTime(), model.getRowCount(), 2);
				resultmodel.setValueAt(scheduling.getResponseTime(), model.getRowCount(), 4);
				resultmodel.setValueAt(scheduling.getAverageTurnAroundTime(), model.getRowCount(), 6);

				drawChart.setTimeQuantum(scheduling.getCLists());
			}
		});

		returnBtn = new JButton("다시하기");
		returnBtn.setBounds(570, 665, 100, 25);
		returnBtn.setBackground(new Color(242, 255, 237));
		returnBtn.setEnabled(false);
		returnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = resultmodel.getRowCount() - 1; i > -1; i--) {
					resultmodel.removeRow(i);
				}
				resetBtn.setEnabled(true);
				addBtn.setEnabled(true);
				deChoiceBtn.setEnabled(true);
				deLastBtn.setEnabled(true);
				resultBtn.setEnabled(true);
				returnBtn.setEnabled(false);
				exitBtn.setEnabled(false);
			}
		});

		exitBtn = new JButton("종료");
		exitBtn.setBounds(570, 700, 100, 25);
		exitBtn.setBackground(new Color(242, 255, 237));
		exitBtn.setEnabled(false);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
		framePanel.add(chartPane);
		framePanel.add(resultPane);
		framePanel.add(returnBtn);
		framePanel.add(exitBtn);
		
		setResizable(false);
		setSize(1000, 800);
		setVisible(true);
		add(framePanel);
	}

	class MyPanel extends JPanel {
		private List<ChartList> list;

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					ChartList chartList = list.get(i);

					int x = 30 * (i + 1);
					int y = 40; // 고정
					int width = 40 * (i + 2); // 곱하기 초해서 크기 조정
					int height = 5;// 고정

					if (i / model.getRowCount() == 0) {
						g.setColor(chartList.getColor());
						g.drawRect(width, height, 10, 10);
						g.fillRect(width, height, 10, 10);
						g.setColor(Color.black);
						g.drawString(chartList.getPid(), width, height + 25);
					}

					g.drawString(Integer.toString(chartList.getpStart()), X - 5, y + 45);
					g.setColor(chartList.getColor());
					g.drawRect(X, y, (chartList.getpFinish() - chartList.getpStart()) * 12, 24);
					g.fillRect(X, y, (chartList.getpFinish() - chartList.getpStart()) * 12, 24);
					g.setColor(Color.black);

					if ((chartList.getpFinish() - chartList.getpStart()) == 1)
						g.drawString(chartList.getPid(), X + 5, y + 18);
					else
						g.drawString(chartList.getPid(), X + 5, y + 18);

					X += (chartList.getpFinish() - chartList.getpStart()) * 12;

					if (i == list.size() - 1) {
						g.drawString(Integer.toString(chartList.getpFinish()), X, y + 45);
					}
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