<%@page session="false"%>

<jsp:include page="header.jsp" />

    <section id="main">
        <div class="container">
            <div class="alert alert-info">
                <h2>Welcome !</h2>
                This is a non admin page. You can attempt to login <a style="text-decoration: underline;" href="${pageContext.request.contextPath}/dashboard">here</a>.
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/dashboard.js"></script>

</body>
</html>