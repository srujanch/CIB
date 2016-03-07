package za.co.standardbank.sbg.cda.portal.search.opentext;

import com.vignette.portal.search.internal.connectors.opentext.OpenTextSearchConnector;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epicentric.authentication.AuthenticationManager;
import com.epicentric.authentication.JNDIAuthenticator;
import com.epicentric.authentication.internal.AuthenticatorWrapper;
import com.epicentric.common.StringUtils;
import com.epicentric.user.User;
import com.vignette.opentext.webapi.LoginFailureException;
import com.vignette.opentext.webapi.search.SearchFunc;
import com.vignette.opentext.webapi.search.SearchResult;
import com.vignette.opentext.webapi.search.WhereClause;
import com.vignette.portal.log.LogWrapper;
import com.vignette.portal.search.ConnectorSearchContext;
import com.vignette.portal.search.DoubleValueSearchTerm;
import com.vignette.portal.search.SearchCriteria;
import com.vignette.portal.search.SearchException;
import com.vignette.portal.search.SearchResults;
import com.vignette.portal.search.SearchTerm;
import com.vignette.portal.search.SingleValueSearchTerm;
import com.vignette.portal.search.internal.connectors.opentext.OpenTextSearchConnector;

public class CIBOpenTextSearchConnector extends OpenTextSearchConnector{
	
	private static final LogWrapper LOG = new LogWrapper(CIBOpenTextSearchConnector.class);

