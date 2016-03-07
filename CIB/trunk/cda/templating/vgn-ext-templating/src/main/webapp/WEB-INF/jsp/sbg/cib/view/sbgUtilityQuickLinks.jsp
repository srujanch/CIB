<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<div class="internetBanking actionBar">
	<c:if test="${not empty content && not empty content.SBG_RELATED_LINKS}">
		<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
		<form class="applicationsMenu" name="applicationsMenu" action="">
			<select id="${rc.region.id}" name="appMenu" class="appMenuSelect">
				<option value="">${content.title}</option>
				<c:forEach items="${sortedLinks}" var="relatedLink">
					<c:set var="item" value="${relatedLink}"/>
					<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
					<c:set var="labelValue" value="${linkTarget=='_blank'?'blank':'self'}"/>
					<option value="${itemURL}" label="${labelValue}">${relatedLink.linkText}</option>
				</c:forEach>
			</select>
			<input type="button" name="button" class="go" value="&nbsp;" onclick="return trackActionClick(document.getElementById('${rc.region.id}').value);" />
		</form>
	</c:if>
</div>