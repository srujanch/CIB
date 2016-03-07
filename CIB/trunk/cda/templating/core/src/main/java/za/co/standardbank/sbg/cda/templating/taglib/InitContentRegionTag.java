package za.co.standardbank.sbg.cda.templating.taglib;

import java.lang.reflect.Constructor;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;
import za.co.standardbank.sbg.cda.templating.controller.ContentComponentController;
import za.co.standardbank.sbg.cda.templating.controller.DefaultContentComponentController;

/**
 * Handles a Content Region component request.
 * 
 */
public class InitContentRegionTag extends TagSupport {

	private static final Logger logger = Logger.getLogger(InitContentRegionTag.class);
	private static final long serialVersionUID = 1L;
	private String controller;

	public final int doStartTag() throws JspException {
		ContentComponentController contentComponentController = getComponentController(controller);
		contentComponentController.handleRequest();
		return SKIP_BODY;
	}

	private final ContentComponentController getComponentController(String componentControllerClass) {
		// If no controller is provided, use default controller
		if (componentControllerClass == null) {
			return new DefaultContentComponentController(pageContext);
		}
		// Otherwise, let's instantiate the specified controller
		try {

			Class[] argsClass = new Class[] { PageContext.class };
			Object[] args = new Object[] { pageContext };
			Class clazz = Class.forName(componentControllerClass);
			Constructor constructor = clazz.getConstructor(argsClass);
			ContentComponentController object = (ContentComponentController) constructor.newInstance(args);
			return object;
		} catch (Exception e) {
			logger.error("Exception occured while instantiating Controller Class::", e);
			throw new CdaRuntimeException("Exception occured while instantiating Controller Class::", e);
		}
	}

	/**
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 * @return int
	 */
	public final int doEndTag() {
		return EVAL_PAGE;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}
}
