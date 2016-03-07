package za.co.standardbank.sbg.cda.templating.controller;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;

import javax.servlet.jsp.PageContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import za.co.standardbank.sbg.cda.exception.CdaRuntimeException;

import com.vignette.as.client.exception.ApplicationException;
import com.vignette.as.common.util.StringUtil;
import com.vignette.as.config.ConfigUtil;
import com.vignette.ext.templating.client.javabean.ContentComponent;
import com.vignette.ext.templating.util.PageUtil;
import com.vignette.ext.templating.util.RequestContext;

/**
 * Abstract base component controller containing methods required by all
 * component controllers.
 * 
 */
public abstract class AbstractContentComponentController {
	private static final Logger logger = Logger.getLogger(AbstractContentComponentController.class);

	private static boolean isMgmtStage;
	private RequestContext requestContext;
	private Locale locale;
	private PageContext pageContext;
	private ContentComponent component;
	private Document configAttXmlDocument;

	static {
		isMgmtStage = ConfigUtil.isMgmtStage();
	}

	/**
	 * Abstract method allowing concrete classes to initialize specific content
	 * component as necessary.
	 */
	protected abstract void initialiseComponent();

	protected ContentComponent getComponent() {
		return component;
	}

	protected void setComponent(ContentComponent component) {
		this.component = component;
	}

	/**
	 * Constructor.
	 * 
	 * @param pageContext
	 *            - Page Context
	 * @throws ApplicationException
	 */
	public AbstractContentComponentController(PageContext pageContext) {
		this.pageContext = pageContext;
		this.requestContext = PageUtil.getCurrentRequestContext(this.pageContext);
		this.locale = requestContext.getLocale();
		try {
			this.component = (ContentComponent) this.requestContext.getRenderedManagedObject();
		} catch (ApplicationException e) {
			logger.error("ApplicationException while retrieving the Current component from  requestContext.", e);
			throw new CdaRuntimeException("Failed to retrieve content component for region", e);
		}
	}

	private AbstractContentComponentController() {
	};

	/**
	 * @see za.co.standardbank.sbg.cda.templating.controller.ContentComponentController#handleRequest()
	 */
	public final void handleRequest() {

		this.pageContext.setAttribute("isMgmtStage", isMgmtStage);

		/**
		 * hand over to the particular component to set any additional required
		 * attributes
		 */
		initialiseComponent();
	}

	/**
	 * Retrieve Request Context.
	 * 
	 * @return Request Context
	 */
	protected final RequestContext getRequestContext() {
		return this.requestContext;
	}

	/**
	 * Retrieve Page Context.
	 * 
	 * @return Page Context
	 */
	protected final PageContext getPageContext() {
		return this.pageContext;
	}

	/**
	 * Retrieves xapath value/node value of given expression from the current
	 * component xml.
	 * 
	 * @return xpath value
	 */
	protected String getXPathValue(String attributeXmlName, String expression) throws XPathExpressionException {
		if (this.configAttXmlDocument == null) {
			String xml = null;
			try {
				xml = (String) component.getAttributeValue(attributeXmlName);
			} catch (ApplicationException e) {
				logger.error("ApplicationException while retrieving the component data in xml format.", e);
				throw new CdaRuntimeException("ApplicationException while retrieving the component data in xml format.", e);
			}
			if (!StringUtil.isEmpty(xml)) {
				try {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Reader reader = new CharArrayReader(xml.toCharArray());
					this.configAttXmlDocument = builder.parse(new InputSource(reader));
				} catch (ParserConfigurationException e) {
					logger.error("ParserConfigurationException while building the document object from component data.", e);
					throw new CdaRuntimeException("ParserConfigurationException while building the document object from component data.", e);
				} catch (SAXException e) {
					logger.error("SAXException while building the document object from component data.", e);
					throw new CdaRuntimeException("SAXException while building the document object from component data.", e);
				} catch (IOException e) {
					logger.error("IOException while building the document object from component data.", e);
					throw new CdaRuntimeException("IOException while building the document object from component data.", e);
				}
			}
		}

		if (this.configAttXmlDocument != null) {
			try {
				XPath xpath = XPathFactory.newInstance().newXPath();
				Node node = (Node) xpath.evaluate(expression, this.configAttXmlDocument, XPathConstants.NODE);
				if (node != null)
					return node.getTextContent();
			} catch (XPathExpressionException xPathExprEx) {
				// Throw same Exception
				throw xPathExprEx;
			}
		}
		return null;
	}
}
