#Jwitter API

----------

##User Service

###Base URL:
`http://jwitter-url/user`
  
###Login:

	- url: /login
	- arguments:
		+ username: String
		+ password: String

	> Returns a session key

###Logout:
		
	- url: /logout
	- arguments:
		+ key: String

	> Closes a session key

###New:

	- url: /new
	- arguments:
		+ username : String
		+ password : String

	> Creates a user

###Deactivate
		
	- url: /deactivate
	- arguments:
		+ username: String
		+ password: String

	> Deactivates a user

##Friend Service

###Base URL:
`http://jwitter-url/friend`

###Add:

	- url: /add
	- arguments:
		+ username : String
		+ key : String

	> adds a friend to key user

###Remove:

	- url: /remove
	- arguments:
		+ username : String
		+ key : String

	 > removes a friend to key user

###List:
	- url: /list
	- arguments:
		+ username : String 
		+ nbRes : Int
		+ offset: Int

	> List a user's friends

