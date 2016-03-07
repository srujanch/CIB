<%-- declarations --%>
<%@ taglib uri="/WEB-INF/vgnExtTemplating.tld" prefix="templating" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<templating:initRequestContext var="rc"/>
<c:if test="${not empty rc.componentFragments}">
	<div id="pageHeader" class="headerImage col_4">
		<c:forEach items="${rc.componentFragments}" var="component" varStatus="status">			
			<c:out value="${component.data}" escapeXml="false"/>				
		</c:forEach>
	</div>
</c:if>