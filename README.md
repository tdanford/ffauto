ffauto
======

Tools for managing my fantasy football draft and team.

I wrote most of this in ~40 minutes before my first ever draft, so there are zero comments and zero interface.  It's a standalone program that's run from the command-line and works off of static text files (for now). 

Right now, the only code present here is some rudimentary stuff for managing a draft.  It's all under the package
    tdanford.ffauto.draft

I'll be adding some of my scraping and parsing stuff for stats from pro-football-reference.com in the future.  

The main method to run is PlayerList.main(), and it requires two input files: 
* a file containing the list of all available players, as well as their predicted point value in teh upcoming season, and 
* a file containing an ordered list of all players that have been drafted so far, with "*" prefixes for the players that _you_ have already picked up.

An example of the first file, gleaned from the ESPN.com ff site, is given in the src/main/resources folder.

2013 Season
-----------

This branch, 'season2013', contains my updates in preparation for the 2013/2014 NFL season.  

Most of the code here is based on the "default" ESPN FF League rules.  Some work would be required to adapt the code to handle other or modified league rules.


Known Issues / To Do
====================

* Handle bye-weeks for players
* Strategic decisions based on previous choices by other players
* Update the model (move to a sampling/stochastic system?) for the other players draft choices
* Understand the effect of matchups on offensive/defensive players
