<%@page import="com.vignette.ui.core.editor.Editor"%>
<%@ page import="com.vignette.ui.util.StringUtil" %>
<%@page import="com.vignette.as.client.common.AsObjectMappingConstants"%>
<%@page import="com.vignette.ui.api.VCMConstant"%>
<%@page import="com.vignette.ui.vcm.util.MapHelper"%>

<%@ taglib prefix="core" uri="http://ui.vignette.com/core" %>

<%
	MapHelper object = MapHelper.get(request, VCMConstant.ITEM_ATTRIBUTE);
	String editorId = object.getString(AsObjectMappingConstants.CONTENT_MANAGEMENT_ID_ATTRIBUTE);
	if (StringUtil.isEmpty(editorId)) {
		editorId = (String)request.getAttribute(Editor.ID_ATTRIBUTE);
	}
%>
<!-- This tags outputs default editor -->
<core:renderRegion id="region-parent"/>

<!-- GS Customization - Begin -->
<script type="text/javascript">
	vExt.onReady(function(){
		var editor = vui.ui.editor.find('<%= editorId %>');
		if(!editor){
			alert('Current Editor Object is Null.Please contact your administrator.');
			return;
		}

		var prefix = '.' + editor.relationAttrs.relationPath + '.'+editor.id+'.';
		//Attribute XML names.
		var tabType			= "TAB-TYPE";
		var tabFriendlyName = "TAB-FRIENDLY-NAME";
		var tabDescription	= "TAB-DESCRIPTION";
		var tabChannelLink	= "TAB-CHANNEL-ID";
		var tabExtUrlLink	= "TAB-EXTERNAL-URL";

		var tabTypeWidget			= editor.getWidget(prefix+tabType);
		var tabFriendlyNameWidget	= editor.getWidget(prefix+tabFriendlyName);
		var tabDescriptionWidget	= editor.getWidget(prefix+tabDescription);
		var tabChannelLinkWidget	= editor.getWidget(prefix+tabChannelLink);
		var tabExtUrlLinkWidget		= editor.getWidget(prefix+tabExtUrlLink);

		if(!tabTypeWidget || !tabFriendlyNameWidget || !tabDescriptionWidget || !tabChannelLinkWidget || !tabExtUrlLinkWidget){
			/**
			* Widget Objects are null.Possible reasons are  
			*	1. Configured CTD may not require the custom behavior.
			*	  This means it doesn't have above mentioned attributes.
			*	2. Attribute XML names might be different.
			*	   Please check the Attribute XML Names for the above 5 attributes. The names should be same.
			*/
			return;
		}

		var tabTypeValue = tabTypeWidget.value;		
		var doAction = function(tabTypeValue){
			if(!tabTypeValue || tabTypeValue=='' || tabTypeValue=="Deals" || tabTypeValue=="Accolades"){
				sbg.common.utils.hideWidgets([tabFriendlyNameWidget, tabDescriptionWidget,tabChannelLinkWidget,tabExtUrlLinkWidget]);
			} else if(tabTypeValue=="external") {
				sbg.common.utils.hideWidgets([tabFriendlyNameWidget, tabDescriptionWidget,tabChannelLinkWidget]);
				sbg.common.utils.showWidgets([tabExtUrlLinkWidget]);
			} else if(tabTypeValue=="More Info") {
				sbg.common.utils.hideWidgets([tabChannelLinkWidget,tabExtUrlLinkWidget]);
				sbg.common.utils.showWidgets([tabFriendlyNameWidget, tabDescriptionWidget]);
			} else {
				sbg.common.utils.hideWidgets([tabFriendlyNameWidget,tabDescriptionWidget,tabExtUrlLinkWidget]);
				sbg.common.utils.showWidgets([tabChannelLinkWidget]);
			}
			return;
		}

		doAction(tabTypeValue);
		var tabTypeDom = vExt.getCmp(tabTypeWidget.fieldId);
		tabTypeDom.on('select', vui.scope(this, function() {
			doAction(tabTypeDom.getValue());
		}));
		return;

	});
</script>
<!-- GS Customization - End -->