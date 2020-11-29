import java.util.ArrayList;
import java.util.List;

public abstract class SchedulingManager {
	private final List<Process> processes;	// Process들의 리스트
    private final List<ChartList> cLists;	// 간트차트 그리기 전 리스트
    private int timeQuantum;		// 시간할당량
    
    // SchedulingManager 생성자
    public SchedulingManager() {
    	this.processes = new ArrayList();
    	this.cLists = new ArrayList();
        timeQuantum = 1;
    }
    
    // Process 리스트에 프로세스 추가
    public void addProcess(Process process) {
        processes.add(process);
    }
    
    // 시간할당량 설정 
    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
    
    // 시간할당량 반환
    public int getTimeQuantum() {
        return timeQuantum;
    }
    
    // 평균 대기 시간 반환
    public double getAverageWaitingTime() {
        double avg = 0.0;
        
        for (Process process : processes) {
            avg += process.getWaitingTime();
        }
        
        return avg / processes.size();
    }
    
    // 평균 반환 시간 변환
    public double getAverageTurnAroundTime() {
        double avg = 0.0;
        
        for (Process process : processes) {
            avg += process.getTurnAroundTime();
        }
        
        return avg / processes.size();
    }
    
    // 평균 응답 시간 반환
    public double getResponseTime() {
    	double avg = 0.0;
    	
    	for (Process process : processes) {
    		avg += process.getResponseTime();
    	}
    	
    	return avg / processes.size();
    }
    
    // 프로세스에 1:1로 대응하는 간트차트 리스트내 항목 반환
    public ChartList getChartList(Process process) {
        for (ChartList chartList : cLists) {
            if (process.getPid().equals(chartList.getPid())) {
                return chartList;
            }
        }
        
        return null;
    }
    
    // 간트차트 리스트내 항목에 1:1로 대응하는 프로세스 반환
    public Process getProcess(String chartListElement) {
        for (Process process : processes)  {
            if (process.getPid().equals(chartListElement)) {
                return process;
            }
        }
        
        return null;
    }

    // Process들의 리스트 반환
    public List<Process> getProcesses() {
        return processes;
    }
    
    // 간트차트 리스트 반환
    public List<ChartList> getCLists() {
        return cLists;
    }
    
    // Scheduling Algorithm 내에서 scheduling시 사용할 메소드
    public abstract void scheduling();
}
