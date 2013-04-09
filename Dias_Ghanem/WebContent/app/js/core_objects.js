
/*Environnement*/

function Environnement(user){
	
	this.users = [];

	if(user){
		this._connectContext();
		this.userConnected = user;
	}
	else
		this._disconnectContext();
}

Environnement.prototype.switchContext = function(){
	
	if(this.context ===  "connected"){
		this._disconnectContext();
	}
	else if(this.context === "disconnected"){
		this._connectContext();
	}
};


/* Sets the DOM to "connected" context */	
Environnement.prototype._connectContext = function(){
	this.context = "connected";
	var postId = $("#content_form");
	var loginId = $("#login");
	var logoutId = $("#logout");

	postId.show();
	loginId.hide();
	logoutId.show();
};


/* Sets the DOM to "disconnected" context */	
Environnement.prototype._disconnectContext = function(){	
	this.context = "disconnected";
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

