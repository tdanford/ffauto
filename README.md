ffauto
======

Tools for managing my fantasy football draft and team.

Right now, the only code present here is some rudimentary stuff for managing a draft.  It's all under the package
    tdanford.ffauto.draft

I'll be adding some of my scraping and parsing stuff for stats from pro-football-reference.com in the future.  

The main method to run is PlayerList.main(), and it requires two input files: 
* a file containing the list of all available players, as well as their predicted point value in teh upcoming season, and 
* a file containing an ordered list of all players that have been drafted so far, with "*" prefixes for the players that _you_ have already picked up.

An example of the first file, gleaned from the ESPN.com ff site, is given in the src/main/resources folder.

