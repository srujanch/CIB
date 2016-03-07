package za.co.standardbank.sbg.cda.mvc.portlet.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

public class ContactMeListController implements Controller {
	private static Log log = LogFactory.getLog(ContactMeListController.class);

	public void handleActionRequest(ActionRequest request, ActionResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering ContactMeListController.handleActionRequest()");
			log.debug("Exiting ContactMeListController.handleActionRequest()");
		}
	}

	public ModelAndView handleRenderRequest(RenderRequest renderRequest, RenderResponse response) throws Exception {
		if (log.isDebugEnabled())
			log.debug("Entering ContactMeListController.handleActionRequest()");
		ModelAndView modelAndView = new ModelAndView("contactmeList");
		if (log.isDebugEnabled())
			log.debug("Exiting ContactMeListController.handleActionRequest()");
		return modelAndView;
	}

}
