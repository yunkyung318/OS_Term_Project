import java.util.ArrayList;
import java.util.List;

public class Copy {
	public static List<Process> listCopy(List<Process> oldList) {
        List<Process> newList = new ArrayList();
        
        for (Process process : oldList) {
            newList.add(new Process(process.getPid(), process.getArriveTime(), process.getBurstTime(), process.getPriority()));
        }
        
        return newList;
    }
}
