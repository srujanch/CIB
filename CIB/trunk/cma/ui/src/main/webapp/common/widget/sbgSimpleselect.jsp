<%--
/*######################################################################################
Copyright 2009 Vignette Corporation.  All rights reserved.  This software
is an unpublished work and trade secret of Vignette, and distributed only
under restriction.  This software (or any part of it) may not be used,
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
<%@ include file="header.jspf"%>
<%@ page import="com.vignette.ui.vcm.widget.renderer.SimpleSelectRenderer"%>
<%@ page import="com.vignette.ui.vcm.widget.VUISimpleSelectItem"%>
<%@ page import="java.util.List"%>
<%
	SimpleSelectRenderer ssr = (SimpleSelectRenderer) wr;
	boolean isMulti = ssr.isMultiSelect();
	String widgetType = "simpleselect";
	String fieldId = widgetId + "-" + widgetType;
	String readOnlyCls = readOnly ? " vui-widget-readonly" : "";
	String readOnlyTxt = readOnly ? "disabled" : "";
%>
<script type="text/javascript">
	vui.ready(function(){
		var fieldId='<%=fieldId%>';
		var isMulti=<%=isMulti%>;
		<%-- register id --%>
		vui.vcm.ui.editor.widget.simpleselect.setMultiById(fieldId, isMulti);
	});
</script>
<%-- custom body.jspf start --%>

<%-- wrapper div --%>
<div id="<%= widgetId %>-wrapper" <%=hiddenDisplayStyle%> class="vui-widget-wrapper">
	<%-- rounded corners - optional --%>
	<div class="vui-widget-tl"><div class="vui-widget-tr"><div class="vui-widget-tc"></div></div></div>
	<div class="vui-widget-ml"><div class="vui-widget-mr"><div class="vui-widget-mc">
		<%-- start widget content --%>
		<div id="<%= widgetId %>-label" class="vui-widget-label">
			<label id="<%= fieldId %>-label" for="<%= fieldId %>" class="vui-widget-label-text"
<% if (tooltip.length() > 0) { %> title="<%= tooltip %>" <% } %>
			><%= label %></label>
<% if (required.length() > 0) { %>
			<span class="vui-widget-required-flag"><%= required %></span>
<% } %>
<% if (tooltip.length() > 0) { %>
			<span id="<%= widgetId %>-tooltip" class="vui-tooltip"></span>
<% } %>
		</div>
		<div id="<%= widgetId %>-help" class="vui-widget-help">
			<%-- rounded corners - optional --%>
			<div class="vui-widget-tl"><div class="vui-widget-tr"><div class="vui-widget-tc"></div></div></div>
			<div class="vui-widget-ml"><div class="vui-widget-mr"><div class="vui-widget-mc">
				<%-- help message - optional --%>
				<div id="<%= widgetId %>-help-text" class="vui-widget-help-text">
					<p id="<%= fieldId %>-help-text"><%= helpText %></p>
				</div>
			</div></div></div>
			<div class="vui-widget-bl"><div class="vui-widget-br"><div class="vui-widget-bc"></div></div></div>
		</div>
		<div id="<%= widgetId %>-data" class="vui-widget-data">
			<%-- rounded corners - optional --%>
			<div class="vui-widget-tl"><div class="vui-widget-tr"><div class="vui-widget-tc"></div></div></div>
			<div class="vui-widget-ml"><div class="vui-widget-mr"><div class="vui-widget-mc">
				<%-- widget data and controls --%>
				<div id="<%= widgetId %>">
				<%
					String widgetValue = wr.getValue();
					if (widgetValue != null) {
						widgetValue = StringUtil.htmlEscape(widgetValue);
					}

					if (isMulti) {
						// checkbox: assumes List has one item, since CTM models one checkbox per widget
						VUISimpleSelectItem item = ssr.getItems().get(0);
						String itemValue = StringUtil.htmlEscape(item.getValue());
						boolean selected = ((widgetValue != null) && widgetValue.equals(itemValue)) ||
								(isNew && item.isSelectedByDefault() && !isClone);
				%>
				<input type="checkbox" class="vui-widget-input-simpleselect<%= readOnlyCls%>" id="<%= fieldId %>" name="<%= itemValue %>"
					<% if (selected) { %> checked="checked" <% } %><%= readOnlyTxt %>><label class="vui-form-label-notbold" id="<%= fieldId %>-checkbox-label"for="<%= fieldId %>"><%= StringUtil.htmlEscape(item.getLabel()) %></label><br/>
				<% } else { 
				%>
				<div id="<%= fieldId%>">
				<%
					// radio buttons
					List<VUISimpleSelectItem> items = ssr.getItems();
					for (int i = 0, len = items.size(); i < len; ++i) {
						VUISimpleSelectItem item = items.get(i);
						String itemValue = StringUtil.htmlEscape(item.getValue());
						boolean selected = ((widgetValue != null) && widgetValue.equals(itemValue)) ||
								(isNew && item.isSelectedByDefault() && !isClone);
				%>
				<input type="radio" class="vui-widget-input-simpleselect<%= readOnlyCls %>" id="<%= fieldId %>-<%= i %>" name="<%=fieldId%>" value="<%= itemValue %>"
					<% if (selected) { %> checked="checked" <% } %><%= readOnlyTxt %>> <label class="vui-form-label-notbold" id="<%= fieldId %>-<%= i %>-radio-label" for="<%= fieldId %>-<%= i %>"><%= StringUtil.htmlEscape(item.getLabel()) %></label> &nbsp;
				<%
						} 
				%>
				</div>
				<%
					}
                %>
				</div>
			</div></div></div>
			<div class="vui-widget-bl"><div class="vui-widget-br"><div class="vui-widget-bc"></div></div></div>
		</div>
		<div id="<%= widgetId %>-validation" class="vui-widget-validation">
			<%-- rounded corners - optional --%>
			<div class="vui-widget-tl"><div class="vui-widget-tr"><div class="vui-widget-tc"></div></div></div>
			<div class="vui-widget-ml"><div class="vui-widget-mr"><div class="vui-widget-mc">
				<%-- widget validation message - optional --%>
				<div id="<%= widgetId %>-validation-text" class="vui-widget-validation-text"></div>
			</div></div></div>
			<div class="vui-widget-bl"><div class="vui-widget-br"><div class="vui-widget-bc"></div></div></div>
		</div>
	</div></div></div>
	<div class="vui-widget-bl"><div class="vui-widget-br"><div class="vui-widget-bc"></div></div></div>
</div>
<%-- custom body.jspf end --%>

<%@ include file="footer.jspf" %>

<script type="text/javascript">
vui.ready(function() {
	<%-- set the original value, used by hasChecked() --%>
	var sswidget = vui.ui.editor.findWidget('<%= editorId %>', '<%= vcmId %>');
	if (sswidget) {
		sswidget.origValue = '<%= value %>';
	}
});
</script>
