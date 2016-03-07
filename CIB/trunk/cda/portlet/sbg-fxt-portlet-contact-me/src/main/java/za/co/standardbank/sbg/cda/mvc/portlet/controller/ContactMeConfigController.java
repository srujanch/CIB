package za.co.standardbank.sbg.cda.mvc.portlet.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

import za.co.standardbank.sbg.cda.mvc.portlet.domain.ContactMeConfigInfo;

public class ContactMeConfigController extends SimpleFormController {
	private static Log logger = LogFactory.getLog(ContactMeConfigController.class);

	protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Entering ContactMeConfigController.doSubmitAction() " + command);
		ContactMeConfigInfo contactMeConfigInfo = (ContactMeConfigInfo) command;
		String returnURL = contactMeConfigInfo.getReturnURL();
		if (logger.isDebugEnabled())
			logger.debug("getReturnURL::" + returnURL);
		if (returnURL != null) {
			PortletPreferences preferences = request.getPreferences();
			String returnURLFromPref = preferences.getValue("returnURL", "DefaultURL");
			if (logger.isDebugEnabled())
				logger.debug("returnURLFromPref::" + returnURLFromPref);
			preferences.setValue("returnURL", returnURL);
			preferences.store();
		}
		if (logger.isDebugEnabled())
			logger.debug("Entering ContactMeConfigController.doSubmitAction() " + command);
	}
}
