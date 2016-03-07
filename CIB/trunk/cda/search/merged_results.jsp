<%--
/*######################################################################################
Copyright 1999-2007 Vignette Corporation.  All rights reserved.  This software
is an unpublished work and trade secret of Vignette, and distributed only
under restriction.  This software (or any part of it) may not be used,
modified, reproduced, stored on a retrieval system, distributed, or
transmitted without the express written consent of Vignette.  Violation of
the provisions contained herein may result in severe civil and criminal
penalties, and any violators will be prosecuted to the maximum extent
possible under the law.  Further, by using this software you acknowledge and
agree that if this software is modified by anyone such as you, a third party
or Vignette, then Vignette will have no obligation to provide support or
maintenance for this software.
#####################################################################################*/ 
--%>

<!-- start of merged results -->

<%@ page import="com.epicentric.portalbeans.beans.contentsearchbean.*,
                 com.epicentric.contentmanagement.ContentUtils,
                 com.vignette.portal.util.StringUtils,
                 com.vignette.portal.util.internal.WebUtils,
                 com.epicentric.common.Icons,
                 com.epicentric.common.website.HtmlUtils,
                 com.epicentric.portalbeans.beans.contentsearchbean.model.SavedSearch,
                 com.epicentric.common.website.I18nUtils,
                 com.epicentric.common.website.Localizer,
                 com.epicentric.portalbeans.beans.contentsearchbean.model.UserSavedSearch,
                 com.epicentric.portalbeans.beans.contentsearchbean.model.ModuleSavedSearch,
                 com.epicentric.portalbeans.beans.contentsearchbean.view.AggregatedResultsView,
                 com.vignette.portal.search.internal.connectors.camconnector.CAMSearchConnector,
                 com.vignette.portal.search.*,
                 java.util.*,
                 java.text.NumberFormat"

contentType="text/html; charset=UTF-8" %>



<%@taglib uri="epi-tags" prefix="epi_html" %>
<%@taglib uri="module-tags" prefix="mod" %>
<mod:view class="com.epicentric.portalbeans.beans.contentsearchbean.view.MergedResultsView">
<%
	int maxTitleLength = 75;
	int maxSummaryLength = 200;

	ContentSearchBean bean = (ContentSearchBean) view.getBean();
	String imagePath = HtmlUtils.getPath(HtmlUtils.END_USER_IMAGE_PATH);
	// image related variables
	String moduleImgPath =
	        com.epicentric.components.PortalServicesComponent.getInstance()._getPortalHttpRoot() +
	        view.getJSPDirectory() +
	        "/images";
	boolean isNewSearch = view.isNew;
	SavedSearch search = view.search;
    String searchTitle = view.search.getTitle();
	if (StringUtils.isEmpty(searchTitle)){
		searchTitle = I18nUtils.getValue(bean.getBeanTypeID(), "results.view_title", "Search Results", view.getPortalPageContext().getRequest());
	}else{
		searchTitle = WebUtils.charsToXMLEntities(searchTitle);
	}
	String statusMessage = (String)view.getSessionValue(FormFields.STATUS_MESSAGE);
	String errorMessage = (String)view.getSessionValue(FormFields.GENERAL_ERROR);
	String warningMessage = null;
	view.removeSessionValue(FormFields.GENERAL_ERROR);
	boolean showStatusMessage = !StringUtils.isEmpty(statusMessage);
	boolean showError = !StringUtils.isEmpty(errorMessage);
	boolean preview = view.isPreview();

	List mergedList = view.getMergedList();

	NumberFormat relevanceFormat = NumberFormat.getInstance();
	relevanceFormat.setMaximumFractionDigits(1);
	NumberFormat numberOfResultFormat = NumberFormat.getInstance();
	numberOfResultFormat.setGroupingUsed(true);

	String multipleCriteriaTerm = "";
	Localizer localizer = I18nUtils.getLocalizer(view.getPortalPageContext().getSession(), view.getPortalPageContext().getRequest());

	SearchResultsSet srs = view.getSearchResultsSet();
	boolean hasError = false;
	boolean allError = true;
	if (srs != null) {
		for (Iterator iter = srs.getConnectorIds().iterator(); iter.hasNext(); ) {
			String connectorId = iter.next().toString();
			SearchResults sr = srs.getResult(connectorId);
			boolean isError = sr.getStatusCode() != null && sr.getStatusCode() != SearchResultStatusCode.SEARCH_COMPLETED;
			hasError |= isError;
			allError &= isError;
		}
		if (allError) hasError = false;
		if (hasError) {
			warningMessage = I18nUtils.getValue(bean.getBeanTypeID(),
					"results.one_or_more_connector_failure_message",
					"One or more search connectors may not be available. Contact the site administrator for more information.", view.getPortalPageContext().getRequest());
		}
	}
	
	String searchTermAdvanced = "";
	String searchTermDisplay = "";
	if (view.canSeeCriteria() && search.hasAdvancedOptions()) {
		for (ListIterator iter = search.getSearchTermList().listIterator(); iter.hasNext(); ){
			SearchTerm term = (SearchTerm) iter.next();
			if (term instanceof SingleValueSearchTerm) {
				SingleValueSearchTerm sterm = (SingleValueSearchTerm) term;
				if (!StringUtils.isEmpty(sterm.getValue())){
					searchTermDisplay = sterm.getOperator();;
					searchTermAdvanced = sterm.getValue();
				}
			}
		}
	}
