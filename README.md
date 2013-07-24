ffauto
======

Tools for managing my fantasy football draft and team. It's a standalone program that's run from the command-line and works off of static text files (for now). 

Right now, all that's present is basic code for managing a draft.

I'll be adding some of my scraping and parsing stuff for stats from pro-football-reference.com in the future.  

The main method to run is Main.java, and it requires two input files: 
* a file containing the list of all available players, as well as their predicted point value in teh upcoming season, and 
* a file containing an ordered list of all players that have been drafted so far, with "*" prefixes for the players that _you_ have already picked up.

An example of the first file, gleaned from the ESPN.com ff site, is given in the src/main/resources folder.

Known Issues / To Do
====================

* Handle bye-weeks for players
* Strategic decisions based on previous choices by other players
* Update the model (move to a sampling/stochastic system?) for the other players draft choices
