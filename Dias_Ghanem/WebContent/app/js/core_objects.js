
/*Environnement*/

function Environnement(context){
	this.users = [];
	this.context = context;  // takes "connected" or "disconnected"
	if(context === "connected")
		this.connectContext();
	else
		this.disconnectContext();
}

Environnement.prototype.switchContext = function(){
	
	if(this.context ===  "connected"){
		this.disconnectContext();
		this.context = "disconnected";
	}
	else if(this.context === "disconnected"){
		this.connectContext();
		this.context = "connected";
	}
};


/* Sets the DOM to "connected" context */	
Environnement.prototype.connectContext = function(){
	var postId = $("#content_form");
	var loginId = $("#login");
	var logoutId = $("#logout");

	postId.show();
	loginId.hide();
	logoutId.show();
};


/* Sets the DOM to "disconnected" context */	
Environnement.prototype.disconnectContext = function(){	
	var postId = $("#content_form");
	var loginId = $("#login");
	var logoutId = $("#logout");

	postId.hide();
	logoutId.hide();
	loginId.show();
};


/*User*/

function User(login,id,key,contact){
	this.login = login;
	this.id = id;
	this.key = key;
	this.contact = contact;
}




/*Message*/

function Message(id,author,text,data,score){
	this.id = id;
	this.author = author;
	this.text = text;
	this.data = data;
	this.score = score;
}




/*RechercheMessage*/

function RechercheMessage(resultats,recherche,contact_only,author,data){
	this.resultats = resultats;
	this.recherche = recherche;
	this.contact_only = contact_only;
	this.author = author;
	this.data = data;
}

