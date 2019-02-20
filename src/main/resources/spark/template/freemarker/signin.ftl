<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>Web Checkers | Sign In</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

    <h1>Web Checkers | Sign In</h1>

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl">
    </div>

    <form action="./signinattempt" method="POST">
        <br/>
        <input username="myUserName" />
        <br/><br/>
        <button type="submit">Ok</button>
    </form>

</div>
</body>

</html>