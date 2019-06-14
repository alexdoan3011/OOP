
public class Student extends Person {
	
	private int stdId;
	
	public int getStdId() {
		return stdId;
	}

	public void setStdId(int stdId) {
		this.stdId = stdId;
	}

	public String getName()
	{
		return "Student :"+name;
	}

	public void standByMe()
	{
		stand();
	}
}
