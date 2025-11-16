<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf" />

<section class="dashboard">
    <h1>Dashboard</h1>
    <p>Welcome, ${sessionScope.currentUser.fullName}!</p>
    <p>Use the navigation bar to manage your tickets.</p>
</section>

<jsp:include page="includes/footer.jspf" />
