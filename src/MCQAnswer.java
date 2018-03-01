public class MCQAnswer {
	private boolean correct;
	private String ansDesc;
	
	public MCQAnswer() {
		correct = false;
		ansDesc = "";
	}
	
	public MCQAnswer(boolean correct, String ansDesc) {
		this.correct = correct;
		this.ansDesc = ansDesc;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public String getAnsDesc() {
		return ansDesc;
	}
	
	public void setAnsDesc(String ansDesc) {
		this.ansDesc = ansDesc;
	}
}
