package za.co.standardbank.sbg.cda.mvc.portlet.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

import za.co.standardbank.sbg.cda.mvc.portlet.domain.ContactMeInfo;

public class ContactMeInsertController extends SimpleFormController {
	private static Log logger = LogFactory.getLog(ContactMeInsertController.class);

	protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Entering ContactMeInsertController.doSubmitAction() " + command);
		ContactMeInfo contactMeInfo = (ContactMeInfo) command;
		if (logger.isDebugEnabled())
			logger.debug("Exit ContactMeInsertController.doSubmitAction() ");
	}

}
