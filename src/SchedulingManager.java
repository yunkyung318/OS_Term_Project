import java.util.ArrayList;
import java.util.List;

public abstract class SchedulingManager {
	private List<Process> processes;	// ���μ������� list
	// private List<Event> timeflow; 		// ��Ʈ��Ʈ �׸���� list(?)
	private int timeQuantum;			// �ð��Ҵ緮(Ÿ�ӽ����̽�)
	
	/* Scheduling Manager Ŭ������ ������ */
	public SchedulingManager() {
		processes = new ArrayList();
		// timeflow = new ArrayList();
		timeQuantum = 1; // �ð��Ҵ緮(Ÿ�ӽ����̽�)�� �ʱⰪ 1�� ����
	}
	
	/* �ð��Ҵ緮(Ÿ�ӽ����̽�) setter */
	public void setTimeQuantum(int timeQuantum) {
		this.timeQuantum = timeQuantum;
	}
	
	/* �ð��Ҵ緮(Ÿ�ӽ����̽�) getter */
	public int getTimeQuantum() {
		return this.timeQuantum;
	}
	
	/* AWT(��� ��� �ð�) getter */
	public double getAvgWaitingTime() {
		double avg = 0.0;
		
		for (Process process : processes) {
			avg += process.getWaitingTime();
		}
		
		return avg / processes.size(); 
	}
	
	/* ART(��� ���� �ð�) getter */
	public double getAvgResponseTime() {
        double avg = 0.0;
        
        for (Process process : processes) {
            avg += process.getResponseTime();
        }
        
        return avg / processes.size();
    }
	
	/* ATT(��� ��ȯ �ð�) getter */
	public double getAvgTurnAroundTime() {
        double avg = 0.0;
        
        for (Process process : processes) {
            avg += process.getTurnAroundTime();
        }
        
        return avg / processes.size();
    }
}