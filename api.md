#Jwitter API
----------
##General Overview

Jwitter api has 4 distinct services:

- User : handles authentification and sessions
- Friend : handles friends
- Message : handles messages
- Search : handles messages searchs

All of these services are reachable using the HTTP protocol over GET method.
Arguments and Errors are standardized.

##Arguments

###Authentification

 + user's login := login
 + user's password := pass
 + user's session key := key
	

###id

 + user's id := uid
 + friend's id := fid
 + message's id := mid
	

###Search/Lists

 + Maximum results := maxr
 + Results' offset := off
 + Query := q
 + Restrict to user's friend := rtf
	

###Miscellaneous 

 + a message := msg
 + a first name := fname
 + a last name := lname

## Error-Codes

- 3  : User Already Exists
- 20 : Invalid User
- 56 : Invalid Message ID
- 84 : Parameters Error
- 291 : Wrong Login
- 398 : Empty Result
- 403 : Invalid Key
- 900 : Database Error
- 999 : Other Error

Error n° 84 and 900 can always happen.

##User Service

###Base URL:
`http://jwitter-url/user`

###Login:

- url: /login
- arguments:
 + username: login
 + password: pass
- error codes:
 + wrong login : 291

`http://jwitter-url/user/login?login=user&pass=pass`

> Returns a session key

###Logout:
		
- url: /logout
- arguments:
 + session key: key
- error codes:
 + invalid key : 403

`http://jwitter-url/user/logout?key=key`

> Expires a session key

###New:

- url: /new
- arguments:
 + username : login
 + password : pass
 + first name : fname
 + last name : lname
- error codes:
 + user already exists : 3

`http://jwitter-url/user/new?login=user&pass=pass&fname=james&lname=darwin`

> Creates a user


##Friend Service

###Base URL:
`http://jwitter-url/friend`

###Add:

- url: /add
- arguments:
 + friend id : fid
 + session key : key
- error codes:
 + user doesn't exist : 20
 + invalid key : 403
 + already friends : 999


`http://jwitter-url/friend/add?fid=42&key=supersecretkey`

> adds a friend to key user

###Remove:

- url: /remove
- arguments:
 + friend id : fid
 + key : key
- error codes:
 + invalid key : 403

`http://jwitter-url/friend/remove?fid=42&key=supersecretkey`

> removes a friend to key user

###List:

- url: /list
- arguments:
 + username : login 
 + [ max results returned : maxr - default = 10 ]
 + [ offset: off - default = 0 ]
- error codes:
 + user doesn't exist : 20
 + result is empty : 398
 + invalid key : 403

`http://jwitter-url/friend/list?login=jacktheboss
http://jwitter-url/friend/list?login=jacktheboss&off=10&maxr=8`

> List a user's friends

##Message Service

###Base URL:
`http://jwitter-url/message`

###New

- url: /new
- arguments:
 + key : key
 + message : msg
- error codes:
 + invalid key : 403

`http://jwitter-url/message/new?key=thisisakey&msg=helloworld`

> Post a new message

###Delete

- url: /delete
- arguments:
 + key : key
 + message id : mid
- error codes:
 + invalid key : 403

`http://jwitter-url/message/new?key=thisisakey&mid=42`

> Delete a message

###List
- url: /list
- arguments:
 + user id : uid or username : login
 + [ max results returned : maxr - default = 100 ]
 + [ offset: off - default = 0 ]
- error codes:
 + user doesn't exist : 20
 + result is empty : 398

`http://jwitter-url/message/list?uid=12
http://jwitter-url/message/list?login=jack
http://jwitter-url/message/list?uid=12&off=12&maxr=5
http://jwitter-url/message/list?login=jack&off=12&maxr=5`

> list all user's message

