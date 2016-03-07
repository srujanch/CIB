<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%--
/*######################################################################################
Copyright 2001-2006 Vignette Corporation.  All rights reserved.  This software
is an unpublished work and trade secret of Vignette, and distributed only
under restriction. This software (or any part of it) may not be used,
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

<%@ page import="com.epicentric.common.Installation,
					 com.epicentric.common.website.ParameterConstants,
                     com.epicentric.common.website.EndUserUtils,
                     java.util.*,
                     com.epicentric.template.*,
					 com.vignette.ext.templating.portlets.RegionBean"
%>
<%@ page import="com.epicentric.common.website.MenuItemUtils"%>
<%@ page import="com.epicentric.common.website.MenuItemNode"%>
<%@ taglib uri="vgn-tags" prefix="vgn-portal" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    long t1 = new Date().getTime();
%>
<vgn-portal:defineObjects/>

<%@ taglib uri="/WEB-INF/taglib/vgnExtTemplatingPortal.tld" prefix="page" %>

<page:hide-template-menu-items />

<page:tas-authenticator/>

<!-- Vignette Application Portal <%=Installation.getVersion()%> -->
<%
	MenuItemNode node = MenuItemUtils.getSelectedMenuItemNode(portalContext);
    String pageLayoutStyle = node.getProperty("pageLayoutStyle");
	String templateType = node.getProperty("templateType");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<!-- <meta http-equiv="X-UA-Compatible" content="IE=8"> as per DPM8.x-->
		<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/seo/sbgSEOTags.jsp"/>
		<link rel="shortcut icon" href="/vgn-ext-templating/sbg/cib/favicon.ico" />		
		<vgn-portal:styleBlock />
		<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/styleSheet.jsp"/>
		<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/scripts.jsp"/>
		<page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/theme/sbgThemeDisplay.jsp?render=css"/>
	</head>
	<body>
		<%-- Get the DPM Site properties using a jsp in DPM context--%>
		<page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/include/getSiteProperties.jsp"/>
		<noscript>
			<div class="noScript">
				<div class="left" style="width:120px;">
					<img src="/vgn-ext-templating/sbg/cib/sharedImages/siteElements/icons-logos/excl.png" alt="!" />
				</div>
				<div class="left" style="width:600px;">
					<p class="errorText p-s14"> In order to bring you the best possible user experience, this site uses Javascript. If you are seeing this message, it is likely that the Javascript option in your browser is disabled. For optimal viewing of this site, please ensure that Javascript is enabled for your browser. </p>
					<br />
					<p>To enable Javascript in your browser follow the instructions in this <a href="http://www.google.com/support/bin/answer.py?answer=23852" target="_blank"><strong>link: http://www.google.com/support/bin/answer.py?answer=23852</strong></a></p>
					<br />
					<p>After you have enabled JavaScript on your browser, refresh this page.</p>
				</div>
				<br class="clearBoth" />
			</div>
		</noscript>
		<div id="page" class="<%=request.getAttribute("sbg.site.stylingClassname")%> hpgVer3 scroll"><!-- [headerSBG_CIB == stylingClassname] -->
		<!-- this class name "headerSBG_CIB" is the master controlling class that themes the entire page according to the different stylesheets. 
		This should be a variable on the master grid and mapped to an input element in the VCM that would take three values["country", "business group name", "stylingClassname"](search below for these keynames), outputting them on the grid
		**************IMPORTANT INFO: All HTML must validate according to w3c standards as defined in http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd *************
		A firefox plugin: "HTML Tidy" should be installed on your browser from http://users.skynet.be/mgueury/mozilla/ to assist in correcting all HTML rendered, thus ensure that there is always a green check mark in the tool icon. This tool will provide solutions to fix incorrect HTML if necessary.

		*******************Standard best practice rules and guidelines*******************
		1. All special character entities [&, ,< , >, © , etc.] should always be written as [&amp;, &lt;, &gt;, &copy;, etc.] when is to be presented as content from the VCM.
		2. no white space should reside within a url tag eg. <a href="../standard bank.html"> The naming standard for all files should be <a href="../standardBank.html">, whereby the first word is always lower case, theeafter every adjoining word should start with an upper case.
		3. All file name extension's used should be lower case "pic.jpg" and not "pic.JPG" 
		4. All text elements should be wrapped in an HTML tag, paragraphs should be wrapped in <p></p> tags
		5. All HTML tags must close, even single tags such as <input type="" > tags with a trailing "/" <input type="" /> as well as <img src="" /> tags
		6. All HTML rendered from the VCM should exactly mirror the HTML in this page for it to display correctly. 
		7. <<DO NOT FOLLOW FIREBUG>> when copying HTML, use the actual view source, as some code is dynamically generated and wrapped around elements, by the browser itself to optimize speed and loading of the pages, this keeping the file size down.
		8. "id"'s should only appear once on a page, and "classNames" can be repeated.
		9. Absolutely no inline styles should be used. All CSS should be contained in the head of the page.
		10. All small images, icons, especially mouse overed and repeating backgrounds should be contained as a CSS sprite
		-->
		<div id="topBarFrame">
			<!-- begin header area -->
			<vgn-portal:includeStyle friendlyID="header"/>
			<!-- end header area -->
			
			<!-- begin horizontal nav area -->
			<vgn-portal:includeNavigation friendlyID="navigation-horizontal" />
			<!-- end horizontal nav area -->
		</div>
	<div id="pageContentFrame">
		<div id="pageContent" class="<%=pageLayoutStyle%>">
			<!-- 
			[homePage == home page layout style with elements:
			ticker[id="shareTicker"], promo areas[id="mainPromo"]&[id="homeBottomBanners"], left content[id="leftNav"] and right content[id="rightContent"], Information portlets are within [id="information"], defined within their own Id's, this similar concept would apply to [id="footerContentBar"] and its sub portlets
			
			leftNav1Column == page with left navigation and the righthand side is content the rest of the width 790px
			
			this is a calculated classname that would define the widths and heights of all the elements within the page] 
			-->
			
			<!-- begin page content area -->
			<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/in-context-edit.jsp"/>
			<vgn-portal:includePageContent />
			<!-- end page content area -->
		</div>
		<!-- Do not remove div#mask, because we'll need it to fill the whole screen on overlays, these are just dormant div element placeholders that can be manipulates as needed -->
		<div id="mask"></div>
		<div id="maskPerm"></div>
		<div id="footer" class="clearBoth">
			<!-- begin footer area -->
			<vgn-portal:includeStyle friendlyID="footer" />
			<!-- end footer area -->
			<br class="clearBoth"/>
		</div>
	</div>
</div>
<script type="text/javascript" language="JavaScript" src="<%= portalContext.getPortalHttpRoot() %>jslib/form_state_manager.js"></script>

<%
	if ("homePage".equals(templateType)) {
%>
    <page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/theme/sbgThemeDisplay.jsp?render=js&type=homePage"/>
<%} else if ("fxtVideoGallery".equals(templateType)) { %>
    <page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/theme/sbgThemeDisplay.jsp?render=js&type=fxtVideoGallery"/>
<%} else if ("fxtContactUs".equals(templateType)) { %>
		<page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/theme/sbgThemeDisplay.jsp?render=js&type=fxtContactUs"/>
<%}else{%>
    <page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/theme/sbgThemeDisplay.jsp?render=js"/>
<%}%>     

<page:include-page urlContext="/vgn-ext-templating" pagePath="/WEB-INF/jsp/sbg/common/sitecatalyst/sbgSiteCatalyst.jsp"/>
</body>
</html>

<%--
Need to invoke dependency tracking at the end of the template
--%>
<page:include-page urlContext="/vgn-ext-templating" pagePath="/in_line/trackDependencies.jsp"/>
<%
	long t2 = new Date().getTime();
	out.print("<!--");
	out.print(String.valueOf(t2-t1));
	out.print("-->");
%>