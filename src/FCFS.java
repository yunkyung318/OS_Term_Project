import java.util.*;
// First Come First Served

public class FCFS extends SchedulingManager {
	public void FCFSAL() {
		List<Process> process = new ArrayList<Process>();
		int temp;
		for (int i = process.size(); i < 0; i--) {
			for (int j = i; j < i; j++) {
				if ((process.get(i)).getArriveTime() < (process.get(i + 1)).getArriveTime()) {
					temp=i+1;
				}

			}
		}
	}
}