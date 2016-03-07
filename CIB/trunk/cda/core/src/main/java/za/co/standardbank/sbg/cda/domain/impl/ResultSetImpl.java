package za.co.standardbank.sbg.cda.domain.impl;


import java.util.ArrayList;
import java.util.List;

import za.co.standardbank.sbg.cda.domain.ResultSet;

import com.vignette.as.client.api.bean.ManagedObjectBean;

public class ResultSetImpl implements ResultSet {

	private List results = new ArrayList();
	private int totalResultsSize = 0;

	/**
	 * Returns the totalResultsSize attribute.
	 * 
	 * @return totalResultsSize attribute
	 */
	public int getTotalResultsSize() {
		return totalResultsSize;
	}

	/**
	 * Sets the totalResultsSize attribute.
	 * 
	 * @param totalResultsSize
	 *            - value of the totalResultsSize attribute
	 */
	public void setTotalResultsSize(int totalResultsSize) {
		this.totalResultsSize = totalResultsSize;
	}

	public final List getResults() {
		return results;
	}

	public final void setResults(List results) {
		this.results = results;
	}

	public final void addResult(ManagedObjectBean bean) {
		this.results.add(bean);
	}

}