	@Override
	public SearchResults executeBasicSearch(String text, Set targetCollectionNames, ConnectorSearchContext context, boolean recursive)
	        throws SearchException
	    {
		 	LOG.info("@@@ Inside CIBOpenTextSearchConnector - executeBasicSearch @@@ with collection names :: " +targetCollectionNames);
	        if(text == null || "".equals(text) || targetCollectionNames == null || targetCollectionNames.isEmpty() || context == null)
	        {
	            return null;
	        } else
	        {
	        	text= text.trim();
	        	text=text.replaceAll("(\\s)+", " ");
	        	text=text.replaceAll("(\\s)+", "+");
	            SearchFunc searchFunc = new SearchFunc();
	            WhereClause wc = new WhereClause();
	            wc.setWhere(text);
	            wc.setWithin(com.vignette.opentext.webapi.search.SearchFunc.Within.all);
	            searchFunc.getWhereClauses().add(wc);
	            return getLivelinkResults(searchFunc, targetCollectionNames, context);
	        }
	    }
	
	
	@Override
	public SearchResults executeAdvancedSearch(SearchCriteria searchCriteria, Set targetCollectionNames, ConnectorSearchContext context, boolean recursive)
	        throws SearchException {
		SearchFunc searchFunc;
        String queryFromDateRange;
        if(searchCriteria == null || searchCriteria.getSearchTerms().isEmpty() || targetCollectionNames == null || targetCollectionNames.isEmpty() || context == null)
        {
            return null;
        }
        searchFunc = new SearchFunc();
        queryFromDateRange = "";
        LinkedList andList = new LinkedList();
        LinkedList orList = new LinkedList();
        LinkedList notList = new LinkedList();
        StringBuffer bodyANDField = new StringBuffer();
        StringBuffer bodyNOTField = new StringBuffer();
        StringBuffer bodyORField = new StringBuffer();
        StringBuffer queryFromBody = new StringBuffer();
        Map operatorMap = new HashMap();
        Iterator iter = searchCriteria.getSearchTerms().iterator();
        do
        {
            if(!iter.hasNext())
            {
                break;
            }
            Object o = iter.next();
            SearchTerm term = (SearchTerm)o;
            String oper = term.getOperator();
            if(term instanceof SingleValueSearchTerm)
            {
                SingleValueSearchTerm sterm = (SingleValueSearchTerm)term;
                if("body".equalsIgnoreCase(sterm.getName()))
                {
                    boolean exactPhrase = false;
                    if("Contains phrase".equals(oper) || "Could contain phrase".equals(oper) || "Does not contain phrase".equals(oper))
                    {
                        exactPhrase = true;
                    }
                    if("Contains".equals(oper) || "Could contain".equals(oper) || "Does not contain".equals(oper))
                    {
                        String searchTexts[] = StringUtils.split(sterm.getValue(), " ");
                        if("Contains".equals(oper))
                        {
                            String arr$[] = searchTexts;
                            int len$ = arr$.length;
                            int i$ = 0;
                            while(i$ < len$) 
                            {
                                String searchText = arr$[i$];
                                if(bodyANDField.length() > 0)
                                {
                                    bodyANDField.append(getBodyQLRegion(" AND ", searchText, false));
                                } else
                                {
                                    bodyANDField.append(getBodyQLRegion("", searchText, false));
                                }
                                i$++;
                            }
                        } else
                        if("Could contain".equals(oper))
                        {
                            String arr$[] = searchTexts;
                            int len$ = arr$.length;
                            int i$ = 0;
                            while(i$ < len$) 
                            {
                                String searchText = arr$[i$];
                                bodyORField.append(getBodyQLRegion(" OR ", searchText, false));
                                i$++;
                            }
                        } else
                        if("Does not contain".equals(oper))
                        {
                            String arr$[] = searchTexts;
                            int len$ = arr$.length;
                            int i$ = 0;
                            while(i$ < len$) 
                            {
                                String searchText = arr$[i$];
                                bodyNOTField.append(getBodyQLRegion(" AND NOT ", searchText, false));
                                i$++;
                            }
                        }
                    } else
                    if("Contains phrase".equals(oper))
                    {
                        if(bodyANDField.length() > 0)
                        {
                            bodyANDField.append(getBodyQLRegion(" AND ", sterm.getValue(), exactPhrase));
                        } else
                        {
                            bodyANDField.append(getBodyQLRegion("", sterm.getValue(), exactPhrase));
                        }
                    } else
                    if("Could contain phrase".equals(oper))
                    {
                        bodyORField.append(getBodyQLRegion(" OR ", sterm.getValue(), exactPhrase));
                    } else
                    if("Does not contain phrase".equals(oper))
                    {
                        bodyNOTField.append(getBodyQLRegion(" AND NOT ", sterm.getValue(), exactPhrase));
                    }
                } else
                if("Contains".equals(oper) || "Contains phrase".equals(oper))
                {
                    andList.add(sterm);
                } else
                if("Could contain".equals(oper) || "Could contain phrase".equals(oper))
                {
                    orList.add(sterm);
                } else
                if("Does not contain".equals(oper) || "Does not contain phrase".equals(oper))
                {
                    notList.add(sterm);
                }
            } else
            if(term instanceof DoubleValueSearchTerm)
            {
                DoubleValueSearchTerm dterm = (DoubleValueSearchTerm)term;
                String name = dterm.getName();
                Object startDate1 = dterm.getValues()[0];
                Object endDate1 = dterm.getValues()[1];
                queryFromDateRange = getDateFieldSpecifier(name, startDate1, endDate1);
            }
        } while(true);
        operatorMap.put(" AND ", andList);
        operatorMap.put(" OR ", orList);
        operatorMap.put(" AND NOT ", notList);
        String queryFromOthers = generateQueryFromOtherFields(operatorMap);
        String queryFromOR = generateQueryFromORFields(operatorMap);
        queryFromBody.append(bodyANDField.toString()).append(bodyNOTField.toString());
        if(bodyORField.length() > 0)
        {
            if(queryFromBody.length() > 0)
            {
                queryFromBody.append(" AND ");
            }
            queryFromBody.append("(");
            queryFromBody.append(bodyORField);
            queryFromBody.append(")");
        }
        if(queryFromBody.length() > 0)
        {
            WhereClause wc = new WhereClause();
            wc.setWhere(queryFromBody.toString());
            wc.setLookfor(com.vignette.opentext.webapi.search.SearchFunc.Lookfor.complexquery);
            wc.setWithin(com.vignette.opentext.webapi.search.SearchFunc.Within.content);
            wc.setBool(com.vignette.opentext.webapi.search.SearchFunc.Bool.and);
            searchFunc.getWhereClauses().add(wc);
        }
        if(queryFromOthers.length() > 0)
        {
            WhereClause wc = new WhereClause();
            wc.setWhere(queryFromOthers);
            wc.setBool(com.vignette.opentext.webapi.search.SearchFunc.Bool.and);
            wc.setLookfor(com.vignette.opentext.webapi.search.SearchFunc.Lookfor.complexquery);
            searchFunc.getWhereClauses().add(wc);
        }
        if(queryFromDateRange.length() > 0)
        {
            WhereClause wc = new WhereClause();
            wc.setWhere(queryFromDateRange);
            wc.setBool(com.vignette.opentext.webapi.search.SearchFunc.Bool.and);
            wc.setLookfor(com.vignette.opentext.webapi.search.SearchFunc.Lookfor.complexquery);
            searchFunc.getWhereClauses().add(wc);
        }
        if(queryFromOR.length() > 0)
        {
            WhereClause wc = new WhereClause();
            wc.setWhere(queryFromOR);
            wc.setBool(com.vignette.opentext.webapi.search.SearchFunc.Bool.and);
            wc.setLookfor(com.vignette.opentext.webapi.search.SearchFunc.Lookfor.complexquery);
            searchFunc.getWhereClauses().add(wc);
        }
        return getLivelinkResults(searchFunc, targetCollectionNames, context);
	}


