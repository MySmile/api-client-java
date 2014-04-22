MySmile API REST Client
=======================

Objective
---------
This project provides simple java client for web-service such as MySmile REST API.
MySmileClient can be used to perform HTTP connection by GET method to web-servisce.
It use ParserFactory to treat the response from the server. 

The ParserFactory is a generic interface which must be specified for some base class of parsing objects.

In this project as parsing class it is used JSONObject which can parse json and xml response from the server.
Using JSONOBject JSONMySmileClien extends MySmileClent to treat json and xml response from web-servise.  

Dependences
-----------
To parse json and xml response from the server in this project JSON package in Java 
(https://github.com/douglascrockford/JSON-java) is used.
