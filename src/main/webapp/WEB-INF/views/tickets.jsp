<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://jakarta.ee/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/header.jspf" />

<section class="tickets-list">
    <h1>Tickets</h1>

    <div class="toolbar">
        <a href="${pageContext.request.contextPath}/tickets/new" class="btn primary">Create New Ticket</a>
    </div>

    <c:choose>
        <c:when test="${empty tickets}">
            <p>No tickets found.</p>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Subject</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${tickets}" var="t">
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.subject}</td>
                        <td>${t.status}</td>
                        <td>${t.priority}</td>
                        <td>${t.createdAt}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/tickets/detail?id=${t.id}">
                                View
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</section>

<jsp:include page="includes/footer.jspf" />
