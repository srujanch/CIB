package za.co.standardbank.sbg.cda.mvc.portlet.domain;

import java.io.Serializable;

public class ContactMeConfigInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String returnURL;

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public ContactMeConfigInfo(String returnURL) {
		super();
		this.returnURL = returnURL;
	}

	public ContactMeConfigInfo() {
	}

	public String toString() {
		StringBuffer contactStr = new StringBuffer("Contact =[ Return URL = ");
		contactStr.append(returnURL);
		contactStr.append(" ]");
		return contactStr.toString();
	}
}
