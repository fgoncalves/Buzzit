# Buzzit

## How does the game work?

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

## How can I install it?

You'll notice the app has 2 flavours - ``production`` and ``mock``. To run the real app you should
choose the production flavour, i.e., ``installProductionRelease``. Further in this document it's
explained why this separation.

## Architecture

The overview of the architecture is depicted by the following layered architecture:

![Overview](/imgs/overview.png?raw=true "Architecture Overview")

- The data layer is responsible for retrieving, creating, deleting and updating the data in the
storage. The storage is implemented in the data layer and independent from the other layers. This
makes it possible to easily replace the storage, i.e., from local to a cloud storage.

- The domain layer is responsible for all the business logic related with the application. It
communicates with the data layer by means of services defined in the data layer and offers use
cases to the presentation layer.

- The presentation layer interacts with the domain layer using it's use cases and is responsible for
the entire UI

The next sections describe each layer in more detail.

### Data

The data layer is depicted by the following architecture:

![Data](/imgs/data.png?raw=true "Data Architecture")

This layer has simply some services that implement the basic functionality of interacting with the
data. The storage can be any kind of storage, just needs to support the operations defined by the
services

### Domain

The domain layer is depicted by the following architecture:

![Domain](/imgs/domain.png?raw=true "Domain Architecture")

The domain layer basically consists of use cases that interact with services. The use cases are
a kind of higher level services. As an example consider the case where you would try to guarantee
a certain sorting of the data to the presentation layer. This sorting should be guaranteed by the
domain layer. The data layer should only be concerned with retrieving the data. All the business
logic should be in the domain layer

### Presentation

The presentation layer is depicted by the following architecture:

![Presentation](/imgs/presentation.png?raw=true "Presentation Architecture")

In this architecture, the presenters communicate with the domain layer by means of the use cases
exposed by said layer. They retrieve the data and tell the view to show it. The view is a
dumb object that simply shows what the presenter tells it to show. The presenters can communicate
between themselves using the event bus.

### Android specific

A closer look to the project reveals a ``production`` and ``mock`` flavours. I chose to separate these
because having a layer architecture is very convenient for testing as well. The flavour ``mock``
can easily mock the domain layer by returning mock data to the presentation layer. This flavour
can then be used to test the UI isolating it from the domain and data layer. Just making sure the
UI functions correctly.

One can then later on use the production flavour for end to end tests and obviously for the real
application.

## Tools & libraries

Libraries for the application code:

 - Dagger 1 - For dependency injection. It's a library I'm comfortable with. I didn't go with
 dagger 2 because at the time of writing this app and to my best knowledge it's still a bit unstable
 - ORMlite - I just needed a simple thing that had the DAO pattern
 - Butterknife - Just to remove boiler plate code
 - Timber - I personally believe that timber brings sense to the android logging
 - Gson - Create the POJOs from the json
 - Rx Android - The application has quite some lengthy operations that shouldn't be in the UI thread
 plus the main mechanics of the game fit really well in the event paradigm offered by Rx.
 - EventBus 2 - Easier communication between entities in the app. I chose the green robot variant
 because it's an implementation I'm comfortable with. I didn't go with EventBus 3 because so far
 I'm not very familiar with the api

Libraries for testing:

 - Junit 4 - Since the support for Junit 4 was introduced and one can run unit tests very fast, TDD
 became for me one of the most crucial points when developing my apps.
 - Mockito - Just makes mocking much easier
 - Assertj - Simply because I find the assertj asserts much more expressive, both in code and error
 output.

As for tools I used gradleplease.appspot.com for most of the libraries. I used jsonschema2pojo.org,
someone once said "I'm too old to write POJOs myself". I did it mainly to speed up the process.

## Decisions made due to limit amount of time

From beginning it was clear that the amount of time allotted for the project would not be enough
to come up with a full fledged application. I decided to focus more in the architecture aspect
of the app and not so much in the design and game mechanics, because I believe it allows to build
a really scalable and extensible app. I believe that making the design and game mechanics better
is easier when the architecture of the app is well divided and clear responsibilities are assigned
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
  to be a bit more complicated than expected. This was a decision taken while developing the app
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
 - Usually this kind of application would get it's data from the cloud, meaning the database of
 words would be much bigger. In this case the service in the data layer would probably be a bit
 more complicated and would most probably support some kind of pagination where you could retrieve
 chunks of data instead of the whole thing. Since this is a simpler project I decided to not
 implement any pagination and retrieve the entire data. This saved me time and complexity.
 - During the project development only some of the classes were unit tested. I decided to only
 unit test those that have heavy logic in it. Simple use cases that just call a method from another
 class were not tested. I usually do this during my projects, because first they're quite simple
 and don't need too much unit testing and afterwards if you do test them they can become extra
 maintenance.
 - I also decided to build the ``com.buzzit.buzzit.domain.usecasses.implementation.PopulateWordsStorageUseCaseImpl``
 in a way that would ease the development and not bother too much with testing since in a normal
 application this use case would usually not exist.
 - I inject the entire application with the application context rather than an activity's context.
 This served my needs and I didn't need to change it. I decided to keep it. Further features might
 require a different context, but this is as simple as implementing a named injection.
 - There are some errors I handled such as empty list of words, but others I simply tell the view
 to display a generic message. I think this can easily be changed and I decided to focus on more
 important things within the time limit.

## Time table

The following is a overview of the time taken to developed the app. Since I was given 8 hours I
planned for that target. I actually missed it, it took me around 9:30 hours.

- 3 hours for planning - where roughly 2 were for the architecture described above and 1 was taken
to figure out the best way to fit the game mechanics with the architecture.
- The rest 6:30 hours were taken developing the solutions and facing some bugs. In the beginning I
saved an hour as buffer for bugs, but this wasn't enough. Eventually I spent roughly 2 hours trying
to find why ORM lite wasn't creating automatically ids and roughly half an hour trying to find
why ``getIdAttribute`` would always return null for my custom views.

## Further work

- I think one of the first steps would actually be improve the UI. This can easily be done by
implementing the ``mock`` flavour with the appropriate implementation and make sure the UI behaves
as expected.

- As said above, there is a problem of synchronization between the text view fading and the words
appearing. This should probably be one of the first things to tackle as it is both a big refactor
and a big issue with the current implementation.

- Remove the hardcoded measurements and make the animations respond to different view sizes.

- Then one can start implementing features like pausing the game, saving the game, change the order
of how words appear or even make them specific to different rounds. Since the application is
sliced in different layers, one could even think of classifying the words as difficult, normal and
easy and based on that sort the order which they appear in.

- I would personally also setup a CI to make sure the application stays stable.

- I would also add some end to end tests.

