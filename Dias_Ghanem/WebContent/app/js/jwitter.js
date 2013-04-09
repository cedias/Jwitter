(function( window, undefined ) 
{ 

var Jwitter = new Object();
var baseUrl = "http://li328.lip6.fr:8280/Dias_Ghanem";
window.Jwitter = Jwitter;

Jwitter.login = function(login,pass,callback)
	{
		var url =baseUrl+"/user/login?login="+login+"&pass="+pass;
		Jwitter._get(url,callback);
	};

Jwitter.logout = function(key,callback)
	{
		var url =baseUrl+"/user/logout?key="+key;
		Jwitter._get(url,callback);

	};

Jwitter._get = function(url,callback){
	var jqxhr = $.ajax(url)
	
    .done(function(resp) {
	    console.log(resp);
	     callback(resp);
	 })

    .fail(function() { alert("error in request "+ url); })
}

})(window);