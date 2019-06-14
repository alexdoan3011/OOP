/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Lab 3
* Date: Jan 21, 2019
*/
public class Client {
	private String firstName;
	private String lastName;
	private long phoneNum;
	private String email;

	Client(String firstName, String lastName, long phoneNum, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.email = email;
	}

	public String getName() {// concatenating name
		return firstName + " " + lastName;
	}

	public String getEmail() {
		return email;
	}

	public long getPhoneNum() {
		return phoneNum;
	}
}
