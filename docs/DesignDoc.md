---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: Avoiding Zugzwang
* Team members
  * Andrew Bado
  * Collin Bolles
  * Jacob Jirinec
  * Tristan Knox
  * Evan Nolan

## Executive Summary

How does one recreate a game that is as old and as simple as Checkers in a way that is both exciting and desirable to play? 
The answer, **WebCheckers**! 
This new rendition of Checkers will leave players wanting more. 
By connecting players worldwide WebCheckers brings endless opportunity to test your checker's prowess against any number of opponents. 
And with the ability to practice against AI players and watch replays anyone will be able to take their game to the next level. 
So what are you waiting for, grab your mouse, your keyboard, and your fanciest checkers hat and **lets play WebCheckers!**

### Purpose
WebCheckers aims to provide users means to play games of checkers with others from around the world.
Users should be able to view other users who are looking to play a game and allow them to request a game with anyone from that list.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| MVP  | Minimum Viable Product    |
| OOP  | Object Oriented Programming|
| POJOS| Plain Old Java Objects|
| UI   | User Interface|
| HTML | Hyper Text Markup Language|
| CSS  | Cascading Style Sheets|
| HTTP | Hyper Text Transfer Protocol|


## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

### Definition of MVP
> _Provide a simple description of the Minimum Viable Product._

### MVP Features
> _Provide a list of top-level Epics and/or Stories of the MVP._

### Roadmap of Enhancements
> _Provide a list of top-level features in the order you plan to consider them._


## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](Copy of TeamC_Avoiding_Zugzwang.png)


The main entity in the Domain Model is the ‘Checkers Game’ entity. This entity at a high level 
handles the interactions that exists with the other domain entities. The ‘Checkers Game’ is played
on a ‘Board’ which is made up of ‘Tiles’ which are the individual spaces on the board. The
relation between the ‘Board’ and ‘Tiles’ are important since the ‘Tiles’ represent different
functionality on the ‘Board’. For example, the light vs. dark tiles impact if a ‘Piece’ can be
placed in the given ‘Tile’. Another important entity is the ‘Player’ entity. The ‘Player’
represents the two opposing sides in checkers. ‘Player’ interacts with the ‘Pieces’ and the
color of each ‘Piece’ represents each of the ‘Players’. ‘Turns’ is another important domain
entity as it handles interactions between the ‘Players’ and the ‘Tiles’ through the ‘Move Rules’. 


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface-placeholder.png)

As soon as the user enters the page they will be greeted by a message that displays how many people
 are currently in the player lobby waiting for a game. If they are not signed in they will be asked 
 to sign in using a name of their choosing. Leading to the “waiting for username” and brought to a 
 sign in page. There they can put in a name, which is checked to be a good username. That username 
 is checked for clearance and if it is a good one they move on, else they will be asked to choose a 
 username and given the rules for which to do so. Once they move on they will be brought to the 
 player lobby. Where all the names of potential opponents await to merely be clicked on to be
  brought a game! If such a challenge of clicking on the opponent is issued via a click
  on their name, then they will be given the option to accept or decline the challenge. 
  If it is accepted they will be brought to a new page that displays their board positions 
  and be brought to the “In game” state. Where a plethora of options await the user. One such 
  option is to resign where they will be taken back to the home page. Another is to play the game 
  by making a move which is validated and then brought to the opponents turn once submit turn is
  done. If such a move resulted in the ending of a game they are then brought to the “endgame” 
  where the homepage of the player lobby will be the next sight for the user to see. And lastly
  at any moment where they are in the home page and they wish to sign out they will be taken
  back to the sign in page where they can exit or resign in. 



### UI Tier
> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._


### Application Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._


### Model Tier
The model tier is made up of three main classes. The Game, Player, and 
Turn each represent major functionality of the application. The Game 
handles representation of the checkers board over all including the 
rows, pieces, and spaces on the board. The Player represents a user of
the game who is currently using the application. The Turn represents 
the process of a user going through and making a move in the checkers
application. All interactions with the model tier during game play is 
handled through the Game which then distributes the responsibility to 
the other model tier classes.

#### Game
![The Game UML](game_uml.png)

The Game model handles all of the representation of the checkers board.
The logic of creation of the board’s initial state including the rows 
and spaces is handled within the game. The game also keeps track of all
turns made on it by users. This list is will be used later as part of the
Replay enhancement but is currently used to keep track of the current turn.
Interactions with the model tier takes place through the Game model
including the addition of Moves to a Turn. 
![The Game Sequence](game_creation_sequence.png)
When a game is created, the board
is setup to its initial position and is then interacted through moves to
modify the game.

#### Player
![The Player UML](player_uml.png)
The Player is a simple class which represents a single user via the 
username. The main use of Player is through the passing of the Player 
between the front-end and the back-end. Here the player represents how 
a given user is associated with a given Game. The Player does not handle 
any functionality directly. See PlayerLobby in the application tier to 
see more uses of the Player.

#### Turn
![The Turn UML](turn_uml.png)
The Turn represents the series of moves that are made during a Player’s
turn in the game of checkers. The Turn therefore keeps track of the various 
positions that pieces are being moved to. 
![Move Validation Sequence Diagram](validate_move_sequence.png)
In addition, the Turn has the
logic for validating the Moves that are being added to it. The Turn validated 
the move based on the rules of checkers and returns a response based on rules 
that are potentially broken. Later the Turn will be used in the replay enhancement.

The turn also handles the execution of a move in which the piece is moved on the board and and peices are captured
### Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements.  Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
