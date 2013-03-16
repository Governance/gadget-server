 
function getUserPreferenceValue(name) {
   	var queryString, theQueryName, vars, prefs;
   	queryString = window.location.search;
   	prefs = new gadgets.Prefs();
   	if (queryString != " " && queryString != "undefined") {
   		queryString = queryString.substring(1);
   		vars = queryString.split("&");
   		for (var i = 0; i < vars.length; i++) {
   			var pair = vars[i].split("=");
   			if (pair[0] == name) {
   				return pair[1];
   			}
   		}
   	}
   	return prefs.getString(name);
}