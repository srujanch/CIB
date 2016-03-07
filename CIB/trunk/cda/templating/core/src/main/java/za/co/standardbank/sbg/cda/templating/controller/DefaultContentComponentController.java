package za.co.standardbank.sbg.cda.templating.controller;

import javax.servlet.jsp.PageContext;

public class DefaultContentComponentController extends
        AbstractContentComponentController implements
        ContentComponentController {

    public DefaultContentComponentController(PageContext pageContext) {
        super(pageContext);
    }

    @Override
    protected void initialiseComponent() {

    }

}
