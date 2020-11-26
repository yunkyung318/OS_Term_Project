
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SJF extends SchedulingManager {
    @Override
    public void scheduling() {
    	// 일단은 프로세스 리스트를 도착시간 기준으로 정렬
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
        
        // 프로세스 리스트를 복사해 SJF에서 사용할 프로세스 리스트를 생성
        List<Process> processes = Copy.listCopy(this.getProcesses());
        
        // 한번에 가져올 프로세스 갯수를 정하기 위한 시간
        // 처음엔 첫번째 프로세스의 도착시간(0초)으로 초기화
        int time = processes.get(0).getArriveTime();
        
        while (!processes.isEmpty()) {
        	// 한번에 가져온 사용 가능한 프로세스들 리스트 생성
            List<Process> availableProcesses = new ArrayList();
            
            // 처음엔 첫 프로세스의 도착시간으로 초기화 했기에, 첫 프로세스 하나만 가져옴
            for (Process process : processes) {
                if (process.getArriveTime() <= time) {
                	availableProcesses.add(process);
                }
            }
            
            // 프로세스 리스트를 실행 시간이 짧은 기준으로 정렬
            Collections.sort(availableProcesses, (Object o1, Object o2) -> {
                if (((Process) o1).getBurstTime() == ((Process) o2).getBurstTime()) {
                    return 0;
                }
                else if (((Process) o1).getBurstTime() < ((Process) o2).getBurstTime()) {
                    return -1;
                }
                else {
                    return 1;
                }
            });
            
            // 사용 가능한 프로세스 리스트에서 첫 번째 프로세스를 가져옴
            Process process = availableProcesses.get(0);
            
            // 간트차트 리스트에 방금 가져온 프로세스를 삽입
            this.getCLists().add(new ChartList(process.getPid(), time, time + process.getBurstTime(), process.getColor()));
            // 한번에 가져올 기준 시간을 앞선 프로세스까지의 실행 시간만큼 증가
            time += process.getBurstTime();
            
            // 기존 프로세스들 리스트에서 해당 수행 완료한 프로세스 삭제 
            for (int i = 0; i < processes.size(); i++) {
                if (processes.get(i).getPid().equals(process.getPid())) {
                	processes.remove(i);
                    break;
                }
            }
        }
        
        // 프로세스 별 대기 시간, 응답 시간, 반환 시간 계산
        for (Process process : this.getProcesses()) {
        	process.setWaitingTime(this.getChartList(process).getpStart() - process.getArriveTime());
        	process.setResponseTime(this.getChartList(process).getpStart());
        	process.setTurnAroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
