
import java.util.Collections;
import java.util.List;

/*
FCFS

준비큐에 도착한 순서대로 CPU를 할당
도착시간 기준으로 정렬하여 간트차트를 그리고 값 계산
*/

public class FCFS extends SchedulerManage {
    public void process() {
        // sort로 프로세스 목록들을 정렬.
        // 두번째 인자는 정렬 기준 -> 기준은 i(o1)번과 i+1(o2)번을 가지고 도착시간을 비교해 오름차순으로 정렬
        Collections.sort(this.getRows(), (Object o1, Object o2) -> {
            if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
                return 0;
            }
            else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
                return -1;
            }
            else {
                return 1;
            }
        });
        
        // 이벤트 처리를 위한 리스트 생성
        // timeflow 배열의 한 원소는 차례로 프로세스 이름, 시작시간, 종료시간으로 이루어짐
        List<Event> timeflow = this.getTimeflow();
        
        // 정렬된 프로세스 목록들을 불러와 하나씩 add
        for (Row row : this.getRows()) {
            if (timeflow.isEmpty()) {
                timeflow.add(new Event(row.getProcessName(), row.getArrivalTime(),
                		row.getArrivalTime() + row.getBurstTime(),row.getColor()));
            }
            else {
                Event event = timeflow.get(timeflow.size() - 1);

                // Event 클래스의 생성자 호출
                // Event 클래스 생성자의 매개변수는 차례로 프로세스 이름, 시작시간, 종료시간
                timeflow.add(new Event(row.getProcessName(), event.getFinishTime(),
                		event.getFinishTime() + row.getBurstTime(),row.getColor()));
            }
        }
        
        // 프로세스 목록들을 불러와 대기시간과 반환시간을 계산
        // 대기시간: 프로세스가 CPU를 할당받기 전까지 대기하는 시간
                // 대기시간 = 시작시간 - 도착시간
        // 반환시간: 프로세스가 준비큐에 도착해 대기 후 실행완료까지 걸리는 총 시간
                // 반환시간 = 대기시간 + 실행시간
        for (Row row : this.getRows()) {
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
