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


      <!-- Check if there is a player signed in. -->
      <#if currentUser??>

        <div class = "grid-container">

          <div>
            <br/>
            <p id = "availableUsers">
              Available Users
            </p>

            <!-- Add clickable buttons with available users. -->
            <form action="./requestgame" method="POST">
              <#list players as player>
                <#if currentUser != player>
                  <button class = "player grid-item" name = "otherUser" type="submit" value = "${player.getName()}">${player.toString()}</button>
                </#if>
              </#list>
            </form>
          </div>

            <!-- Add a button to go to replay mode -->
            <div class = "replayHolder">
              <form action = "./replay" method = "GET">
                <button class = "grid-item" id = "replay" type = "submit">Enter Replay Mode</button>
              </form>
            </div>
        </div>
      </#if>


    <!-- TODO: future content on the Home:
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
