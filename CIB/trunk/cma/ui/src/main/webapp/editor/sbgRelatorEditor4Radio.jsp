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
		var externalUrl = "LINK-TO-URL";
		var channelLink = "LINK-TO-CHANNEL";
		var itemLink = "LINK-TO-CONTENT";
		var linkTo = "LINK-TO";

		var externalUrlWidget = editor.getWidget(prefix+externalUrl);
		var channelLinkWidget = editor.getWidget(prefix+channelLink);
		var itemLinkWidget = editor.getWidget(prefix+itemLink);
		var linkToWidget = editor.getWidget(prefix+linkTo);

		if(!externalUrlWidget || !channelLinkWidget || !itemLinkWidget || !linkToWidget){
			/**
			* Widget Objects are null.Possible reasons are  
			*	1. Configured CTD may not require the custom behavior.
			*	  This means it doesn't have above mentioned attributes.
			*	2. Attribute XML names might be different.
			*	   Please check the Attribute XML Names for the above 4 attributes. The names should be same.
			*/
			return;
		}

		var extUrlRadio = vExt.get(linkToWidget.fieldId + "-0");
		var channelRadio = vExt.get(linkToWidget.fieldId + "-1");
		var ciOrFileRadio = vExt.get(linkToWidget.fieldId + "-2");

		if(!extUrlRadio || !channelRadio || !ciOrFileRadio){
			//Please check the Radio CCE widget configuration for 'LINK-TO' attribute. It should have 3 options (externalURL/channel/contentInstance)
			return;
		}

		var doAction = function(checkedBoxValue){
			if(checkedBoxValue === "externalURL"){
				sbg.common.utils.hideWidgets([channelLinkWidget, itemLinkWidget]);
				sbg.common.utils.showWidgets(externalUrlWidget);
			}else if(checkedBoxValue === "channel"){
				sbg.common.utils.hideWidgets([externalUrlWidget, itemLinkWidget]);
				sbg.common.utils.showWidgets(channelLinkWidget);
			}else if(checkedBoxValue === "contentInstance"){
				sbg.common.utils.hideWidgets(externalUrlWidget);
				sbg.common.utils.showWidgets([channelLinkWidget, itemLinkWidget]);
			}
			return;
		}

		if(extUrlRadio.dom.checked === true){
			doAction(extUrlRadio.dom.value);
		}else if(channelRadio.dom.checked === true){
			doAction(channelRadio.dom.value);
		}else if(ciOrFileRadio.dom.checked === true){
			doAction(ciOrFileRadio.dom.value);
		} else {
			sbg.common.utils.hideWidgets([externalUrlWidget,channelLinkWidget,itemLinkWidget]);
		}

		var eventName = 'change';
		/**
			* On IE Browser, There is an issue with on 'change' event listener.
			* So Listerner is register on 'Click' event.
		*/
		if (vExt.isIE) {
			eventName = 'click';
		}
		extUrlRadio.addListener(eventName, function(thisBox, checkedBox) {
			doAction(checkedBox.value);
		});

		channelRadio.addListener(eventName, function(thisBox, checkedBox) {
			doAction(checkedBox.value);
		});

		ciOrFileRadio.addListener(eventName, function(thisBox, checkedBox) {
			doAction(checkedBox.value);
		});

		return;
	});
</script>
<!-- GS Customization - End -->