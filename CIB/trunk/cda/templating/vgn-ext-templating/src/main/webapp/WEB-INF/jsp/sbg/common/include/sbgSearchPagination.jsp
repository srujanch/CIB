<div class="paginationMenu">
    <ul class="pagination">
        <c:if test="${numberOfPages gt 1}">
            <c:set var="url" value="?" />
            <c:set var="flag" value="false" />

            <c:if test="${not empty param.Year}">
                <c:set var="url" value="${url}Year=${param.Year}" />
                <c:set var="flag" value="true" />
            </c:if>
            <c:if test="${not empty param.Country}">
                <c:choose>
                    <c:when test="${flag}">
                        <c:set var="url" value="${url}&Country=${param.Country}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="url" value="${url}Country=${param.Country}" />
                        <c:set var="flag" value="true" />
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${not empty param.Sector}">
                <c:choose>
                    <c:when test="${flag}">
                        <c:set var="url" value="${url}&Sector=${param.Sector}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="url" value="${url}Sector=${param.Sector}" />
                        <c:set var="flag" value="true" />
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${not empty param.Product}">
                <c:choose>
                    <c:when test="${flag}">
                        <c:set var="url" value="${url}&Product=${param.Product}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="url" value="${url}Product=${param.Product}" />
                        <c:set var="flag" value="true" />
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${(not empty param.Client) && (param.Client ne 'Client')}">
                <c:choose>
                    <c:when test="${flag}">
                        <c:set var="url" value="${url}&Client=${param.Client}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="url" value="${url}Client=${param.Client}" />
                        <c:set var="flag" value="true" />
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:choose>
                <c:when test="${currentPageIndex eq 1}">
                    <li class="previousOff"><span class="inactive">&laquo; Previous</span></li>
                </c:when>
                <c:otherwise>
                    <li class="previous"><a href="${url}${flag?'&':''}vgnNextStartIndex=${currentPageIndex-1}">&laquo; Previous</a></li>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="1" end="${numberOfPages}" step="1" varStatus="status">
                <c:choose>
                    <c:when test="${currentPageIndex == i}">
                        <li class="active"><span class="inactive">0${i}</span></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${url}${flag?'&':''}vgnNextStartIndex=${i}">0${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${currentPageIndex lt numberOfPages}">
                    <li class="next"><a href="${url}${flag?'&':''}vgnNextStartIndex=${currentPageIndex+1}">Next &raquo;</a></li>
                </c:when>
                <c:otherwise>
                    <li class="nextOff"><span class="inactive">Next &raquo;</span></li>
                </c:otherwise>
            </c:choose>
        </c:if>
    </ul>
</div>
<br class="clearBoth" />