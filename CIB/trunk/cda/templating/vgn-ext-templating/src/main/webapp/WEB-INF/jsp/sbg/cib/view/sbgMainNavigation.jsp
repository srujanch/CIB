<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>
<%!
	private  final Logger logger = Logger.getLogger(this.getClass());
%>

<%
	long t1 = new Date().getTime();
%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentSite" value="${rc.currentSiteBean}"/>
<c:set var="homeChannel" value="${currentSite.homeChannel}"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<div id="topMenuBar">
	<div id="topMenu" class="topMenu">
		<ul id="dropMenu">
			<c:forEach items="${homeChannel.activeSubChannels}" var="topLevelChannel" varStatus="iteratorCount">
				<c:set var="topLevelChannelURL" value="#"/>
				<sbg-templating:descriptorProperty var="isPlaceholderChannel" channelId="${topLevelChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_PLACEHOLDER_CHANNEL%>"/>
				<sbg-templating:descriptorProperty var="subNavigationItems" channelId="${topLevelChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_SUB_NAV_ITEMS%>"/>
				<sbg-templating:descriptorProperty var="noOfNavigationColumns" channelId="${topLevelChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_COLUMNS%>"/>
										
				<c:if test="${isPlaceholderChannel !='Yes'}">
					<templating:contentLink var="topLevelChannelURL" oid="${topLevelChannel.system.id}"/>
				</c:if>
				<c:choose>
					<c:when test="${subNavigationItems=='contentitems'}">
						<c:set var="channelHasSubItems" value="${fn:length(topLevelChannel.contentAssociations)>0}"/>
					</c:when>
					<c:otherwise>
						<c:set var="channelHasSubItems" value="${fn:length(topLevelChannel.activeSubChannels)>0}"/>
					</c:otherwise>
				</c:choose>
				<c:set var="activeTabClass" value="${topLevelChannel == currentChannel?'activeTab':''}"/>
				<li class="${activeTabClass}">
					<a href="${topLevelChannelURL}" class="${channelHasSubItems?'drop':''}">${topLevelChannel.system.name}</a>
				
					<c:if test="${channelHasSubItems}">
						<%-- 
							Get Information about Number of Columns to be shown for each Top level channel to
							display their sub-channels. Use Channel OTM attribute 'No of Columns In Navigation' to get this info.
							Also get the information about in which column a sub channel needs to be shown.
							Get this info from channel OTM attribute 'Column Number'.
						--%>						
						<c:set var="noOfNavigationColumns">${noOfNavigationColumns}</c:set>
						<c:if test="${empty noOfNavigationColumns}">
							<c:set var="noOfNavigationColumns">1</c:set>
						</c:if>
						<sbg-templating:descriptorProperty var="alignmentClass" channelId="${topLevelChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_ALIGNMENT%>"/>
						<div class="dropdown_${noOfNavigationColumns}column${noOfNavigationColumns>1?'s':''} ${alignmentClass}">
							<c:choose>
								<c:when test="${subNavigationItems=='contentitems'}">
									<templating:sort result="sortedContentItems" items="${topLevelChannel.contentAssociations}" properties="title" order="ascending" />
									<c:set var="lengthOfAssociatedCis" value="${fn:length(sortedContentItems)}"/>
									<fmt:parseNumber var="equatedNumber" integerOnly="true" type="number" value="${lengthOfAssociatedCis/noOfNavigationColumns}" />
									<c:set var="modValue" value="${lengthOfAssociatedCis%noOfNavigationColumns}"/>
									<c:set var="startIndex" value="0"/>
									<c:choose>
										<c:when test="${modValue == 0}">
											<c:set var="endIndex" value="${equatedNumber-1}"/>
										</c:when>
										<c:otherwise>
											<c:set var="endIndex" value="${equatedNumber+modValue-1}"/>
										</c:otherwise>
									</c:choose>
									<c:forEach var="columnNo" begin="1" end="${noOfNavigationColumns}" varStatus="status">	
										<div class="col_1">
											<ul class="linkList">
												<c:forEach items="${sortedContentItems}" begin="${startIndex}" end="${endIndex}" var="associatedCi">
													<templating:contentLink var="ciURL" oid="${associatedCi.system.id}"/>
													<li><a href="${ciURL}">${associatedCi.title}</a></li>
												</c:forEach>
											</ul>
										</div>
										<c:set var="startIndex" value="${endIndex+1}"/>
										<c:set var="endIndex" value="${startIndex+equatedNumber-1}"/>
										<c:if test="${endIndex>lengthOfAssociatedCis-1}">
											<c:set var="endIndex" value="${lengthOfAssociatedCis-1}"/>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<jsp:useBean id="columnSubchannelsMap" class="java.util.HashMap"/>
									<c:forEach var="columnNo" begin="1" end="${noOfNavigationColumns}">
										<jsp:useBean id="subchannelsList" class="za.co.standardbank.sbg.cda.templating.web.util.SBGArrayList"/>
										<c:set target="${columnSubchannelsMap}" property="${columnNo}" value="${subchannelsList}"/>
										<c:remove var="subchannelsList" scope="page"/>
									</c:forEach>
									<c:forEach items="${topLevelChannel.activeSubChannels}" var="subChannel">
										<sbg-templating:descriptorProperty var="navigationColumnNumber" channelId="${subChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_COLUMN_NUMBER%>"/>
										<c:set var="navigationColumnNumber">${navigationColumnNumber}</c:set>
										<c:if test="${empty navigationColumnNumber}">
											<c:set var="navigationColumnNumber">${noOfNavigationColumns}</c:set>
										</c:if>
										<c:set var="subchannelsList" value="${columnSubchannelsMap[navigationColumnNumber]}"/>
										<c:if test="${subchannelsList==null}">
											<c:set var="subchannelsList" value="${columnSubchannelsMap[noOfNavigationColumns]}"/>
										</c:if>
										<c:set target="${subchannelsList}" property="addItem" value="${subChannel}"/>
									</c:forEach>
									<c:forEach var="columnNo" begin="1" end="${noOfNavigationColumns}">
										<c:set var="key">${columnNo}</c:set>
										<jsp:useBean id="UlLiMap" class="java.util.LinkedHashMap"/>
										<jsp:useBean id="liList" class="za.co.standardbank.sbg.cda.templating.web.util.SBGArrayList"/>
										<c:set target="${UlLiMap}" property="${topLevelChannel.system.id}" value="${liList}"/>
										<c:remove var="liList" scope="page"/>
										<c:forEach var="subChannel" items="${columnSubchannelsMap[key]}" varStatus="status">
											<sbg-templating:descriptorProperty var="thirdLeveNavItems" channelId="${subChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_SUB_NAV_ITEMS%>"/>
											<c:choose>
												<c:when test="${empty thirdLeveNavItems || thirdLeveNavItems=='subchannels'}">
													<c:set var="thirdLeveNavItemsList" value="${subChannel.activeSubChannels}"/>	
												</c:when>
												<c:when test="${thirdLeveNavItems=='contentitems'}">
													<templating:sort result="thirdLeveNavItemsList" items="${subChannel.contentAssociations}" properties="title" order="ascending" />
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${not empty thirdLeveNavItemsList}">
													<jsp:useBean id="liList1" class="za.co.standardbank.sbg.cda.templating.web.util.SBGArrayList"/>
													<c:forEach var="thirdLeveNavItem" items="${thirdLeveNavItemsList}">
														<c:set target="${liList1}" property="addItem" value="${thirdLeveNavItem}"/>
													</c:forEach>
													<c:set target="${UlLiMap}" property="${subChannel.system.id}" value="${liList1}"/>
													<c:remove var="liList1" scope="page"/>
												</c:when>
												<c:otherwise>
													<c:set target="${UlLiMap[topLevelChannel.system.id]}" property="addItem" value="${subChannel}"/>
												</c:otherwise>
											</c:choose>
											<c:remove var="thirdLeveNavItems" scope="page"/>
										</c:forEach>
										
										<div class="col_1">
											<c:set var="showHr" value="false"/>
											<c:forEach var="entry" items="${UlLiMap}" varStatus="status">
												<templating:bean var="channelObj" oid="${entry.key}"/>
												<c:set var="liList2" value="${entry.value}"/>
												<c:choose>
													<c:when test="${topLevelChannel == channelObj}">
														<c:if test="${fn:length(UlLiMap)>1 && not empty liList2}">
															<h1>${channelObj.system.name}</h1>
															<c:set var="showHr" value="true"/>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:set var="subChannelURL" value="#"/>
														<sbg-templating:descriptorProperty var="isSubChannelAPlaceholderChannel" channelId="${entry.key}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_PLACEHOLDER_CHANNEL%>"/>
														<c:if test="${isSubChannelAPlaceholderChannel !='Yes'}">
															<templating:contentLink var="subChannelURL" oid="${channelObj.system.id}"/>
														</c:if>
														<h1><a href="${subChannelURL}">${channelObj.system.name}</a></h1>
														<c:remove var="isSubChannelAPlaceholderChannel" scope="page"/>
													</c:otherwise>
												</c:choose>
												<c:if test="${not empty liList2}">
													<ul class="linkList">
														<c:forEach var="subChannelObj" items="${liList2}">
															<templating:contentLink var="subChannelObjURL" oid="${subChannelObj.system.id}"/>
															<li><a href="${subChannelObjURL}">${subChannelObj.system.name}</a></li>
														</c:forEach>
													</ul>
												</c:if>
												<c:if test="${showHr && !status.last}">
													<hr />
												</c:if>
												<c:set var="showHr" value="true"/>
											</c:forEach>
										</div>
										<c:remove var="UlLiMap" scope="page"/>
									</c:forEach>
								</c:otherwise>
							</c:choose>							
						</div>
						<c:remove var="alignmentClass" scope="page"/>
						<c:remove var="columnSubchannelsMap" scope="page"/>
						<c:remove var="subchannelsList" scope="page"/>
					</c:if>
				</li>
					<c:remove var="isPlaceholderChannel" scope="page"/>
					<c:remove var="subNavigationItems" scope="page"/>
					<c:remove var="noOfNavigationColumns" scope="page"/>
			</c:forEach>
		</ul>
		<br class="clearBoth" />
	</div>
</div>
<%
	long t2 = new Date().getTime();
	if (logger.isDebugEnabled())
		logger.debug("Time For Processing MainNav::"+String.valueOf(t2-t1));
%>