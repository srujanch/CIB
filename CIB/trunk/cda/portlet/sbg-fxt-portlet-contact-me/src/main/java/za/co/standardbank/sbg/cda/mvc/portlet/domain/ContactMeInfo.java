package za.co.standardbank.sbg.cda.mvc.portlet.domain;

import java.io.Serializable;

public class ContactMeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String phone;
	private String subject;
	private String description;

	public ContactMeInfo(String name, String email, String phone, String subject, String description) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.subject = subject;
		this.description = description;
	}

	public ContactMeInfo() {
	}

	public String toString() {
		StringBuffer contactStr = new StringBuffer("Contact =[ Name = ");
		contactStr.append(name);
		contactStr.append(", Email = ");
		contactStr.append(email);
		contactStr.append(", Phone Number = ");
		contactStr.append(phone);
		contactStr.append(",  Email = ");
		contactStr.append(subject);
		contactStr.append(",  Description = ");
		contactStr.append(description);
		contactStr.append(" ]");
		return contactStr.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
