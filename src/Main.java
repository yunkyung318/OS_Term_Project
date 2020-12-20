import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class Main extends JFrame {
	private JPanel framePanel;	// 메인 프레임을 구성하는 패널
	private MyPanel drawChart;	// 차트를 그릴 Mypanel 메소드 객체

	private DefaultTableModel model;		// table의 값을 당담
	private DefaultTableModel resultmodel;	// resulttable의 값을 당담

	private JTable table;		// 프로세스의 정보를 받아오는 JTable
	private JTable resulttable;	// 프로세스 결과값을 출력하는 JTable

	private JScrollPane js;			// table 스크롤
	private JScrollPane resultPane;	// resulttable 스크롤
	private JScrollPane chartPane;	// drawChart 스크롤

	private JButton resetBtn;		// 프로세스 리셋 버튼
	private JButton addBtn;			// 프로세스 추가 버튼
	private JButton deChoiceBtn;	// 프로세스 선택 삭제 버튼
	private JButton deLastBtn;		// 프로세스 마지막 삭제 버튼
	private JButton resultBtn;		// 결과 버튼
	private JButton returnBtn;		// 결과 후 다시 실행할 버튼
	private JButton exitBtn;		// 프레임 종료 버튼

	private JComboBox<String> Combo;	// 스케줄링 방법을 선택 할 콤보박스
	private String blank[] = { "", "", "", "" };
	// table, resulttable 에서 사용할 빈칸으로 구성된 문자열 배열

	private int X = 25;	// 차트 그릴때 이용할 좌표

	public Main() {

		setTitle("CPU 스케줄링"); 	// 타이틀 달기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 프레임 종료 시 프로그램 종료

		String ment[] = { "프로세스ID", "도착시간", "서비스시간", "우선순위" };	// 테이블 첫 행에 넣을 문자열 배열
		model = new DefaultTableModel(ment, 0);						// ment를 첫 행으로 가진 DefaultTableModel model에 할당

		table = new JTable(model);			// model의 정보를 가진 JTable 할당 
		table.setFillsViewportHeight(true);	// table 창 체우기

		js = new JScrollPane(table);		// table 스크롤
		js.setBounds(10, 15, 540, 250);		// js의 좌표, 크기 설정 = table의 좌표,크기 

		String choice[] = { "FCFS", "SJF", "비선점 Priority", "선점 Priority", "RR", "SRT", "HRN" }; // 콤보 박스안에 들어갈 문자열
		Combo = new JComboBox<>();			// 컴포넌트 할당			
		for (int i = 0; i < choice.length; i++)
			Combo.addItem(choice[i]); 		// 반복문을 이용하여 콤보박스 안에 choice 배열을 하나씩 넣어준다
		Combo.setBounds(560, 233, 100, 30);	// 좌표와 크기 설정

		drawChart = new MyPanel();				// 객체 생성
		drawChart.setBackground(Color.WHITE);	// 패널의 배경 설정

		chartPane = new JScrollPane(drawChart);	// drawChart 스크롤
		chartPane.setBounds(10, 320, 650, 100);	// chartPane 좌표, 크기 설정 = chartPane 좌표,크기

		resultmodel = new DefaultTableModel(		// 결과값 테이블 첫 행 문자열을 설정한 DefaultTableModel 할당
				new String[] { "PID", "대기시간", "평균 대기시간", "응답시간", "평균 응답시간", "반환시간", "평균 반환시간" }, 0);
		resulttable = new JTable(resultmodel);		// resultmodel 데이터를 가진 JTable 할당
		resulttable.setFillsViewportHeight(true);	// resulttable 창 체우기

		resultPane = new JScrollPane(resulttable);	// resulttable 스크롤
		resultPane.setBounds(10, 430, 550, 270);	// resultPane 좌표,크기 설정 = resulttable 좌표,크기 설정
		
		resetBtn = new JButton("메모리 리셋");					// table의 모든 행을 삭제하는 버튼 생성
		resetBtn.setBounds(10, 280, 100, 25);				// 좌표와 크기 설정
		resetBtn.setBackground(new Color(242, 255, 237));	// 색상 설정

		addBtn = new JButton("프로세스 추가");					// table 안에 하나의 행을 추가하는 버튼 생성
		addBtn.setBounds(115, 280, 120, 25);				// 좌표와 크기 설정
		addBtn.setBackground(new Color(242, 255, 237));		// 색상 설정
	

		deChoiceBtn = new JButton("프로세스 선택 삭제");			// table 안에 선택한 행을 삭제하는 버튼 생성
		deChoiceBtn.setBounds(240, 280, 140, 25);			// 좌표와 크기 설정
		deChoiceBtn.setBackground(new Color(242, 255, 237));// 색상 설정
		
		deLastBtn = new JButton("프로세스 마지막 줄 삭제");		// table 안에 마지막 행을 삭제하는 버튼 생성
		deLastBtn.setBounds(385, 280, 170, 25);				// 좌표와 크기 설정
		deLastBtn.setBackground(new Color(242, 255, 237));	// 색상 설정
		
		resultBtn = new JButton("결과 보기");					// table 안에 데이터를 토대로 결과를 도출해줄 버튼 생성
		resultBtn.setBounds(560, 280, 100, 25);				// 좌표와 크기 설정
		resultBtn.setBackground(new Color(242, 255, 237));	// 색상 설정
		
		returnBtn = new JButton("다시하기");					// 결과를 본 후 table과 Combo의 선택값을 바꾸기 위해 선택하는 버튼 생성
		returnBtn.setBounds(565, 635, 95, 25);				// 좌표와 크기 설정
		returnBtn.setBackground(new Color(242, 255, 237));	// 색상 설정
		returnBtn.setEnabled(false);						// 버튼 초기 boolean값 false로 선택이 안되는 상태
	
		exitBtn = new JButton("종료");						// 결과를 본 후 프레임을 종료 시킬 수 있는 버튼 생성
		exitBtn.setBounds(565, 670, 95, 25);				// 좌표와 크기 설정
		exitBtn.setBackground(new Color(242, 255, 237));	// 색상 설정
		exitBtn.setEnabled(false);							// 버튼 초기 boolean값 false로 선택이 안되는 상태

		resetBtn.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {// 버튼이 눌리면
				int row = table.getRowCount();			// row에 table 행의 개수 저장
				if (row > 0) {							// row가 0이상 => table의 데이터가 빈 값이 아니면
					for (int i = row - 1; i > -1; i--)	// i는 row-1(인덱스 마지막 값부터),i는 감소, i>-1까지(0번 인덱스까지)
						model.removeRow(i);				// model 안 행 삭제
				}
			}
		});

		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 버튼이 눌리면
				model.addRow(blank);					// model에 blank(= 빈 행) 추가
			}
		});

		deChoiceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// 버튼이 눌리면
				int row = table.getSelectedRow();		// 선택된 행의 인덱스 값 row에 저장
				if (row > -1) {							// row(인덱스)가 -1이상 => 선택된게 없어 값이 없는 경우가 아니면
					model.removeRow(row);				// 선택된 행 삭제
				}
			}
		});

		deLastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// 버튼이 눌리면
				int row = table.getRowCount();			// 생성된 행의 개수 row에 저장
				if (row > 0) {							// row가 0이상 => 생성된 행이 있으면
					model.removeRow(row - 1);			// row-1(행의 개수-1=마지막 행의 인덱스)번째 행 삭제
				}
			}
		});

		resultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			// 버튼이 눌리면
				String selected = (String) Combo.getSelectedItem();	// Combo에서 선택된 문자열을 selected에 저장
				SchedulingManager scheduling;						// 객체 생성

				framePanel.add(resultPane);		// 결과 버튼을 눌러야 결과 테이블과
				framePanel.add(returnBtn);		// 다시하기 버튼,
				framePanel.add(exitBtn);		// 종료 버튼이 나타날 수 있도록 추가하였다

				resetBtn.setEnabled(false);		// 리셋 버튼, 
				addBtn.setEnabled(false);		// 추가 버튼,
				deChoiceBtn.setEnabled(false);	// 프로세스 선택 삭제 버튼,
				deLastBtn.setEnabled(false);	// 마지막 프로세스 삭제 버튼,
				resultBtn.setEnabled(false);	// 결과 버튼,
				Combo.setEnabled(false);		// 스케줄링 선택 콤보 박스
												// boolean 값 false로 선택할 수 없게하여 중간에 데이터가 꼬이지 않게한다.
				returnBtn.setEnabled(true);		// 다시하기 버튼,
				exitBtn.setEnabled(true);		// 종료 버튼 선택할 수 있도록 활성화

				X = 30;							// X=30 저장 

				switch (selected) {				// 선택된 스케줄링 방법이
				case "FCFS":					// FCFS면
					scheduling = new FCFS();	// FCFS 스케줄링 실행
					break;						// switch-case문 종료

				case "SJF":						// SJF면
					scheduling = new SJF();		// SJF 스케줄링 실행
					break;						// switch-case문 종료

				case "비선점 Priority":			// 비선점 Priority면
					scheduling = new NPRE();	// NPRE(비선점) 스케줄링 실행
					break;						// switch-case문 종료

				case "선점 Priority":				// 선점 Priority면
					scheduling = new PRE();		// PRE(선점) 스케줄링 실행
					break;						// switch-case문 종료

				case "HRN":						// HRN이면
					scheduling = new HRN();		// HRN 스케줄링 실행
					break;						// switch-case문 종료

				case "RR":						// RR이면
					String time = JOptionPane.showInputDialog("타임 슬라이스 (Time Quantum)");
					// 다이얼로그를 만들어 입력된 값을 time에 저장
					if (time == null) {
						return;					// 입력된 값이 없으면 리턴
					}
					scheduling = new RR();		// RR(라운드 로빈) 스케줄링 실행
					scheduling.setTimeQuantum(Integer.parseInt(time));
					// 입력된 값을 정수형으로 변환하여 스케줄링 매니저 setTimeQuantum에 전달
					break;						// switch-case문 종료

				case "SRT":						// SRT이면
					String srttime = JOptionPane.showInputDialog("타임 슬라이스 (Time Quantum)");
					// 다이얼로그를 만들어 입력된 값을 srttime에 저장
					if (srttime == null) {
						return;					// 입력된 값이 없으면 리턴
					}
					scheduling = new SRT();		// SRT 스케줄링 실행
					scheduling.setTimeQuantum(Integer.parseInt(srttime));	
					// 입력된 값을 정수형으로 변환하여 스케줄링 매니저 setTimeQuantum에 전달
					break;						// switch-case문 종료

				default:						// 예외처리
					return;
				}

				for (int i = 0; i <= model.getRowCount(); i++) {	// i=0부터 i++, i가 table의 행 개수까지
					resultmodel.addRow(blank);						// resulttable에 행 추가
																	// 즉,table의 행개수+1개 생성
				}

				for (int i = 0; i < model.getRowCount(); i++) {		// 0부터 table의 행 개수까지
					String pid = (String) model.getValueAt(i, 0);	
					// i번째 행의 0번째 열(프로세스 ID)의 값을 문자열 pid에 저장한다
					int arrive = Integer.parseInt((String) model.getValueAt(i, 1));	
					// i번째 행의 1번째 열(도착시간)의 값을 정수형 arrive에 저장한다
					int burst = Integer.parseInt((String) model.getValueAt(i, 2));
					// i번째 행의 2번째 열(실행시간)의 값을 정수형 burst에 저장한다.
					int priority = Integer.parseInt((String) model.getValueAt(i, 3));
					// i번째 행의 3번째 열(우선순위)의 값을 정수형 priority에 저장한다.
					scheduling.addProcess(new Process(pid, arrive, burst, priority));
					// 위에서 저장한 정보를 Process 생성자에 전달해 새로운 객체를 만들어 addProcess에 전달한다.
				}

				scheduling.scheduling();	// 추상 메소드

				for (int i = 0; i < model.getRowCount(); i++) {		// 0부터 table 행의 개수까지
					String pid = (String) model.getValueAt(i, 0);
					// i번째 행의 0번째 열(프로세스 ID)의 값을 문자열 pid에 저장한다
					Process process = scheduling.getProcess(pid);
					// pid를 전달해 proccess 정보를 받아온다
					resultmodel.setValueAt(pid, i, 0);
					// resulttable i에 0번째 열에 pid(프로세스이름)을 출력
					resultmodel.setValueAt(process.getWaitingTime(), i, 1);
					// resulttable i에 1번째 열에 pid에 해당하는 대기시간을 출력
					resultmodel.setValueAt(process.getResponseTime(), i, 3);
					// resulttable i에 3번째 열에 pid에 해당하는 응답시간을 출력
					resultmodel.setValueAt(process.getTurnAroundTime(), i, 5);
					// resulttable i에 5번째 열에 pid에 해당하는 반환시간을 출력
				}
				resultmodel.setValueAt(scheduling.getAverageWaitingTime(), model.getRowCount(), 2);
				// 모든 프로세스의 대기시간을 합해 나눈 평균대기 시간을 resulttabl (table의 행개수=결과테이블의 마지막 열)행, 2열에 출력
				resultmodel.setValueAt(scheduling.getResponseTime(), model.getRowCount(), 4);
				// 모든 프로세스의 응답시간을 합해 나눈 평균대기 시간을 resulttabl (table의 행개수=결과테이블의 마지막 열)행, 4열에 출력
				resultmodel.setValueAt(scheduling.getAverageTurnAroundTime(), model.getRowCount(), 6);
				// 모든 프로세스의 반환시간을 합해 나눈 평균대기 시간을 resulttabl (table의 행개수=결과테이블의 마지막 열)행, 6열에 출력
				drawChart.setTimeQuantum(scheduling.getCLists());
				// 간트 차트를 그릴 준비, chartlist 정보
			}
		});

		returnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// 버튼이 눌리면
				for (int i = resultmodel.getRowCount() - 1; i > -1; i--) {
					// i는 (resulttable에 행 개수-1)=마지막 인덱스부터, i--, i가 -1보다 클때까지(인덱스 번호 0번까지)
					resultmodel.removeRow(i); // 행 삭제
				}
				resetBtn.setEnabled(true);		// 메모리 리셋 버튼 활성화
				addBtn.setEnabled(true);		// 프로세스 추가 버튼 활성화
				deChoiceBtn.setEnabled(true);	// 프로세스 선택 삭제 버튼 활성화
				deLastBtn.setEnabled(true);		// 마지막 프로세스 삭제 버튼 활성화
				resultBtn.setEnabled(true);		// 결과 버튼 활성화		
				Combo.setEnabled(true);			// 스케줄링 방법
				returnBtn.setEnabled(false);	// 데이터가 꼬이지 않도록 다시하기 버튼 비활성화
			}
		});

		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// 버튼이 눌리면
				System.exit(0);								// 프로그램 종료
			}
		});

		framePanel = new JPanel(null);		// JPanel, 레이아웃 null 할당
		framePanel.add(js);					// js 프로세스 정보를 받을 스크롤(tabl(model)) 추가
		framePanel.add(Combo);				// Combo(스케줄링 방법 선택) 추가
		framePanel.add(resetBtn);			// resetBtn(메모리 리셋 버튼) 추가
		framePanel.add(addBtn);				// addBtn(프로세스 추가 버튼) 추가
		framePanel.add(deChoiceBtn);		// deChoiceBtn(선택한 프로세스 삭제 버튼) 추가
		framePanel.add(deLastBtn);			// deLastBtn(마지막 프로세스 삭제 버튼) 추가
		framePanel.add(resultBtn);			// resultBtn(결과보기 버튼) 추가
		framePanel.add(chartPane);			// chartPane 간트차트를 그릴 스크롤(drawChart) 추가
		framePanel.setBackground(new Color(255, 245, 245));	// 메인 배경 색상 설정

		setResizable(false);				// 확장 비활성화
		setBounds(350, 30, 680, 750);		// 좌표, 크기 설정
		setVisible(true);					// 프레임이 항상 보이도록 boolean값 true
		add(framePanel);					// 모든 컨포넌트를 추가한 패널을 추가
	}

	class MyPanel extends JPanel {			// 간트차트를 그릴 JPanel을 상속
		private List<ChartList> list;		// ChartList 정보를 가지는 리스트 list 생성

		public void paintComponent(Graphics g) {	// 오버라이딩
			super.paintComponent(g);				// 패널 내의 이전에 그려진 그래픽을 지우기 위해 호출

			if (list != null) {							// list가 null이 아닐때
				for (int i = 0; i < list.size(); i++) {	// 0부터 list의 크기까지(프로세스의 개수)
					ChartList chartList = list.get(i);	// i번 인덱스의 list 정보를 chartList에 저장

					int y = 40;							// 간트 차트의 y좌표
					int width = 40 * (i + 2);			// 작은 차트의 x좌표
					int height = 5;						// 작은 차트의 y좌표

					g.setColor(chartList.getColor());	// 프로세스마다의 색상으로 설정
					g.drawRect(width, height, 10, 10);	// 10*10크기의 정사각형을 그린다
					g.fillRect(width, height, 10, 10);	// 그린 정사각형을 같은 색으로 채운다
					g.setColor(Color.black);			// 색깔을 검정으로 설정
					g.drawString(chartList.getPid(), width, height + 20); 
					// 프로세스id를 같은 x좌표, height+20에 그려쓴다. 즉, 그린 정사각형 바로 밑에 pid 출력

					g.drawString(Integer.toString(chartList.getpStart()), X - 5, y + 45);	// 간트차트 시작점 밑 시작 시간 출력
					g.setColor(chartList.getColor());										// 프로세스 색상으로 설정
					g.drawRect(X, y, (chartList.getpFinish() - chartList.getpStart()) * 20, 25);
					g.fillRect(X, y, (chartList.getpFinish() - chartList.getpStart()) * 20, 25);
					// 프로세스 실행 시간(종료 시간-시작시간)마다 가로 20크기로 사각형을 그린다
					g.setColor(Color.black);	// 색깔 검정으로 설정
					g.drawString(chartList.getPid(), X + 5, y + 18);	
					// 그린 사각형의 x좌표+5, y+18=> 사각형 안에 프로세스 id를 출력

					X += (chartList.getpFinish() - chartList.getpStart()) * 20;
					// X좌표에 실행시간*20 = 이전에 그린 사각형의 좌표 크기만큼 더해준 뒤 저장
					if (i == list.size() - 1)	// 마지막 프로세스이면
                    {
                        g.drawString(Integer.toString(chartList.getpFinish()), X , y + 45);
                    }	// 끝나는 시간을 출력 
				}
			}
		}

		public void setTimeQuantum(List<ChartList> list) {
			this.list = list;	// 차트 리스트의 정보를 받아오고
			repaint();			// 한번 더 호출
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}