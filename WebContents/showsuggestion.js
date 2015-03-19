oThis = null;

function ShowSuggestion(){
	this.control = null;
	this.fill = false;
	this.xmlHttp = new XMLHttpRequest();
	this.init();
}

ShowSuggestion.prototype.init = function() {
	oThis = this;
}

ShowSuggestion.prototype.parseSuggestion = function(){
	if (oThis.xmlHttp.readyState == 4){
		var response = oThis.xmlHttp.responseText;
		var xmlDoc;
		if (window.DOMParser){
			var parser = new DOMParser();
			xmlDoc = parser.parseFromString(response, "text/xml");
		} else {
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		  	xmlDoc.async = false;
		  	xmlDoc.loadXML(response);
		}
		var resultElements = xmlDoc.getElementsByTagName("CompleteSuggestion");
		var results = [];
		for (var i = 0; i < resultElements.length; ++i){
			results.push(resultElements[i].getElementsByTagName("suggestion")[0].getAttribute("data"));
		}
		if (results.length != 0){
			oThis.control.autosuggest(results, oThis.fill);
		}
		document.getElementById("test").innerHTML = response;
	}
};

ShowSuggestion.prototype.getSuggestion = function(autoSuggestControl, fill) {
	this.control = autoSuggestControl;
	this.fill = fill;
	var q = "suggest?q=" + encodeURI(this.control.textbox.value);
	this.xmlHttp.open("GET", q);
	this.xmlHttp.onreadystatechange = this.parseSuggestion;
	this.xmlHttp.send(null);
};
