<%-- declarations --%>
<%@ taglib uri="/WEB-INF/vgnExtTemplating.tld" prefix="templating" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<templating:initRequestContext var="rc"/>

<div id="breadCrumbBar">
	<c:forEach items="${rc.componentFragments}" var="component" varStatus="status">			
		<c:out value="${component.data}" escapeXml="false"/>				
	</c:forEach>
	<br class="clearBoth" />
</div>