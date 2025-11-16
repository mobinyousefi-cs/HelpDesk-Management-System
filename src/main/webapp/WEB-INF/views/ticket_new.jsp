<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://jakarta.ee/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/header.jspf" />

<section class="ticket-form-container">
    <h1>New Ticket</h1>

    <form method="post" action="${pageContext.request.contextPath}/tickets/new" class="ticket-form">
        <div class="form-group">
            <label for="subject">Subject</label>
            <input id="subject" name="subject" type="text" required>
        </div>

        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description" rows="5" required></textarea>
        </div>

        <div class="form-group">
            <label for="priority">Priority</label>
            <select id="priority" name="priority" required>
                <c:forEach items="${priorities}" var="p">
                    <option value="${p}">${p}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn primary">Create Ticket</button>
        <a class="btn secondary" href="${pageContext.request.contextPath}/tickets">Cancel</a>
    </form>
</section>

<jsp:include page="includes/footer.jspf" />
