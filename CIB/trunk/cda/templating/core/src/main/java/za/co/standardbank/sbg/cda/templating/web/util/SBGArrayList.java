package za.co.standardbank.sbg.cda.templating.web.util;

import java.util.ArrayList;

/**
 * @author Venkat.
 */
public class SBGArrayList extends ArrayList {
	private static final long serialVersionUID = 1L;
	private Object addItem;

	public Object getAddItem() {
		return addItem;
	}

	public void setAddItem(Object addItem) {
		this.addItem = addItem;
		super.add(addItem);
	}
}
