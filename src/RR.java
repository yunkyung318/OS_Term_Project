
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RR extends SchedulingManager {
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
        
        List<Process> processes = Copy.listCopy(this.getProcesses());
        
        
        // time : 현재 Scheduling 중 흐르는 현재 시간
        // 초기값은 첫 프로세스의 도착시간인 0으로 설정
        int time = processes.get(0).getArriveTime();
        
        // 시간할당량 설정
        int timeQuantum = this.getTimeQuantum();
        
        // 프로세스 리스트에서 하나씩 삭제할 예정이므로 프로세스 리스트에 데이터가 있는 동안 반복
        while (!processes.isEmpty()) {
        	// 프로세스 리스트의 첫번째 프로세스를 가져옴
        	Process process = processes.get(0);
        	
        	// oneBurstTime : 한번에 실행하게되는 시간 단위
        	// 시간할당량보다 프로세스의 남은 실행시간이 적다면 남은 실행시간으로,
        	// 시간할당량보다 프로세스의 남은 실행시간이 많다면 시간할당량으로 초기화
            int oneBurstTime = (process.getBurstTime() < timeQuantum ? process.getBurstTime() : timeQuantum);
            
            // 간트차트 리스트에 프로세스 리스트의 첫 번째 프로세스를 가져와서 삽입
            // 현재 흐르는 시간의 단위는 앞서 정한 실행 단위를 기존 시간에 더하여 종료시간 계산
            this.getCLists().add(new ChartList(process.getPid(), time, time + oneBurstTime, process.getColor()));
            
            // 다음 시작시간 = 현재 시간 + 한번에 실행하는 시간 단위
            time += oneBurstTime;
            
            // 프로세스 리스트에서 첫번째 프로세스 삭제
            // (삭제한 뒤 추후 작업 큐의 맨 뒤에 삽입 예정)
            processes.remove(0);
            
            // 실행시간이 시간할당량보다 많으면 프로세스의 남은 실행시간에서 시간할당량을 빼줌
            if (process.getBurstTime() > timeQuantum) {
            	process.setBurstTime(process.getBurstTime() - timeQuantum);
                
            	
                for (int i = 0; i < processes.size(); i++) {
                	// 이게 뭐지.. 뭘까...
                    if (processes.get(i).getArriveTime() > time) {
                    	processes.add(i, process);
                        break;
                    }
                    // 마지막 프로세스일 경우
                    else if (i == processes.size() - 1) {
                    	processes.add(process);
                        break;
                    }
                }
            }
        }
        
        // {key : value}로 구성되는 Map 자료형 생성.
        // 여기서는 {Pid : 간트차트 리스트내 해당 프로세스의 종료시간}으로 설정
        Map map = new HashMap();
        
        // 프로세스별 대기시간, 응답시간, 반환시간 계산
        // 이중 for문으로 프로세스 리스트와 간트차트 리스트 내의 항목들을 하나 가리킴
        for (Process process : this.getProcesses()) {
            map.clear();
            
            // 입력된 프로세스를 하나씩 순회하면서 간트차트 리스트에 있는지 확인 후,
            for (ChartList chartList : this.getCLists()) {
            	
            	// 현재 서로 가리키는 프로세스가 같다면
                if (chartList.getPid().equals(process.getPid())) {
                	
                	// Map에 현재 가리키는 프로세스의 정보가 이미 있다면
                	// (프로세스가 이미 전에 들어왔었고, 두번째로 다시 할당받아 들어온 경우)
                    if (map.containsKey(chartList.getPid())) {
                    	
                    	
                    	// 현재 간트차트 리스트의 시작시간에서 앞서 들어왔었던 때의 종료시간을 빼준다.
                    	// (같은 프로세스들 사이의 중간 대기 시간을 계산)
                        int w = chartList.getpStart() - (int) map.get(chartList.getPid());
                        
                        // 기존 계산되었던 대기시간에 방금 계산한 대기시간을 더함
                        process.setWaitingTime(process.getWaitingTime() + w);
                    }
                    // Map에 현재 가리키는 프로세스의 정보가 없다면
                    // (처음 프로세스가 할당받은 경우의 대기시간, 응답시간 계산)
                    else {
                    	// 대기시간 = 시작시간 - 도착시간
                    	process.setWaitingTime(chartList.getpStart() - process.getArriveTime());
                    	
                    	// 응답시간 = 처음 프로세스가 들어온 후 시작시간
                    	process.setResponseTime(chartList.getpStart());
                    }
                    
                    // 프로세스의 종료시간 업데이트
                    map.put(chartList.getPid(), chartList.getpFinish());
                }
            }
            // 프로세스의 반환시간 계산 (대기시간 + 실행시간)
            process.setTurnAroundTime(process.getWaitingTime() + process.getBurstTime());
        }
    }
}
