
import java.util.Collections;
import java.util.List;

public class FCFS extends SchedulingManager {
    public void scheduling() {
    	// 프로세스 리스트를 도착시간 기준으로 정렬
        Collections.sort(this.getProcesses(), (Object o1, Object o2) -> {
            if (((Process) o1).getArriveTime() == ((Process) o2).getArriveTime()) {
                return 0;
            }
            else if (((Process) o1).getArriveTime() < ((Process) o2).getArriveTime()) {
                return -1;
            }
            else {
                return 1;
            }
        });
        
        List<ChartList> cLists = this.getCLists();
        
        // 프로세스 목록에서 프로세스를 하나씩 불러와 간트차트 리스트에 추가 
        for (Process process : this.getProcesses()) {
        	// 리스트가 비어있을 때 -> 처음 프로세스를 리스트에 삽입
            if (cLists.isEmpty()) {
            	cLists.add(new ChartList(process.getPid(), process.getArriveTime(),
                		process.getArriveTime() + process.getBurstTime(), process.getColor()));
            }
            // 리스트가 비어있지 않을 때 -> 계속 프로세스를 리스트에 삽입
            else {
            	ChartList chartList = cLists.get(cLists.size() - 1);
                cLists.add(new ChartList(process.getPid(), chartList.getpFinish(),
                		chartList.getpFinish() + process.getBurstTime(),process.getColor()));
            }
        }
        
        // 프로세스 별 대기 시간, 응답 시간, 반환 시간 계산
        for (Process process : this.getProcesses()) {
        	process.setWaitingTime(this.getChartList(process).getpStart() - process.getArriveTime());
        	process.setResponseTime(this.getChartList(process).getpStart() - process.getArriveTime());
        	process.setTurnAroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
