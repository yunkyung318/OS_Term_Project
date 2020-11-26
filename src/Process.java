
public class Process {
	private String pid; 		// 프로세스 이름 v
	private int arriveTime; 	// 도착 시간 v
	private int returnTime; 	// 종료 시간 
	private int waitingTime; 	// 대기 시간
	private int responseTime;	// 응답 시간
	private int burstTime; 		// 실행 시간 v
	private int turnAroundTime; // 반환 시간
	private int priority; 		// 우선 순위 v

	public Process(String pid,int arriveTime, int burstTime,int priority) {
		this.pid=pid;
		this.arriveTime=arriveTime;
		this.burstTime=burstTime;
		this.priority=priority;
	}
	
	public String getPid() {	// 프로세스 이름 반환
		return pid;
	}

	public void setPid(String pid) {	// 프로세스 이름 설정
		this.pid = pid;
	}

	public int getArriveTime() {	// 도착 시간 반환
		return arriveTime;
	}

	public void setArriveTime(int arriveTime) {	// 도착 시간 설정
		this.arriveTime = arriveTime;
	}

	public int getReturnTime() {	// 종료 시간 반환
		return returnTime;
	}

	public void setReturnTime(int returnTime) {	// 종료 시간 설정
		this.returnTime = returnTime;
	}

	public int getWaitingTime() {	// 대기 시간 반환
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {	// 대기 시간 설정
		this.waitingTime = waitingTime;
	}

	public int getResponseTime() {	// 응답 시간 반환
		return responseTime;
	}

	public void setResponseTime(int responseTime) {	// 응답 시간 설정
		this.responseTime = responseTime;
	}

	public int getBurstTime() {	// 실행 시간 반환
		return burstTime;
	}

	public void setBurstTime(int burstTime) {	// 실행 시간 설정
		this.burstTime = burstTime;
	}

	public int getTurnAroundTime() {	// 반환 시간 반환
		return turnAroundTime;
	}

	public void setTurnAroundTime(int turnAroundTime) {	// 반환 시간 설정
		this.turnAroundTime = turnAroundTime;
	}

	public int getPriority() {	// 우선 순위 반환
		return priority;
	}

	public void setPriority(int priority) {	// 우선 순위 설정
		this.priority = priority;
	}

}
