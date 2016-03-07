package za.co.standardbank.sbg.cda.mvc.portlet.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

import za.co.standardbank.sbg.cda.mvc.portlet.domain.ContactMeInfo;

public class ContactMeUpdateController extends SimpleFormController {
	private static Log log = LogFactory.getLog(ContactMeUpdateController.class);

	protected Object formBackingObject(PortletRequest request) throws Exception {
		if (log.isDebugEnabled())
			log.debug("Entering ContactMeUpdateController.formBackingObject() ");
		ContactMeInfo updateContact = (ContactMeInfo) super.formBackingObject(request);
		// Write logic to handle formBackingObject.
		if (log.isDebugEnabled())
			log.debug("Exiting ContactMeUpdateController.formBackingObject() " + updateContact);
		return updateContact;
	}

	protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		if (log.isDebugEnabled())
			log.debug("Entering ContactMeUpdateController.doSubmitAction() " + command);
		// Write logic to handle processFormSubmission for Update.
		if (log.isDebugEnabled())
			log.debug("Exit ContactMeUpdateController.doSubmitAction() " + command);
	}
}
