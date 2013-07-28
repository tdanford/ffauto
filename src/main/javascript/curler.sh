#!/bin/bash

ESPN_BASE=http://games.espn.go.com/ffl/tools/projections

for (( c = 0; c <= 1340; c = c + 40 )); 
do 
	URL=${ESPN_BASE}?startIndex=${c}  
	echo ${URL}
	curl -o player_table_${c}.html ${URL}
	sleep 3
done

for file in `ls *.html`; do 
	cat ${file} | node extract.js >> player_table.txt ; 
done
