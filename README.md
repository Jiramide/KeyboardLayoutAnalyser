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