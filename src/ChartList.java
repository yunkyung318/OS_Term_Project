import java.awt.Color;

public class ChartList {
	private final String pid;		// 프로세스 이름
    private final int pStart;		// 간트차트 리스트에서 각 항목들의 시작시간
    private int pFinish;			// 간트차트 리스트에서 각 항목들의 종료시간
    private Color color;			// 색상 변수
    
    // 간트차트 리스트 생성자
    public ChartList(String pid, int pStart, int pFinish, Color color) {
        this.pid = pid;
        this.pStart = pStart;
        this.pFinish = pFinish;
        this.color = color;
    }

    // 프로세스 이름 반환
    public String getPid() {
		return pid;
	}
    
    // 시작시간 반환
    public int getpStart() {
		return pStart;
	}
    
    // 종료시간 반환
	public int getpFinish() {
		return pFinish;
	}

	// 종료시간 설정
	public void setpFinish(int pFinish) {
		this.pFinish = pFinish;
	}

	// 색상 반환
	public Color getColor() {
		return color;
	}

	// 색상 설정
	public void setColor(Color color) {
		this.color = color;
	}
}
