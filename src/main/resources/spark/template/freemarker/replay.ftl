<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="5">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl">

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl">

      <div class = "grid-container">

        <div>
          <br/>
          <p id = "availableReplays">
            Available Replays
          </p>

          <!-- Add clickable buttons with available replays. -->
          <form action="./game" method="GET">
            <#list replays as replay>
                <button class = "replay grid-item" name = "replayID" type="submit" value = ${replay.hashCode()}>${replay.toString()}</button>
            </#list>
          </form>
        </div>
      </div>
  </div>

</div>
</body>

</html>