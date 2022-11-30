# Keyboard Layout Analyser (using) Corpus Crunching
*or K.L.A.C.C. for short*

---
## What does this project do?
A keyboard layout determines the permutation of symbols on your keyboard.
One example of a keyboard layout is the QWERTY layout that we know and love
(or hate) today. QWERTY comes with it's set of flaws; for example: the E key
is placed on the top row despite being the most common letter in English.
To optimize this, the E key should be on the home row.

This program aims to help typists create and optimize keyboard layouts
to minimize for strain and reduce risk of long-term wrist injury, which
is important for those who primarily use a keyboard as their medium of 
work like authors, programmers, etc.

---
## Who will use it?
This program appeals to two specific demographics:
* Alternate layout hobbyists
* Professionals who use the keyboard as their primary medium of work

---
## Why is this project of interest to me?
When I was thinking about my future endeavours and career, I realized
that I would be spending a *looooot* of my time just typing. This
realization made me very interested in ergonomics: looking for chairs,
standing tables, etc. When I got into looking into keyboards, I learned
that there were two main ways to reduce strain whenever typing:

* Using an ergonomically-shaped keyboard (like Alice or split layouts)
* or using a different keyboard layout that makes typing less strainful.

The first option is doable, but nicely made and ergonomic keyboards are
very expensive due to their specialized nature and not-so-high demand.
So I decided to focus on the second option and learned Colemak-DH as
an alternative layout for keyboard. 

After learning Colemak-DH, I got deep into the rabbit hole of alternative
layouts (like different effort models, layouts specifically optimized
for different languages, etc) and I thought that it would be really cool
to create a program that takes a layout and judges how 'optimal' it is.

---
## User Stories

* [X] As a user, I want to see how layouts (like QWERTY,
  Colemak, Dvorak, Workman) are rated against each other by letting them compete.
* [X] As a user, I want to create a keyboard layout and add it to a competition
between other layouts
* [X] As a user, I want to add different corpuses (like language frequencies
for non-English languages) in order to determine which layout is better
for my specific use case.
* [ ] As a user, I want to explore different effort models and valuations
(prioritize hand-alternation, prioritize rolling motions, etc) to
optimize for my typing habits.
* [ ] As a user, I want to see what flaws are present within a keyboard
layout (like excessive strain for typing a specific letter).
* [X] As a user, I want to remove layouts that I'm not interested in
judging, or layouts that aren't applicable to me (like Neo, which is
optimized for typing German).
* [X] As a user, I want to compute specific effort values for typing a certain
corpora on a specific keyboard.
* [X] As a user, I want to add an arbitrary number of keyboards into a tournament
and see which keyboard has the best effort.
* [X] As a user, I want to be able to save the keyboard geometries, layouts and
corpora that I've created.
* [X] As a user, I want to be able to load the saved geometries, layouts and
corpora that I've saved.

---

## Instructions for Grader
* In order to load application state from file, you can go to the Corpora, 
Keyboard.Geometry, Keyboard.Layout windows and use the load button located
at the button. This will show a pop up dialog that queries the user for 
a name, which the application will load into its app state.
* In order to save application state to file, you can go to the same windows
as listed above, select items you want to save and then press the save button.
This button allows for multiple item selections as well, so you can press
<Ctrl> or <Shift> while selecting items to select multiple; pressing the save
button when multiple items are selected will save all of them.
* To generate the first event (add keyboards to tournament), go to the 
Tournament tab and press the create tournament button. You will be greeted
with fields related to constructing a tournament. To add a keyboard, select
any geometry and layout from the dropdowns then press add keyboard.
* To generate the second event (remove keyboard from tournament), go to
the same window as above and create a tournament. After adding keyboards
into the tournament, a remove button should appear on the same line as
the keyboard. Pressing that button will remove that keyboard from the
tournament.
* To find the visual component, create a tournament with multiple keyboards.
Afterwards, you'll be directed back to the main tournament page. A tournament
should now be appearing in the list. Select that tournament, press view
tournament and a bar graph showing the scores of all of the keyboards will
appear.

---

## Phase 4: Task 2
```
Wed Nov 30 11:44:48 PST 2022
Added keyboard 'StandardAlphaKeyboard + QWERTY' to tournament #0.
Wed Nov 30 11:44:49 PST 2022
Added keyboard 'StandardAlphaKeyboard + QWERTY' to tournament #0.
Wed Nov 30 11:44:50 PST 2022
Removed keyboard 'StandardAlphaKeyboard + QWERTY' from tournament #0.
Wed Nov 30 11:44:53 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #0.
Wed Nov 30 11:44:55 PST 2022
Added keyboard 'StandardAlphaKeyboard + Dvorak' to tournament #0.
Wed Nov 30 11:44:57 PST 2022
Added keyboard 'StandardAlphaKeyboard + Workman' to tournament #0.
Wed Nov 30 11:45:10 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #1.
Wed Nov 30 11:45:11 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #1.
Wed Nov 30 11:45:11 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #1.
Wed Nov 30 11:45:12 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #1.
Wed Nov 30 11:45:12 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #1.
Wed Nov 30 11:45:13 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #1.
Wed Nov 30 11:45:14 PST 2022
Removed keyboard 'StandardAlphaKeyboard + Colemak-DH' from tournament #1.
Wed Nov 30 11:45:14 PST 2022
Removed keyboard 'StandardAlphaKeyboard + Colemak-DH' from tournament #1.
Wed Nov 30 11:45:14 PST 2022
Removed keyboard 'StandardAlphaKeyboard + Colemak-DH' from tournament #1.
Wed Nov 30 11:45:23 PST 2022
Added keyboard 'StandardAlphaKeyboard + QWERTY' to tournament #2.
Wed Nov 30 11:45:25 PST 2022
Added keyboard 'StandardAlphaKeyboard + Colemak-DH' to tournament #2.
Wed Nov 30 11:45:28 PST 2022
Added keyboard 'StandardAlphaKeyboard + Dvorak' to tournament #2.
Wed Nov 30 11:45:30 PST 2022
Added keyboard 'StandardAlphaKeyboard + Workman' to tournament #2.
Wed Nov 30 11:45:34 PST 2022
Added keyboard 'StandardAlphaKeyboard + Workman' to tournament #2.
```

Getting a log like this requires some action: you need to import the relevant
layouts, geometries and corpora. After doing that, go to the Tournament
page and click "Create new tournament". You can add and remove keyboards
from that page (which are the two events relating X to Y). The number
after the tournament serves as a unique identifier rather than an actual
name.