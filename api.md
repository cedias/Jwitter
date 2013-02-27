#Jwitter API
----------
##User Service

###Base URL:
`http://jwitter-url/user`

###Login:

- url: /login
- arguments:
 + username: login
 + password: pass
- error-codes:
 + wrongLogin

> http://jwitter-url/user/login?login=user&pass=pass
> Returns a session key

###Logout:
		
- url: /logout
- arguments:
 + session key: key
- error-codes:
 + invalidKey

> http://jwitter-url/user/logout?key=key
> Expires a session key

###New:

- url: /new
- arguments:
 + username : login
 + password : pass
 + first name : fname
 + last name : lname
- error-codes:
 + userAlreadyExists

> http://jwitter-url/user/new?login=user&pass=pass&fname=james&lname=darwin
> Creates a user


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

##Message Service

###Base URL:
`http://jwitter-url/message`

###New

- url: /new
- arguments:

> Creates new message

###Delete

- url: /delete
- arguments:

###List
- url: /list
- arguments:

##Search Service

###Base URL:
`http://jwitter-url/search`

###Message
- url: /message
- arguments:

