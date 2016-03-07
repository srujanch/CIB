<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="templating" uri="/WEB-INF/vgnExtTemplating.tld"  %>
<%@ taglib prefix="sbg-templating" uri="/WEB-INF/sbg-templating.tld"  %>
<%@ page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Date"%>
<%@page import="za.co.standardbank.sbg.cda.templating.web.util.DPMConstants"%>
<%@ page import="za.co.standardbank.sbg.cda.service.VideoService"  %>

<c:set var="SFPathPrefix" value="/static_files" scope="application"/>
<c:set var="defaultImagePath" value="/vgn-ext-templating/sbg/cib/noImage.jpg" scope="application"/>