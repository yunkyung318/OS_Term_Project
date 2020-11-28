import java.awt.Color;

public class Process {
	private String pid; 		// 프로세스 이름 v
	private int arriveTime; 	// 도착 시간 v
	private int returnTime; 	// 종료 시간
	
	private int waitingTime; 	// 대기 시간
	private int responseTime;	// 응답 시간
	private int burstTime; 		// 실행 시간 v
	private int turnAroundTime; // 반환 시간
	private int priority; 		// 우선 순위 v
	
	private Color color;		// 색상 변수
	
	
	private Process(String pid, int arriveTime, int burstTime, int priority, int waitingTime, int turnAroundTime) {
        this.pid = pid;
        this.arriveTime = arriveTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = waitingTime;
        this.turnAroundTime = turnAroundTime;
        this.color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
    }

	public Process(String pid, int arriveTime, int burstTime,
    		int priority) {
        this(pid, arriveTime, burstTime, priority, 0, 0);
    }
	
	// 프로세스 이름 반환
	public String getPid() {
		return pid;
	}

	// 프로세스 이름 설정
	public void setPid(String pid) {
		this.pid = pid;
	}

	// 도착 시간 반환
	public int getArriveTime() {
		return arriveTime;
	}

	// 도착 시간 설정
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}

	// 종료 시간 반환
	public int getReturnTime() {
		return returnTime;
	}

	// 종료 시간 설정
	public void setReturnTime(int returnTime) {
		this.returnTime = returnTime;
	}

	// 대기 시간 반환
	public int getWaitingTime() {
		return waitingTime;
	}

	// 대기 시간 설정
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	// 응답 시간 반환
	public int getResponseTime() {
		return responseTime;
	}

	// 응답 시간 설정
	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	// 실행 시간 반환
	public int getBurstTime() {
		return burstTime;
	}

	// 실행 시간 설정
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	// 반환 시간 반환
	public int getTurnAroundTime() {
		return turnAroundTime;
	}

	// 반환 시간 설정
	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	// 우선 순위 반환
	public int getPriority() {
		return priority;
	}

	// 우선 순위 설정
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	// 색상 반환
	public Color getColor() {
    	return this.color;
    }
	
	// 색상 설정
	public void setColor(Color color) {
    	this.color = color;
    }
	
	public double getHRNCalc() {
    	return ((this.getWaitingTime()+this.getBurstTime())/this.getBurstTime());
    }
}
