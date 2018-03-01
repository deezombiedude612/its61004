import java.util.ArrayList;
import java.util.List;

public class MCQuestion {
	private String qDesc;
	private List<MCQAnswer> answers;
	
	public MCQuestion() {
		qDesc = "";
		answers = new ArrayList<>();
		
	}
	
	public MCQuestion(String qDesc) {
		this.qDesc = qDesc;
		answers = new ArrayList<>();
	}
	
	public String getqDesc() {
		return qDesc;
	}
	
	public void setqDesc(String qDesc) {
		this.qDesc = qDesc;
	}
	
	public List<MCQAnswer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(List<MCQAnswer> answers) {
		this.answers = answers;
	}
	
	public void addAnswers(boolean correct, String ansDesc) {
		answers.add(new MCQAnswer(correct, ansDesc));
	}
}
