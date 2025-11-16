<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://jakarta.ee/jsp/jstl/core" prefix="c" %>
<jsp:include page="includes/header.jspf" />

<section class="auth-container">
    <h1>Sign in</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
            ${error}
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login" class="auth-form">
        <div class="form-group">
            <label for="username">Username</label>
            <input id="username" name="username" type="text" required autofocus>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input id="password" name="password" type="password" required>
        </div>

        <button type="submit" class="btn primary">Login</button>
    </form>
</section>

<jsp:include page="includes/footer.jspf" />
