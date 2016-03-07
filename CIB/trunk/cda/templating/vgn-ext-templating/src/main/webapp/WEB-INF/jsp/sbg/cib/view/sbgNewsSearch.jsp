<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>
<%@ page import="za.co.standardbank.sbg.cda.templating.web.util.DisplayUtil"%>
<%@ page import="za.co.standardbank.sbg.cda.templating.web.util.DPMConstants"%>
<%@ page import="java.util.*"%>
<%@ page import="com.vignette.ext.templating.util.*"%>



<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:if test="${not empty component.channelPath}">
<templating:contentLink var="linkUrl" oid="${component.channelPath.system.id}"/>
</c:if>
<%
    try{
        RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
        String siteName = rc.getCurrentSite().getData().getName();
        List<String> years = DisplayUtil.getYearsForSearch(siteName.toUpperCase()+ DPMConstants.MEDIA_START_YEAR);

        HashMap<String, String> countries = DisplayUtil.getSBCountries();
        List<Map.Entry> countrieAsc = (countries != null)?new ArrayList<Map.Entry>(countries.entrySet()):new ArrayList<Map.Entry>();
        Collections.sort(countrieAsc, DisplayUtil.getHashMapEntrySetComparator());

        pageContext.setAttribute("listYears",years);
        pageContext.setAttribute("listCountries",countrieAsc);

    }catch(Exception ex){
        ex.printStackTrace();
    }

%>

<div id="newsSearch" class="search float portlet">
<form action="${linkUrl}" method="POST">
    <h1>${empty component.title?'Search news':component.title}</h1>
    <p>
		<select class="year" name="Year">
            <option value="">Year - all</option>
             <c:forEach items="${listYears}" var="year">
                 <c:choose>
                    <c:when test="${(not empty param.Year) and (param.Year eq year)}">
                        <option value="${year}" selected="selected">${year}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${year}">${year}</option>
                    </c:otherwise>
                </c:choose>
             </c:forEach>
        </select>
    </p>
    <p>
         <select class="country" name="Country">
             <option value="">Country - all</option>
             <c:forEach items="${listCountries}" var="entry">
                <c:choose>
                    <c:when test="${(not empty param.Country) and (param.Country eq entry.key)}">
                        <option value="${entry.key}" selected="selected">${entry.value}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${entry.key}">${entry.value}</option>
                    </c:otherwise>
                </c:choose>
             </c:forEach>
         </select>
    </p>
    <p><input type="text" style="width:170px;" value="${empty param.Keyword ?'Keyword':param.Keyword}" onfocus="this.value=(this.value==this.defaultValue) ? '' : this.value;return true;" onblur="if(this.value==''){this.value='Keyword'; }" id="Keyword" name="Keyword" /></p>
	<p class="textRight">
		<input type="submit" value="Search news" />
	</p>
</form>
</div>