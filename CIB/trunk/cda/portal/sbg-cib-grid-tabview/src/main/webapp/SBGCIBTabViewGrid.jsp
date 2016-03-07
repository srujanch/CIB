<%@ page contentType="text/html"%>

<%@ taglib uri="vgn-tags" prefix="vgn-portal"%>
<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<vgn-portal:defineObjects/>
<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/_inMgmtStage.jsp" />
<%-- Get the DPM Site properties using a jsp in DPM context--%>
<page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/include/getSiteProperties.jsp"/>
<%
	String mgmtStage = (String) request.getAttribute("com.vignette.ext.mgmtstage");
	boolean isInMgmtStage = (mgmtStage != null) && ("true".equals(mgmtStage));
	String noIce = (String)request.getParameter("vgnextnoice");
    if ((isInMgmtStage) && (!"1".equals(noIce))) {
%>
		<html>
			<head>
				<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/styleSheet.jsp" />
			</head>
			<body>
<%
    }
%>
    <c:choose>
        <c:when test="${not empty param.tabResults}">
            <div id="dealsSearch${param.tabResults}${param.vgnNextStartIndex}" class="${empty param.vgnNextStartIndex?'tabBody':''}">
        </c:when>
        <c:otherwise>
            <div id="<%=request.getAttribute("sbg.currentTabFriendlyName")%>" class="tabBody">
        </c:otherwise>
    </c:choose>

        <vgn-portal:includePageContent />

    </div>

<%
    if ((isInMgmtStage) && (!"1".equals(noIce))) {
%>
				<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/in-context-edit.jsp" />
			</body>
		</html>
<%
    }
%>
