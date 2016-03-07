<%@ page contentType="text/html"%>

<%@ taglib uri="vgn-tags" prefix="vgn-portal"%>
<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>

<vgn-portal:defineObjects/>
<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/_inMgmtStage.jsp" />
<%
	String mgmtStage = (String) request.getAttribute("com.vignette.ext.mgmtstage");
	boolean isInMgmtStage = (mgmtStage != null) && ("true".equals(mgmtStage));
	String noIce = (String)request.getParameter("vgnextnoice");
    if ((isInMgmtStage) && (!"1".equals(noIce))) {
%>

				<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/styleSheet.jsp" />

<%
    }
%>
	<vgn-portal:includePageContent />
<%
    if ((isInMgmtStage) && (!"1".equals(noIce))) {
%>
				<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/in-context-edit.jsp" />
		
<%
    }
%>
