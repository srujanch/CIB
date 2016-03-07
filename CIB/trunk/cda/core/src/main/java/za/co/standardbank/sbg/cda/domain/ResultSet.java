package za.co.standardbank.sbg.cda.domain;

import java.util.List;

import com.vignette.as.client.api.bean.ManagedObjectBean;

public interface ResultSet {
	void setTotalResultsSize(int size);

	List getResults();

	void setResults(List results);

	void addResult(ManagedObjectBean bean);

	int getTotalResultsSize();
}
