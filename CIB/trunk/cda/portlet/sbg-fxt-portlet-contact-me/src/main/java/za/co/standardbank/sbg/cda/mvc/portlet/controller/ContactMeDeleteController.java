package za.co.standardbank.sbg.cda.mvc.portlet.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

public class ContactMeDeleteController extends SimpleFormController {
	private static Log logger = LogFactory.getLog(ContactMeDeleteController.class);

	protected void processFormSubmission(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Entering ContactMeDeleteController.processFormSubmission()");
		// Write logic to handle processFormSubmission for deletion.
		if (logger.isDebugEnabled())
			logger.debug("Exiting ContactMeDeleteController.processFormSubmission()");
	}

}
