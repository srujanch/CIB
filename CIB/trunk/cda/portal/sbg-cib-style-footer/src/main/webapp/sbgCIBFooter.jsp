<%@ page import="com.vignette.ext.templating.portlets.RegionBean" %>
<%@ taglib uri="vgn-tags" prefix="vgn-portal" %>
<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>
<vgn-portal:defineObjects />

<!-- SBG CIB FooterNavBar  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG CIB Footer NavBar");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtContentSelectComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG CIB FooterNavBar  Region End -->

<!-- SBG CIB FooterContentBar  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG CIB Footer ContentBar");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtContentSelectComponent");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG CIB FooterContentBar  Region End -->

<!-- SBG CIB FooterLegalBar  Region Begin -->
<%
	request.setAttribute("com.vignette.ext.templating.portlet.region.name", "SBG CIB Footer LegalBar");
	request.setAttribute("com.vignette.ext.templating.portlet.region.types", "VgnExtMultiSelect");
	request.setAttribute("com.vignette.ext.templating.portlet.region.displayInContextEdit", "true");
	request.setAttribute("com.vignette.ext.templating.portlet.region.scope", RegionBean.SCOPE_TEMPLATE);
%>

<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/contentRegion.jsp"/>
<%request.setAttribute("com.vignette.ext.templating.portlet.region.jspOverrideUri",null);%>
<!-- SBG CIB FooterLegalBar  Region End -->