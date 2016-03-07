<%
	List portletPlaceholders = dividerPanel.getPortletPlaceholders();
	int numPortlets = portletPlaceholders.size();
	Iterator portletPlaceholderIterator = portletPlaceholders.iterator();
	boolean isFirstItemInPanel = true;
	while (portletPlaceholderIterator.hasNext())
	{
		PortletPlaceholderBean portletPlaceholder = (PortletPlaceholderBean)portletPlaceholderIterator.next();
		PortletWindowBean portletWindow = (PortletWindowBean)portletWindowBeans.get(portletPlaceholder.getPlaceholderUID());

		request.setAttribute("portletWindow", portletWindow);

		if(isGeneralPageWithTabs && isContetPanel && isFirstItemInPanel){
%>
			<div class="tabBody" id="overviewPage" >
<%
		}
		if(isGeneralPageWithTabs && isContetPanel && !portletPlaceholderIterator.hasNext()){
%>
			</div>
<%
		}
%>
		<%@include file="display_portlet.inc" %>
	
<%
		isFirstItemInPanel = false;
	}
%>
