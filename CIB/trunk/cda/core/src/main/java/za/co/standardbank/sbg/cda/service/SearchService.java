package za.co.standardbank.sbg.cda.service;

import za.co.standardbank.sbg.cda.domain.ResultSet;
import za.co.standardbank.sbg.cda.domain.SearchFilter;

public interface SearchService extends Service {
	ResultSet getContentByFilter(SearchFilter filter);
}
