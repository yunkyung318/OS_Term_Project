
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SRT extends SchedulingManager {
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
        	// 프로세스들이 기다리고 있는 Ready Queue
            List<Process> readyQueue = new ArrayList();
            
            // 처음엔 첫 프로세스의 도착시간으로 초기화 했기에, 첫 프로세스 하나만 가져옴
            for (Process process : processes) {
            	if (process.getArriveTime() <= time) {
            		readyQueue.add(process);
                }
            }
            
            // Ready Queue에서 다음에 사용할 프로세스를 가져오기 쉽게 실행 시간이 짧은 기준으로 정렬
            Collections.sort(readyQueue, (Object o1, Object o2) -> {
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
            
            // 앞서 정렬된 Ready Queue에서 첫 번째 프로세스를 가져옴
            Process process = readyQueue.get(0);
            
            // oneBurstTime : 한번에 실행하게되는 시간 단위
        	// 시간할당량보다 프로세스의 남은 실행시간이 적다면 남은 실행시간으로,
        	// 시간할당량보다 프로세스의 남은 실행시간이 많다면 시간할당량으로 초기화
            int oneBurstTime = (process.getBurstTime() < timeQuantum ? process.getBurstTime() : timeQuantum);
            
            // 간트차트 리스트에 Ready Queue에서 첫 번째 프로세스를 가져와서 삽입
            // 현재 흐르는 시간인 time은 1씩 증가
            //this.getCLists().add(new ChartList(process.getPid(), time, ++time, process.getColor()));
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
                	// 다음 프로세스의 도착시간이 앞 프로세스 실행시간보다 늦었다면
                	// 앞서 수행했던 프로세스가 다시 실행되어야 하므로 그 자리에 다시 삽입
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
                
                if (processes.size() == 0 && process.getBurstTime() != 0) {
                	processes.add(process);
                }
            }
            
            // 시간이 1씩 흐름에 따라 남은 실행시간을 1 감소
            //process.setBurstTime(process.getBurstTime() - 1);
            
            // 현재 프로세스의 남은 실행시간이 없다면 기존 프로세스 목록에서 삭제 
            if (process.getBurstTime() == 0) {
                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).getPid().equals(process.getPid())) {
                    	processes.remove(i);
                        break;
                    }
                }
            }
            
            // Ready Queue에서 다음에 사용할 프로세스를 가져오기 쉽게 실행 시간이 짧은 기준으로 정렬
            Collections.sort(processes, (Object o1, Object o2) -> {
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
        }
        
        // 앞서 1초 time 단위로 끊어서 표현되었던 간트차트 내 같은 프로세스들을 통합
        // 뒤에서부터 시작하여 i번째 프로세스와 앞(i-1번째) 프로세스와 이름이 같다면
        // 앞 프로세스의 finish 시간을 길게 늘이고 i번째 프로세스를 삭제
        /*
        for (int i = this.getCLists().size() - 1; i > 0; i--) {
        	List<ChartList> cLists = this.getCLists();
            
        	if (cLists.get(i - 1).getPid().equals(cLists.get(i).getPid())) {
            	cLists.get(i - 1).setpFinish(cLists.get(i).getpFinish());
            	cLists.remove(i);
            }
        }
        */
        
        // {key : value}로 구성되는 Map 자료형 생성.
        // 여기서는 {Pid : 간트차트 리스트내 해당 프로세스의 종료시간}으로 설정
        Map map = new HashMap();
        
        // 프로세스별 대기시간, 응답시간, 반환시간 계산
        // 이중 for문으로 프로세스 리스트와 간트차트 리스트 내의 항목들을 하나씩 가리킴
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
                    	
                    	// 프로세스의 응답시간 계산 = 처음 프로세스가 들어온 후 시작시간
                        process.setResponseTime(chartList.getpStart() - process.getArriveTime());
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