%>

<%

    int approximateNumberOfResults = view.getApproximateNumberOfResults();
	if (search != null && approximateNumberOfResults > 0 && mergedList != null && !mergedList.isEmpty()) {
%>

		<div id="pageHeader" class="headerImage col_5">
				<form name="advSearch" id="advSearch" class="advSearchForm" method="post" action="<%= view.getSearchFormURL(true) %>">
					 <fieldset class="searchForm closedState">
						<legend><a href="#" class="advSearchExpander">Advanced search</a></legend>
						<a href="#" class="arrowDn advSearchExpander">&nbsp;</a>
						<br class="clearBoth" />
						<p class="padB10">Search
						<select class="advSiteFilter" name="advSiteFilter">
							<option value="currentSite">this site</option>
							<option value="allSites">all sites</option>
						</select> for pages containing  
						<select class="phrase" name="cri_restriction0">
							<option value="Could contain" <%if(searchTermDisplay.equals("Could contain")){%> selected="true" <%}%> >any of these words</option>
							<option value="Contains" <%if(searchTermDisplay.equals("Contains")){%> selected="true" <%}%>>all of these words</option>
							<option value="Contains phrase" <%if(searchTermDisplay.equals("Contains phrase")){%> selected="true" <%}%>>this exact phrase</option>
						</select>
						<input type="text" class="searchBar"  name="cri_restriction_text0" value="<%=search.hasAdvancedOptions() ? WebUtils.charsToXMLEntities(searchTermAdvanced) : WebUtils.charsToXMLEntities(search.getSearchTerms())%>"/>
						</p>
						<div class="col_50Split left">
							<p class="jobs">Looking for jobs? Visit our <a href="/cib/careers">career section</a> for available positions</p>
						</div>
						<div class="col_50Split right">
							<p class="textRight"><input type="submit" name="search" value="Search" /></p>
						</div>
					</fieldset>
					
					
					
					<input type="hidden" name="age0" value="3600000" />
					<input type="hidden" name="collection_scope" value="use_custom_collections" />
					
					 <input type="hidden" name="cri_field0" value="BODY" />
					
					<!-- <input type="hidden" name="cri_restriction0" value="Contains phrase" /> 
					<input type="hidden" name="cri_restriction_text0" value="test" /> -->
					
					
					<input type="hidden" name="day_constraint0" value="use_from_to_date" />
					<input type="hidden" name="search_advanced" value="y" />
					<input type="hidden" name="from_date0" value="" />
					<input type="hidden" name="hidden_key" value="" />
					<input type="hidden" name="origin_page" value="" />
					<input type="hidden" name="to_date0" value="" />
					
					<!-- <input type="submit" name="search" value="Search" /> -->
					
				</form>
			</div>
	<%
	int pageSize = view.getResultsPerPage();
	if(mergedList != null && !mergedList.isEmpty()) {
	%>		
		<div id="content" class="col_5">
			<h1>Showing results for <%=searchTermDisplay%>  "<%=search.hasAdvancedOptions() ? WebUtils.charsToXMLEntities(searchTermAdvanced) : WebUtils.charsToXMLEntities(search.getSearchTerms())%>"</h1>
			
			
			<div class="paginationMenu">
					<ul class="paginationNo">
					<%
						if (view.showPrevious()) {
					%>
						<li class="previous"> <a href="<%=view.getPreviousPageURL()%>"> &laquo; Previous</a> </li><!-- [.previousOff] when this button is inactive -->
					<%
						}else{
					%>
						<li class="previousOff"><span class="inactive">&laquo; Previous</span></li><!-- [.previousOff] when this button is inactive -->
					<%
						}
					%>
					
					<%
						if (view.showNext()) {
					%>
						<li class="next"><a href="<%=view.getNextPageURL()%>" rel="02">Next &raquo;</a></li>
					<%
						}else{
					%>
						<li class="nextOff"><span class="inactive">Next &raquo;</span></li><!-- [.previousOff] when this button is inactive -->
					<%
						}
					%>	
					</ul>
				</div>
				<br class="clearBoth" />
			
			<ul id="searchResults">
				<%
						for (int i=0; i < mergedList.size() && i < pageSize; i++) {
							SearchResult result = (SearchResult) mergedList.get(i);
							String connectorId = result.getConnectorId();
							SearchConnectorConfig connectorConfig = null;
							try {
								connectorConfig = SearchConnectorConfigSet.getInstance().getConfig(connectorId);
							} catch (SearchException ex) {
							}
							if (connectorConfig == null) continue;
							String connectorName = connectorConfig.getName();

							if (CAMSearchConnector.CONNECTOR_ID.equals(connectorId)) {
								String engineId = (String) result.getProperties().get(CAMSearchConnector.PROPERTY_ENGINE_ID);
								if (engineId != null && !".".equals(engineId)) {
									String engineName = (String) result.getProperties().get(CAMSearchConnector.PROPERTY_ENGINE_NAME);
									connectorName = StringUtils.isEmpty(engineName) ? engineId : engineName;
								}
							}

							String resultURL = "";
							String displayURL = "";

							if (result!=null && result.getSourceURL() != null) {
								resultURL = result.getSourceURL();
								displayURL = Helper.insertSoftBreak(resultURL);
								String mimeType = null;
								if (mimeType == null) {
									mimeType = ContentUtils.getMimeType(resultURL);
								}
					%>
				
				<li>
					<h2><a href="<%=resultURL%>" target="<%=bean.isDefaultOpenNewWindow()?"_blank":"_self"%>" >
						<%
							String title = result.getTitle() == null ?null: result.getTitle().trim();
							if (StringUtils.isEmpty(title)) {
								title = ContentUtils.getNameFromPath(resultURL);
								if (title == null) {
									title = I18nUtils.getValue(bean.getBeanTypeID(), "results.title_not_available_message", "Title not available", view.getPortalPageContext().getRequest());
								}
							}
							title = WebUtils.charsToXMLEntities(title);
							if (title.length() > maxTitleLength) {
								%><%=title.substring(0,maxTitleLength-3) + "..."%><%
											} else {
								%><%=title%><%
											} %>
					</a></h2>
					<h3 class="searchLink"><a href=""><%=resultURL%></a></h3>
					<p>
						 <%
							String summary = result.getDescription();
							if (StringUtils.isEmpty(summary)|| summary.trim().length() == 0) {
						%>
							<mod:i18nValue alias="type" key="results.no_summary_label" defaultValue="(no description)" />
						<%
							} else {
						%>
							<%= WebUtils.charsToXMLEntities(summary) %>
						<%			
							}
						%>
					 </p>
				</li>
				<%
						}
					} // Printed all results. End For loop
				%>
			</ul>
			<br class="clearBoth" />
			<div class="paginationMenu">
					<ul class="pagination">
					<%
						if (view.showPrevious()) {
					%>
						<li class="previous"> <a href="<%=view.getPreviousPageURL()%>"> &laquo; Previous</a> </li><!-- [.previousOff] when this button is inactive -->
					<%
						}else{
					%>
						<li class="previousOff"><span class="inactive">&laquo; Previous</span></li><!-- [.previousOff] when this button is inactive -->
					<%
						}
					%>
					
					<%
						if (view.showNext()) {
					%>
						<li class="next"><a href="<%=view.getNextPageURL()%>" rel="02">Next &raquo;</a></li>
					<%
						}else{
					%>
						<li class="nextOff"><span class="inactive">Next &raquo;</span></li><!-- [.previousOff] when this button is inactive -->
					<%
						}
					%>	
					</ul>
				</div>
				<br class="clearBoth" />
			</div>
		</div>

<%
		} else {
%>
    <hr size="1" noshade="noshade" />
    <table border="0" cellpadding="0" cellspacing="6">
        <tr>
            <td valign="top" nowrap="nowrap" class="epi-dim"> <img src="<%=imagePath%>/icons/icon_status_warning_tiny.gif" title="<mod:i18nValue alias="type" key="_global.alert_img" defaultValue="Alert" isHtml="false"/>" width="13" height="12" style="margin: 0px 6px 0px 2px;" align="absmiddle" alt="Warning"  />
            <%
                if(isNewSearch) {
            %>
                <mod:i18nValue alias="type" key="MY_PORTAL_VIEW.no_results" defaultValue="No results"/>
            <%
                } else {
            %>
                <mod:i18nValue alias="type" key="MY_PORTAL_VIEW.no_result_available" defaultValue="No results or the search engine is unavailable."/>
            <%
                }
            %>
           </td>
        </tr>
    </table>
<%
        }

 } else if (allError) {

%>
<table border="0" cellpadding="0" cellspacing="0" style="margin: 0.7em 0em 0em 0.7em;">
<tr>
	<td valign="top" style="padding-right:.5em;"> <img src="<%=imagePath%>/icons/icon_status_warning_sm.gif" title="<mod:i18nValue alias="type" key="_global.alert_img" defaultValue="Alert" isHtml="false"/>" border="0" width="20" height="18" /></td>
	<td width="100%"> <div style="font-weight:bold; margin:3px 0px 5px"><mod:i18nValue alias="type" key="results.no_results_label" defaultValue="No Results"/> </div>
		<p>
			<mod:i18nValue alias="type" key="results.no_results_connector_failure_message" defaultValue="Sorry, connector failure for your search. Please try again."/>
		</p>
		<p>
			<mod:i18nValue alias="type" key="MY_PORTAL_VIEW.no_results_warning_available" defaultValue="If you continue to reach this error, the search engine may be unavailable. Contact the site administrator for more information."/>
		</p>
	</td>
</tr>
</table>
<%

 } else {
        // There is no result available
%>

<div id="pageHeader" class="headerImage col_5">
				<form name="advSearch" id="advSearch" class="advSearchForm" method="post" action="<%= view.getSearchFormURL(true) %>">
					 <fieldset class="searchForm closedState">
						<legend><a href="#" class="advSearchExpander">Advanced search</a></legend>
						<a href="#" class="arrowDn advSearchExpander">&nbsp;</a>
						<br class="clearBoth" />
						<p class="padB10">Search
						<select class="advSiteFilter" name="advSiteFilter">
							<option value="currentSite">this site</option>
							<option value="allSites">all sites</option>
						</select> for pages containing  
						<select class="phrase" name="cri_restriction0">
							<option value="Could contain" <%if(searchTermDisplay.equals("Could contain")){%> selected="true" <%}%> >any of these words</option>
							<option value="Contains" <%if(searchTermDisplay.equals("Contains")){%> selected="true" <%}%>>all of these words</option>
							<option value="Contains phrase" <%if(searchTermDisplay.equals("Contains phrase")){%> selected="true" <%}%>>this exact phrase</option>
						</select>
						<input type="text" class="searchBar"  name="cri_restriction_text0" value="<%=search.hasAdvancedOptions() ? WebUtils.charsToXMLEntities(searchTermAdvanced) : WebUtils.charsToXMLEntities(search.getSearchTerms())%>"/>
						</p>
						<div class="col_50Split left">
							<p class="jobs">Looking for jobs? Visit our <a href="/cib/careers">career section</a> for available positions</p>
						</div>
						<div class="col_50Split right">
							<p class="textRight"><input type="submit" name="search" value="Search" /></p>
						</div>
					</fieldset>
					
					
					
					<input type="hidden" name="age0" value="3600000" />
					<input type="hidden" name="collection_scope" value="use_custom_collections" />
					
					 <input type="hidden" name="cri_field0" value="BODY" />
					
					<!-- <input type="hidden" name="cri_restriction0" value="Contains phrase" /> 
					<input type="hidden" name="cri_restriction_text0" value="test" /> -->
					
					
					<input type="hidden" name="day_constraint0" value="use_from_to_date" />
					<input type="hidden" name="search_advanced" value="y" />
					<input type="hidden" name="from_date0" value="" />
					<input type="hidden" name="hidden_key" value="" />
					<input type="hidden" name="origin_page" value="" />
					<input type="hidden" name="to_date0" value="" />
					
					<!-- <input type="submit" name="search" value="Search" /> -->
					
				</form>
			</div>

			
			<div>
				<p>
					<mod:i18nValue alias="type" key="MY_PORTAL_VIEW.sorry_no_results_available" defaultValue="Sorry, no results were found for your search. Please revise your criteria and try again."/>
				</p>
			</div>



<%
 }

