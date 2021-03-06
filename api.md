#Jwitter API
----------

**[General Overview](#general-overview)**
/ [Services](#services)
/ [Arguments](#arguments)
/ [Error Codes](#error-codes)    
**[User Service](#user-service)**
/ [Login](#login)
/ [Logout](#logout)
/ [New](#new)
/ [Info](#info)  
**[Friend Service](#friend-service)**
/ [Add](#add)
/ [Remove](#remove)
/ [List](#list)  
**[Message Service](#message-service)**
/ [Post](#post)
/ [Delete](#delete)
/ [List](#list-1)

##General Overview

###Services

Jwitter api has 4 distinct services:

- User : handles authentification and sessions
- Friend : handles friends
- Message : handles messages
- Search : handles messages searchs

All of these services are reachable using the HTTP protocol over GET method.
Arguments and Errors are standardized.

###Arguments

Since the GET protocol is using the URL to transmit calls arguments' names have been standardized and shrinked.
####Authentification

 + user's login := login
 + user's password := pass
 + user's session key := key
	

####id

 + user's id := uid
 + friend's id := fid
 + message's id := mid
	

####Search/Lists

 + Maximum results := maxr
 + Results' offset := off
 + Query := q
 + Restrict to user's friend := rtf
	

####Miscellaneous 

 + a message := msg
 + a first name := fname
 + a last name := lname

### Error-Codes

Every API call can go wrong, whenever it does your JSON resp will look like this `{message:"error",error_code:code}`.
This is the meaning of those codes

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
> Returns a session key

- url: /login
- arguments:
 + username: login
 + password: pass
- error codes:
 + wrong login : 291



`http://jwitter-url/user/login?login=user&pass=pass`

```JSON
{"id":33,"login":"user","key":"AwEs0m3K3yToUs3InJw177Er"}
```

###Logout:
> Expires a session key

- url: /logout
- arguments:
 + session key: key
- error codes:
 + invalid key : 403

`http://jwitter-url/user/logout?key=key`

```JSON
{"message":"ok"}
```

###New:
> Creates a user

- url: /new
- arguments:
 + username : login
 + password : pass
 + first name : fname
 + last name : lname
- error codes:
 + user already exists : 3

`http://jwitter-url/user/new?login=user&pass=pass&fname=james&lname=darwin`

```JSON
{"message":"ok"}
```

###Info
> Info on a user


- url: /info
- arguments:
 + username : login or uid
 + [session key : key]

 ```
 http://Jwitter-url/user/info?uid=4
 http://Jwitter-url/user/info?login=user1
 http://Jwitter-url/user/info?uid=4&key=supersecretkey
 ```
 
 ```JSON
 {"id":1,"first_name":"Jean","friend_count":10,"last_name":"Guy","login":"jg398","last_jweets":{"messages":[{"message":"i believe i can fly","id":1,"_id":"51687c80e4b0c087485cf63d","login":"jg398","date":"Fri Apr 12 23:28:32 CEST 2013"},...]}}
 ```


##Friend Service

###Base URL:
`http://jwitter-url/friend`

###Add:
> add a friend

- url: /add
- arguments:
 + friend id : fid
 + session key : key
- error codes:
 + user doesn't exist : 20
 + invalid key : 403
 + already friends : 999


`http://jwitter-url/friend/add?fid=42&key=supersecretkey`


```JSON
{"message":"ok"}
```

###Remove:
> removes a friend

- url: /remove
- arguments:
 + friend id : fid
 + key : key
- error codes:
 + invalid key : 403

`http://jwitter-url/friend/remove?fid=42&key=supersecretkey`

```JSON
{"message":"ok"}
```

###List:
> List a user's friends

- url: /list
- arguments:
 + username : login or uid
 + [ max results returned : maxr - default = 10 ]
 + [ offset: off - default = 0 ]
- error codes:
 + user doesn't exist : 20
 + result is empty : 398
 + invalid key : 403

```
http://jwitter-url/friend/list?login=jacktheboss
http://jwitter-url/friend/list?login=jacktheboss&off=10&maxr=8
```

```JSON
{"friends":[{"id":2,"login":"jack","date":"2013-02-14 17:14:22.0"},…]}
```

##Message Service

###Base URL:
`http://jwitter-url/message`

###Post
> Post a new message

- url: /post
- arguments:
 + key : key
 + message : msg
- error codes:
 + invalid key : 403

`http://jwitter-url/message/new?key=thisisakey&msg=helloworld`

```JSON
{"message":"Hello World !","message_id":"515feb36e4b0e4363f1bef2e"}
```

###Delete
> Delete a message

- url: /delete
- arguments:
 + key : key
 + message id : mid
- error codes:
 + invalid key : 403

`http://jwitter-url/message/new?key=thisisakey&mid=42`

```JSON
{"message":"ok"}
```

###List
> list messages

- url: /list
- arguments:
 + [ only userid's messages : uid or username : login ]
 + [ max results returned : maxr - default = 100 ]
 + [ offset: off - default = 0 ]
 + [ list until message : mid]
- error codes:
 + user doesn't exist : 20
 + result is empty : 398

```
http://jwitter-url/message/list?uid=12
http://jwitter-url/message/list?login=jack
http://jwitter-url/message/list?uid=12&off=12&maxr=5
http://jwitter-url/message/list?login=jack&off=12&maxr=5
http://jwitter-url/message/list?mid=515feb36e4b0e4363f1bef2e
```

```JSON
{"message":[{"message":"I do really love apples","id":1,"_id":"512b3fd8e4b02d9a74da067c","login":"charles"},….]}
```

