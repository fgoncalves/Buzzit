# Buzzit

## How does the game work

The game consists of 4 players, each with a button and a color assigned. These are green, yellow,
blue and red. The buttons are distributed across each corner of the device and have a number in
the middle. This is the player's score.

In the center of the screen a word will show up. This is the target word. The target word can appear
in either english or spanish.

Floating around the screen there will be a word which can or not be the translation for the target
word. This word changes every 3 seconds by means of a fade in/fade out animation. If the target
word is in spanish, then this word will be in english. If the target word is in english, then this
word will be in spanish.

The loop of words is continuous, but the correct translation will show up with a chance of 25%. In
other words, there's a chance that on every 4 words one will be the correct translation.

If the players think the correct translation is on screen, then they should press the button. If
right, the first one who clicked will see the screen flash with his or her color and his score
incremented by 1. In case the player is wrong, then nothing happens.

The game ends when all the words are used and the player with the highest score wins.

## Architecture

## Decisions made due to limit amount of time

From beginning it was clear that the amount of time allotted for the project would not be enough
to come up with a full fledged application. I decided to focus more in the architecture aspect
of the app and not so much in the design and game mechanics, because I believe it allows to build
a really scalable and extensible app. I believe that making the design and game mechanics better
wis easier when the architecture of the app is well divided and clear responsibilities are assigned
to each layer. That said, these were the decisions I took from start:

 - Ending the game when all the words are used instead of making rounds of specific words - This
 meant less logic to implement on deciding which words to show.
 - Similar to the one above, I decided to simply show the words in a sequential way. If a new round
 starts the same sequence of words is used - This also meant less logic to implement and would not
 affect the game outcome.
 - Usually when I choose this architecture, each layer has its own models. From experience I know
  that even with the automated tools out there to generate code this usually means some extra time
  that I wasn't planning on wasting. One has to make sure each layer is translating their models to
  the correct models of the next layer. In this simple app the models would be exactly the same, so
  I decided to use simply the models from the data layer.
 - Similar to the decision above, the ORM model is also tied to the data layer one. Usually I
 separate them, but this again meant making sure all the translations are in place.
 - Simply because of time restrictions, the measurements for each player button are hardcoded as
  well as the bounding box for the floating word. Making this adapt to different screens turned out
  to be a bit more complicated that expected. This was a decision taken while developing the app
  and not during planning.
 - The application was built to support 2 and only 2 languages. This made the whole design and
 implementation much simpler.

## Other decisions

 - I decided to create a use case that would bootstrap the data in the application. In general, the
 json given was not suitable for my needs. I felt with a storage where I could easily manipulate
 each word and the entire collection would be a better fit. Obviously this storage needed to be
 pre-populate before the game start. This is the reason why there's a bootstrap use case in the
 app
 - As a consequence of the above decision, I also decided that this storage should be permanent
 because it would be easier to implement a "pause" functionality in the game or even a
 "save game/load game" functionality. I decide to go with the local database.
 - Initially I projected to simply have one animation moving and fading the possible translations
 shown to the user and then have this word change over the course of the game. Technically, the
 text view is always moving and fading and the words are fed from another channel. While in theory
 this worked quite well testing the app proved different. Soon the fade in and fade out become
 unsynchronized with the word showing up. This leads the players to think the same word is been
 shown twice. Unfortunately, this was only apparent to me in a very late development stage and I
 thought it would be best not to touch this. Correcting this would mean change a lot of the
 mechanics and time was not plenty.
 - The game works by selecting a target word and then removing it from the available storage. These
  was implemented as such to avoid showing the same target word more than once.
 - The above decision comes with 2 consequences. If the players wish to restart the game, then they
 need to restart the application as there's no "restart game" functionality. The other consequence
 comes with a more fringe case. In the odd event that the word fails to be removed no special
 treatment is given to the error. The error is simply ignored. The word will be shown again sometime
 in the future. This might not be desirable, but at the time didn't really sound like it would be
 of a big impact to the game purpose.


## Topics

- How does the game work
- How to install
- Tools chosen and why
- How is the architecture
  -- Layers
  -- MVP

- Log time/plan
- Answer questions


