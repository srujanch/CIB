<%@ page import="com.vignette.ext.templating.portlets.RegionBean" %>
<%@ taglib uri="vgn-tags" prefix="vgn-portal" %>
<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>
<vgn-portal:defineObjects />

<!-- SBG FXT TopNavigation  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG FXT Top Navigation");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtContentSelectComponent,VgnExtSearchToolbarComponent,VgnExtChannelNavComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG FXT TopNavigation  Region End -->

<div id="blankBar">&nbsp;</div>

<!-- SBG FXT MainNavigation  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG FXT Main Navigation");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtChannelNavComponent,VgnExtSearchToolbarComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG FXT MainNavigation  Region End -->

<!-- SBG FXT Breadcrumb  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG FXT Breadcrumb");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtChannelNavComponent,VgnExtContentSelectComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG FXT Breadcrumb  Region End -->