	public SearchResults getLivelinkResults(SearchFunc searchFunc, Set targetCollectionNames, ConnectorSearchContext context)
		        throws SearchException
		    {
		        int start = context.getStartPosition();
		        int pageSize = context.getPageSize();
		        boolean isGuestUser = false;
		        String username = null;
		        User user = context.getUser();
		        isGuestUser = user.isGuestUser();
		        if(!isGuestUser)
		        {
		            username = (String)user.getProperty("logon");
		        }
		        LOG.debug((new StringBuilder()).append("Searching Livelink for: ").append(searchFunc.getFuncName()).append(" (start=").append(start).append(", pageSize=").append(pageSize).append(")").toString());
		        List authMgrList = AuthenticationManager.getDefaultAuthenticationManager().getAuthenticators();
		        Iterator i$ = authMgrList.iterator();
		        do
		        {
		            if(!i$.hasNext())
		            {
		                break;
		            }
		            Object anAuthMgrList = i$.next();
		            AuthenticatorWrapper authWrapper = (AuthenticatorWrapper)anAuthMgrList;
		            if(!(authWrapper.getAuthenticator() instanceof JNDIAuthenticator))
		            {
		                continue;
		            }
		            if(isGuestUser)
		            {
		                LOG.debug("Portal configured with LDAP. Performing guest-user search operation on ContentSe" +
		"rver."
		);
		            } else
		            {
		                //searchFunc.setUserLogin(username);
		            }
		            break;
		        } while(true);
		        WhereClause wc = null;
		        Iterator iter = targetCollectionNames.iterator();
		        do
		        {
		            if(!iter.hasNext())
		            {
		                break;
		            }
		            Object targetCollectionName = iter.next();
		            String collection = targetCollectionName.toString();
		            String otLocation = (String)config.getIdsMap().get(collection);
		            if(otLocation != null && otLocation.trim().length() > 0)
		            {
		                String query = getQLRegion("OTLocation", otLocation, true);
		                wc = new WhereClause();
		                wc.setWhere(query);
		                wc.setBool(com.vignette.opentext.webapi.search.SearchFunc.Bool.and);
		                wc.setLookfor(com.vignette.opentext.webapi.search.SearchFunc.Lookfor.complexquery);
		                searchFunc.getWhereClauses().add(wc);
		            }
		        } while(true);
		        Object targetC[] = targetCollectionNames.toArray();
		        String collections[] = (String[])Arrays.asList(targetC).toArray(new String[targetC.length]);
		        searchFunc.setSlice(collections);
		        try
		        {
		            searchFunc.setStartAt(start + 1);
		            searchFunc.setGoFor(pageSize);
		            otSession.execute(searchFunc);
		        }
		        catch(LoginFailureException le)
		        {
		            if(LOG.willLogAtLevel(4))
		            {
		                LOG.info((new StringBuilder()).append("Error Logging into Livelink: ").append(le.getMessage()).toString());
		            }
		            throw new SearchException((new StringBuilder()).append("Error Logging into Livelink: ").append(le.getMessage()).toString());
		        }
		        catch(IOException ioe)
		        {
		            if(LOG.willLogAtLevel(4))
		            {
		                LOG.info((new StringBuilder()).append("Error getting OpenText slices: ").append(ioe.getMessage()).toString());
		            }
		            throw new SearchException((new StringBuilder()).append("Error getting OpenText slices: ").append(ioe.getMessage()).toString());
		        }
		        List searchResults = searchFunc.getSearchResults();
		        com.vignette.portal.search.SearchResult r[] = new com.vignette.portal.search.SearchResult[searchResults.size()];
		        for(int i = 0; i < searchResults.size(); i++)
		        {
		            SearchResult result = (SearchResult)searchResults.get(i);
		            Calendar cal = new GregorianCalendar();
		            if(result.getObjectDate() != null)
		            {
		                cal = new GregorianCalendar();
		                cal.setTime(result.getObjectDate());
		            }
		            String urlstr = "";
		            if(result.getViewURL() != null)
		            {
		                urlstr = result.getViewURL().toString();
		            }
		            
		            /*
		            * Pseudo for the Title.
		            * =====================
		            * if OTDoctitle is not null
		            *   set it to name
		            * else if OTFilterTitle is not null
		            *   set it to name
		            * else set results.getName() to name
		            * */
                    String name;
		            String docTitle = result.getExtraFields().get("OTDOCtitle");
                    String filterTitle = result.getExtraFields().get("OTFilterTitle");
		            if(docTitle != null && !"".equalsIgnoreCase(docTitle)){
		            	name = docTitle;
		            }else if(filterTitle != null && !"".equalsIgnoreCase(filterTitle)){
                        name = filterTitle;
                    }else{
                        name = result.getName();
                    }
		            /*if(name !=null && !name.equalsIgnoreCase("")){
		            }else{
		            	name = result.getExtraFields().get("OTDOCtitle");
		            }*/

		            String summary;
                    String otDocDescription = result.getExtraFields().get("OTDOCDescription");
		            if(otDocDescription != null && !otDocDescription.equalsIgnoreCase("")){
                        summary = otDocDescription;
		            }else{
		            	summary = result.getSummary();
		            }
		            
		            LOG.info("@@@ Inside CIBOpenTextSearchConnector @@@ with title::: "+name+" and desc :: " +summary);
		           
		            r[i] = new com.vignette.portal.search.SearchResult(name, summary, null, cal, urlstr, result.getScore(), 0L, config.getId());
		        }

		        return new SearchResults(config.getId(), searchFunc.getTotalResults(), r);
		    }
	
	
	private String generateQueryFromOtherFields(Map map)
    {
        LinkedList andList = (LinkedList)map.get(" AND ");
        LinkedList notList = (LinkedList)map.get(" AND NOT ");
        StringBuffer queryBuf = new StringBuffer();
        boolean emptyQuery = true;
        if(andList != null && andList.size() > 0)
        {
            for(int i = 0; i < andList.size(); i++)
            {
                SingleValueSearchTerm sterm = (SingleValueSearchTerm)andList.get(i);
                if("Contains phrase".equals(sterm.getOperator()))
                {
                    if(queryBuf.length() > 0)
                    {
                        queryBuf.append(getQLRegion(sterm.getName(), " AND ", sterm.getValue(), true));
                    } else
                    {
                        queryBuf.append(getQLRegion(sterm.getName(), "", sterm.getValue(), true));
                    }
                    continue;
                }
                String searchTexts[] = StringUtils.split(sterm.getValue(), " ");
                String arr$[] = searchTexts;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String searchText = arr$[i$];
                    queryBuf.append(getQLRegion(sterm.getName(), " AND ", searchText, false));
                }

            }

            emptyQuery = false;
        }
        if(notList != null && notList.size() > 0)
        {
            for(int i = 0; i < notList.size(); i++)
            {
                SingleValueSearchTerm sterm = (SingleValueSearchTerm)notList.get(i);
                if("Does not contain phrase".equals(sterm.getOperator()))
                {
                    queryBuf.append(getQLRegion(sterm.getName(), " AND NOT ", sterm.getValue(), true));
                    continue;
                }
                String searchTexts[] = StringUtils.split(sterm.getValue(), " ");
                String arr$[] = searchTexts;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String searchText = arr$[i$];
                    queryBuf.append(getQLRegion(sterm.getName(), " AND NOT ", searchText, false));
                }

            }

            emptyQuery = false;
        }
        if(!emptyQuery)
        {
            queryBuf.append(")");
        }
        return queryBuf.toString();
    }
	

    private String generateQueryFromORFields(Map map)
    {
        LinkedList orList = (LinkedList)map.get(" OR ");
        StringBuffer queryBuf = new StringBuffer();
        boolean emptyQuery = true;
        if(orList != null && orList.size() > 0)
        {
            for(int i = 0; i < orList.size(); i++)
            {
                SingleValueSearchTerm sterm = (SingleValueSearchTerm)orList.get(i);
                if("Could contain phrase".equals(sterm.getOperator()))
                {
                    queryBuf.append(getQLRegion(sterm.getName(), " OR ", sterm.getValue(), true));
                    continue;
                }
                String searchTexts[] = StringUtils.split(sterm.getValue(), " ");
                String arr$[] = searchTexts;
                int len$ = arr$.length;
                for(int i$ = 0; i$ < len$; i$++)
                {
                    String searchText = arr$[i$];
                    queryBuf.append(getQLRegion(sterm.getName(), " OR ", searchText, false));
                }

            }

            emptyQuery = false;
        }
        if(!emptyQuery)
        {
            queryBuf.append(")");
        }
        return queryBuf.toString();
    }

}
