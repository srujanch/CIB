<%@ page import="com.vignette.ext.templating.portlets.RegionBean" %>
<%@ taglib uri="vgn-tags" prefix="vgn-portal" %>
<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>
<vgn-portal:defineObjects />

<!-- SBG CIB TopNavigation  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG CIB Top Navigation");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtContentSelectComponent,VgnExtSearchToolbarComponent,VgnExtChannelNavComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG CIB TopNavigation  Region End -->

<div id="blankBar">&nbsp;</div>

<!-- SBG CIB MainNavigation  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG CIB Main Navigation");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtChannelNavComponent,VgnExtSearchToolbarComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG CIB MainNavigation  Region End -->

<!-- SBG CIB Breadcrumb  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG CIB Breadcrumb");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtChannelNavComponent,VgnExtContentSelectComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG CIB Breadcrumb  Region End -->