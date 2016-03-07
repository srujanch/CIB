vExt.namespace('sbg.common.utils');

/**
 * All the utility functions necessary for SBG
 */
sbg.common.utils = {

	/**
	 * Hides a widget or array of widgets
	 * 
	 * @param widget
	 */
	hideWidgets: function(widget) {
		if(!widget){
			return;
		}
		var widgetWrapper;
		if(!vui.isArray(widget)){
			widgetWrapper = vExt.get(widget.id + "-wrapper");
			widgetWrapper.dom.style.display = "none";
		}else{
			for(var x = 0 ; x < widget.length; x++){
				widgetWrapper = vExt.get(widget[x].id + "-wrapper");
				widgetWrapper.dom.style.display = "none";
			}
		}
		return;
	},

	/**
	 * Shows a widget or array of widgets
	 *
	 * @param widget
	 */
	showWidgets: function(widget) {
		if(!widget){
			return;
		}
		var widgetWrapper;
		if(!vui.isArray(widget)){
			widgetWrapper = vExt.get(widget.id + "-wrapper");
			widgetWrapper.dom.style.display = "block";
		}else{
			for(var x = 0 ; x < widget.length; x++){
				widgetWrapper = vExt.get(widget[x].id + "-wrapper");
				widgetWrapper.dom.style.display = "block";
			}
		}
		return;
	}
}