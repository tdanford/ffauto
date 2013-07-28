
var $ = require('jquery')
var fs = require('fs')
var readline = require('readline');
var __ = require('underscore')

function parse_table_file(file, handler) { 
	fs.readFile(file, 'utf8', function(err, data) { 
		if(err) { 
			//console.log('error: ' + err);
		} else { 
			handler(extract_data(data));
		}
	});
}

function parse_stdin(handler) { 
	var rl = readline.createInterface({
		  input: process.stdin,
		  output: process.stdout
	});

	var data = '';
	rl.on('line', function(d) { data = data + d + '\n'; });
	rl.on('close', function() { handler(extract_data(data)); });
}

function extract_data(html) { 
	var $html = $(html);
	var $rows = $html.find('tr.pncPlayerRow');
	var objs = $.map($rows, function(value, i) { 
		var $tr = $(value);
		var name = $tr.find('td.playertablePlayerName').text();
		var points = $tr.find('td.appliedPoints').text();
		return { 'name' : name, 'points' : points };
	});

	return objs;
}

function printer(d) { console.log(d); }
function nothing(d) { }

function table_printer(d) { 
	__.each(d, function(v) { 
		console.log(v.name + '\t' + v.points);
	});
}

parse_stdin(table_printer);

