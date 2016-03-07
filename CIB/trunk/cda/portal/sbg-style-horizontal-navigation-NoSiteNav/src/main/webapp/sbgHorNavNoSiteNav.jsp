<%@ page import="com.vignette.ext.templating.portlets.RegionBean" %>
<%@ taglib uri="vgn-tags" prefix="vgn-portal" %>
<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>
<vgn-portal:defineObjects />


<div id="blankBar">&nbsp;</div>

<!-- SBGCIBMainNavigation  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBGCIBMainNavigation");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtChannelNavComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBGCIBMainNavigation  Region End -->

<!-- SBGCIBBreadcrumb  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBGCIBBreadcrumb");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtChannelNavComponent,VgnExtContentSelectComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBGCIBBreadcrumb  Region End -->