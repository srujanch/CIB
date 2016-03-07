   
<%
  MenuItemNode node = MenuItemUtils.getSelectedMenuItemNode(portalContext);
  String templateType = node.getProperty("templateType");
  String contentPanelClass = "";
  boolean isGeneralPageWithTabs = false;
  boolean isContetPanel = false;
  List pageDividers = pageBean.getPageDividers();
  int numDividers = pageDividers.size();
  Iterator pageDividerIterator = pageDividers.iterator();
  while (pageDividerIterator.hasNext())
  {
	PageDividerBean pageDivider = (PageDividerBean)pageDividerIterator.next();

	List dividerPanels = pageDivider.getPanels();
	int numPanels = dividerPanels.size();
	Iterator dividerPanelIterator = dividerPanels.iterator();
	while (dividerPanelIterator.hasNext())
	{
		PagePanelBean dividerPanel = (PagePanelBean)dividerPanelIterator.next();
		if ("homePage".equals(templateType)|| "generalPageWithTabs".equals(templateType) || "fxtContactUs".equals(templateType) || "fxtVideoGallery".equals(templateType)) {
			String panelTitle = dividerPanel.getPanelTitle();
			isContetPanel = "contentPanel".equalsIgnoreCase(panelTitle);
			contentPanelClass= node.getProperty("contentPanelClass");
			if("generalPageWithTabs".equals(templateType))	
				isGeneralPageWithTabs = true;
		}
		if(isContetPanel) {
%>
			<div id="content" class="<%=contentPanelClass%>">
<%
			if ("fxtContactUs".equals(templateType)) {
%>
				<div id="contactUs" class="tabBody">
<%
			}
		}
%>   
			<%@ include file="displaypage_static_common.inc" %>
<%
		if(isContetPanel) {
			if ("fxtContactUs".equals(templateType)) {
%>
				</div>
<%
			}
%>
				<br class="clearBoth" />
			</div>
<%
		}
	}  // end panel loop
	
  } // end divider loop
%>