%>





<script type="text/javascript">
	function submitAdvSearch(formElement){
		var siteFilter = $('select[name=advSiteFilter]').val();
		if (siteFilter == 'currentSite'){
			<%
				List defaultEnabledCollList = bean.getCollectionDefaultList();
				if(!defaultEnabledCollList.isEmpty()) {
					Iterator j = defaultEnabledCollList.iterator();
					while(j.hasNext()){
						Key nextKey = (Key) j.next();
						%>
						$(formElement).append('<input type="hidden" name="<%=nextKey%>" value="selected" />');
						<%
					}
				}
				%>
		}else{
			<%
				List enabledCollList = bean.getCollectionEnabledList();
				if(!enabledCollList.isEmpty()) {
					Iterator j = enabledCollList.iterator();
					while(j.hasNext()){
						Key nextKey = (Key) j.next();
						%>
						$(formElement).append('<input type="hidden" name="<%=nextKey%>" value="selected" />');
						<%
					}
				}
				%>
		}		
	}
	
	function addPageBC(formElement){
		$(formElement).append('<li class="last" style="background: none repeat scroll 0% 0% transparent;">Search Results</li>');
	}
	
</script>

<script type="text/javascript">
	$(document).ready(function() { // bodyguard function
		$('#prodSrchOpt,#sectorSrchOpt,#dealSrchOpt,#accolSrchOpt,#pressRelSrchOpt,#searchRefine').hide();
		addPageBC($('#crumbList'));
		$('.advSearchForm').on('change', 'select.advSiteFilter',function(e){
			var siteFilter = $(this).val();
			if (siteFilter == 'currentSite'){
				$('select[name=typeOfSearch]').append('<option value="Sectors">sectors</option>');
				$('select[name=typeOfSearch]').append('<option value="Deals">deals</option>');
				$('select[name=typeOfSearch]').val('Everything').append('<option value="Accolades">accolades</option>');
			}else{
				$('select[name=typeOfSearch]').val('Everything').find('option[value="Sectors"], option[value="Accolades"], option[value="Deals"]').remove();
			}
		})
		$('.advSearchForm').on('submit',function(e){
			submitAdvSearch($('#advSearch'));
		})
		$('.advSearchForm').on('change', 'select[name=typeOfSearch]',function(e){
			var siteFilter = $(this).val();
			$('#searchRefine').show();
			$('#prodSrchOpt,#sectorSrchOpt,#dealSrchOpt,#accolSrchOpt,#pressRelSrchOpt').hide();
			switch(siteFilter){
			case 'ProductsX':
				$('#prodSrchOpt').show();
			break;
			case 'SectorsX':
				$('#sectorSrchOpt').show();
			break;
			case 'DealsX':
				$('#dealSrchOpt').show();
			break;
			case 'AccoladesX':
				$('#accolSrchOpt').show();
			break;
			case 'Press releasesX':
				$('#pressRelSrchOpt').show();
			break;
			default:
				$('#searchRefine').hide();
			}
		});
	});// bodyguard function
</script>
<style type="text/css">
	ul.paginationNo {padding-bottom: 0 !important;}
	ul.paginationNo li{display:-moz-inline-stack;display:inline-block;zoom:1;*display:inline;}
	.glossary a.keyIndex,
	ul.paginationNo li a{border:solid 1px #DDDDDD; margin-right:2px;}
	ul.paginationNo .previousOff,
	ul.paginationNo .nextOff {color:#666666;font-weight:bold;padding:4px 6px;}
	ul.paginationNo .next a,
	ul.paginationNo .previous a {font-weight:bold;border:solid 1px #FFFFFF;} 
	ul.paginationNo li.active, ul.pagination li.active a:link, ul.pagination li.active a:visited, ul.pagination li.active a:hover{font-weight:bold;padding:4px 6px;border:none;color:#787878;cursor: default;}
	.glossary a.keyIndex,
	ul.paginationNo a:link,
	ul.paginationNo a:visited {color:#0063e3;display:block;padding:3px 6px;text-decoration:none;}
	ul.paginationNo a:hover{border:solid 1px #F58320;color:#F58320;}
</style>
</mod:view>