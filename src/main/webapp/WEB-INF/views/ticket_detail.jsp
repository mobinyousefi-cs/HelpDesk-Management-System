<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://jakarta.ee/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/header.jspf" />

<section class="ticket-detail">
    <h1>Ticket #${ticket.id}</h1>

    <dl class="ticket-metadata">
        <dt>Subject</dt>
        <dd>${ticket.subject}</dd>

        <dt>Description</dt>
        <dd><pre>${ticket.description}</pre></dd>

        <dt>Status</dt>
        <dd>${ticket.status}</dd>

        <dt>Priority</dt>
        <dd>${ticket.priority}</dd>

        <dt>Created At</dt>
        <dd>${ticket.createdAt}</dd>
    </dl>

    <c:if test="${sessionScope.currentUser.role == 'AGENT'}">
        <section class="status-update">
            <h2>Update Status</h2>
            <form method="post" action="${pageContext.request.contextPath}/tickets/updateStatus">
                <input type="hidden" name="ticketId" value="${ticket.id}" />
                <select name="status">
                    <c:forEach items="${statuses}" var="s">
                        <option value="${s}" ${s == ticket.status ? 'selected' : ''}>
                            ${s}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn primary">Update</button>
            </form>
        </section>
    </c:if>

    <p>
        <a href="${pageContext.request.contextPath}/tickets">&laquo; Back to list</a>
    </p>
</section>

<jsp:include page="includes/footer.jspf" />
