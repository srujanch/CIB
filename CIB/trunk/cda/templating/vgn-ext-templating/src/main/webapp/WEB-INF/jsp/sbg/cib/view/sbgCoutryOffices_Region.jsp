<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="parentChannel" value="${currentChannel.parentChannel}"/>

<c:if test="${not empty component.results}">
	<ul class="bigMap">
		 <c:forEach items="${component.results}" var="content">
			<templating:contentLink var="linkUrl" channelId="${currentChannel.system.id}" oid="${content.system.id}"/>
			<c:set var="dotPositionInMap" value=" right"/>
			<c:if test="${content.labelPosition=='Right'}">
				<c:set var="dotPositionInMap" value=""/>
			</c:if>
			<li><a href="${linkUrl}" class="${content.title}"><span class="dot bigDot${dotPositionInMap}">&nbsp;</span><span class="label">${content.headOfficeLocation}</span></a></li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${not empty parentChannel.subChannels}">
	<div class="countryList">
		<c:forEach items="${parentChannel.subChannels}" var="subChannel" varStatus="status">
			<c:set var="isSubChannelCurrentChannel" value="${subChannel==currentChannel}"/>
			<c:set var="noOfAssociatedOffices" value="${subChannel.contentInstanceCount}"/>
			<sbg-templating:descriptorProperty var="noOfColumns" channelId="${subChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_COLUMNS%>"/>
			<c:set var="noOfColumns">${noOfColumns}</c:set>
			<c:set var="noOfColumns" value="${not empty noOfColumns?noOfColumns:'1'}"/>
			<div class="column col_${noOfColumns} ${status.last?'last':''} ${isSubChannelCurrentChannel?'':'disabled'}">
				<h1>${subChannel.system.name}</h1>
				<c:if test="${noOfAssociatedOffices>0}">
					<ul class="world">
						<c:choose>
							<c:when test="${isSubChannelCurrentChannel}">
								<c:forEach items="${subChannel.contentInstances}" var="countryOffice">
									<templating:contentLink var="itemURL" channelId="${currentChannel.system.id}" oid="${countryOffice.system.id}"/>
									<li><a href="${itemURL}" class="${countryOffice.system.name}">${countryOffice.system.name}</a></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${subChannel.contentInstances}" var="countryOffice">
									<li>${countryOffice.system.name}</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>	
					</ul>
				</c:if>
			</div>	
		</c:forEach>
		<br class="clearBoth" />
	</div>
</c:if>