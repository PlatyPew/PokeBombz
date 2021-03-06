<p align="center">
    <br />
    <img src="assets/screen/titlelogo.png" alt="pokebombz" width="410" height="101" />
    <h1 align="center">A Bomberman-esque game that is actually fun!</h1>
</p>

<p align="center">
    <i>PokeBombz is a Bomberman-esque game built with the LibGDX framework in Java.</i>
    <br /><br />
    <img src="https://github.com/sgtechICT1009/ict1009-team01-2022/actions/workflows/gradle.yml/badge.svg?branch=dev" alt="ci badge" />
    <img src="https://img.shields.io/badge/license-MIT-green.svg" />
    <img src="https://img.shields.io/badge/Java-17.02+0-blue.svg" />
    <br /><br />
    <a href="https://youtu.be/KH4pPEYr6As">
        <img align="center" src="https://res.cloudinary.com/marcomontalbano/image/upload/v1645795428/video_to_markdown/images/youtube--KH4pPEYr6As-c05b58ac6eb4c4700831b2b3070cd403.jpg" alt="PokeBombz Trailer" width="80%" />
    </a>
</p>
<hr>

## Documentation

Get started with PokeBombz

-   [Setup](#setup)
    -   [Building from source](#building-from-source-and-running)
    -   [Run pre-built jar file](#run-pre-built-jar-file)
-   [Instructions](#instructions)
    -   [Keyboard Controls](#keyboard-controls)
    -   [Controller Controls](#controller-controls)
    -   [Chatbot Commands](#chatbot-commands)
        -   [Game](#game)
        -   [Scoring](#scoring)
        -   [Miscellaneous](#miscellaneous)
-   [UML Diagram](#uml-diagram)
-   [Contributors](#Contributors)

## Setup

### Building from source and running

MacOS and Linux

```
./gradlew desktop:run
```

Windows

```
.\gradlew.bat desktop:run
```

## Instructions

### Keyboard Controls

| Player | Movement           | Place Bomb  | Kicking | Throwing |
| ------ | ------------------ | ----------- | ------- | -------- |
| 1      | WASD               | Backspace   | -       | =        |
| 2      | TFGH               | \           | [       | ]        |
| 3      | IJKL               | Enter       | ;       | '        |
| 4      | Up Down Left Right | Right Shift | .       | /        |

### Controller Controls

| Movement | Place Bomb | Kicking | Throwing |
| -------- | ---------- | ------- | -------- |
| D-Pad    | A          | L1      | R1       |

## Chatbot Commands

#### IMPORTANT

| Player | Target |
| ------ | ------ |
| 1      | p1     |
| 2      | p2     |
| 3      | p3     |
| 4      | p4     |

### General

Exit game program:

```
exit
```

Exit to main menu:

```
exit main menu
```

Open help file:

```
help
```

### Game

Change player speed:

```sh
# Target all players
change speed <speed:int>

# Target specific player
change speed <speed:int> <player:target>
```

Change player bomb count:

```sh
# Target all players
change bomb count <count:int>

# Target specific players
change bomb count <count:int> <player:target>
```

Change player bomb range:

```sh
# Target all players
change bomb range <range:int>

# Target specific players
change bomb range <range:int> <player:target>
```

Change player bomb kick ability:

```sh
# Target all players
change kick bomb

# Target specific players
change kick bomb <player:target>
```

Change countdown timer:

```sh
# Between 50-500 inclusively
change death timer <time:int>
```

Change item spawn rate:

```sh
# Between 0-100 inclusively
change spawn chance <rate:int>
```

Starts sudden death immediately:

```sh
start sudden death
```

### Scoring

All players scores revert to 0

```
reset score
```

Saves score to database

```sh
# Add score to database
update new score

# Load save ID in database
update score id <id:int>
```

Loads score from database

```sh
upload score id <id:int>
```

### Miscellaneous

_Any command that starts with "who", "what" and "how" would be will be presumed to be a question._

_The chatbot would reply the question according to its database._

_If the data isn't present, it would reply that it doesn't understand the question and expects the user to reply with an answer and save it to the database._

Load current information:

```
load
```

Reset chatbot database to defaults

```
reset
```

Save information into database

```
save
```

## UML Diagram

[![UML Diagram](docs/Project_UML_Diagram.png)](docs/Project_UML_Diagram.png)

## Contributors

This project would not exist without these folks!

-   [@Brockkoli](https://github.com/Brockkoli)
-   [@JonTJT](https://github.com/JonTJT)
-   [@Mel-YWM](https://github.com/Mel-YWM)
-   [@PlatyPew](https://github.com/PlatyPew)
-   [@PototoPatata](https://github.com/PototoPatata)
-   [@tryhardlaijun](https://github.com/tryhardlaijun)
