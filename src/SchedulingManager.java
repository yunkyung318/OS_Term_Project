import java.util.ArrayList;
import java.util.List;

public abstract class SchedulingManager {
	private List<Process> processes;	// 프로세스들의 list
	private List<Process> timeflow;		// 간트차트 그리기용 list(?)
	private int timeQuantum;			// 시간할당량(타임슬라이스)
	
	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

	/* Scheduling Manager 클래스의 생성자 */
	public SchedulingManager() {
		processes = new ArrayList();
		timeflow = new ArrayList();
		timeQuantum = 1; // 시간할당량(타임슬라이스)은 초기값 1로 지정
	}
	
	/* 시간할당량(타임슬라이스) setter */
	public void setTimeQuantum(int timeQuantum) {
		this.timeQuantum = timeQuantum;
	}
	
	/* 시간할당량(타임슬라이스) getter */
	public int getTimeQuantum() {
		return this.timeQuantum;
	}
	
	/* AWT(평균 대기 시간) getter */
	public double getAvgWaitingTime() {
		double avg = 0.0;
		
		for (Process process : processes) {
			avg += process.getWaitingTime();
		}
		
		return avg / processes.size(); 
	}
	
	/* ART(평균 응답 시간) getter */
	public double getAvgResponseTime() {
        double avg = 0.0;
        
        for (Process process : processes) {
            avg += process.getResponseTime();
        }
        
        return avg / processes.size();
    }
	
	/* ATT(평균 반환 시간) getter */
	public double getAvgTurnAroundTime() {
        double avg = 0.0;
        
        for (Process process : processes) {
            avg += process.getTurnAroundTime();
        }
        
        return avg / processes.size();
    }
}