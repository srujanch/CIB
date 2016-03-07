<%@page import="com.vignette.as.client.javabean.Site"%>
<%@page import="com.vignette.as.client.javabean.Channel"%>
<%@page import="com.vignette.ext.templating.util.PageUtil"%>
<%@page import="com.vignette.ext.templating.util.RequestContext"%>
<%
	// retrive current Site OTM attributes and stores them  in request.	
	// Prepare the request context
	RequestContext rc = PageUtil.getCurrentRequestContext(pageContext);
	// Get Current Site from the request context
	Site currentSite = rc.getCurrentSite();
	if(currentSite!=null) {
		String stylingClassName = (String) currentSite.getAttributeValue("STYLING-CLASS-NAME");
		request.setAttribute("sbg.site.stylingClassname",stylingClassName);
	}
	// Get Current Channel from the request context
	Channel currentChannel = rc.getRequestedChannel();
	if(currentChannel!=null) {
		String tabFriendlyName = (String) currentChannel.getAttributeValue("FRIENDLY-NAME");
		if(tabFriendlyName == null) {
			tabFriendlyName = currentChannel.getName();
		}
		tabFriendlyName = tabFriendlyName.replaceAll(" ","");
		request.setAttribute("sbg.currentTabFriendlyName",tabFriendlyName);
	}
%>