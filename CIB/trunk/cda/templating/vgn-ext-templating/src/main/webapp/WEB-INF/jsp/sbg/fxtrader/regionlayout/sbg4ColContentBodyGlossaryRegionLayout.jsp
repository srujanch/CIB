<%-- declarations --%>
<%@ taglib uri="/WEB-INF/vgnExtTemplating.tld" prefix="templating" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<templating:initRequestContext var="rc"/>

<div class="col_4 contentBody glossary" id="content">
	<c:set var="noOfComponents" value="${fn:length(rc.componentFragments)}"/>
	<c:forEach items="${rc.componentFragments}" var="component" varStatus="status">			
		<c:out value="${component.data}" escapeXml="false"/>
		<c:if test="${noOfComponents>1}">
			<br class="clearBoth" />
		</c:if>
	</c:forEach>
</div>