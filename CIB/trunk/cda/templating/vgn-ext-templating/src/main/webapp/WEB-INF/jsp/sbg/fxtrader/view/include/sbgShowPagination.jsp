<div class="paginationMenu">
	<ul class="pagination">
		<c:choose>
			<c:when test="${not empty previousLink}">
				<fmt:formatNumber var="previousPageIndex" value="${currentPage-1}" minIntegerDigits="2"/>
				<li class="previous"><a href="${currentChannelChannelURL}${previousLink}" rel="${previousPageIndex}">&laquo; Previous</a></li>
			</c:when>			
			<c:otherwise>
				<li class="previousOff"><span class="inactive">&laquo; Previous</span></li>
			</c:otherwise>
		</c:choose>
		<c:forEach items="${resultLinks}" var="content" varStatus="status">
			<fmt:formatNumber var="pageIndex" value="${(status.index+pageStartNumber)}" minIntegerDigits="2"/>
			<c:choose>
				<c:when test="${currentPageIndex == pageIndex}">
					<li class="active"><span class="inactive">${pageIndex}</span></li>
				</c:when>
				<c:otherwise>
					<li><a rel="${pageIndex}" href="${currentChannelChannelURL}${content}">${pageIndex}</a></li>
				</c:otherwise>
			</c:choose>	
		</c:forEach>		
		<c:choose>
			<c:when test="${not empty nextLink}">
				<fmt:formatNumber var="nextPageIndex" value="${currentPage+1}" minIntegerDigits="2"/>
				<li class="next"><a href="${currentChannelChannelURL}${nextLink}" rel="${nextPageIndex}">Next &raquo;</a></li>
			</c:when>			
			<c:otherwise>
				<li class="nextOff"><span class="inactive">Next &raquo;</span></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>