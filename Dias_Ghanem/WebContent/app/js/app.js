

var env;

//var resp = '{"id":2,"login":"marwan","key":"421l073hpv1bvUQi0k41U064v6nxo0rH"}';

//var result = JSON.parse(resp);
//var user = new User(result.login,result.id,result.key);

//console.log(user);
Jwitter.login("charles","3060501", function(resp){console.log(resp);});

// $("#submit_login").on("click", function(){
// 	var login = $("#value_login").val();
// 	var password = $("#value_password").val();
// 	console.log(login + "   " + password);
// 	alert(login);
// 	alert(password);

// 	var callback = function(resp){
// 		var user = JSON.parse(resp);
// 		env = new Environnement(user);
// 	};

// 	Jwitter.login(login, password, callback);

// });

