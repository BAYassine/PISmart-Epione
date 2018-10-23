# PISmart-Epione

## Project structure: 

--epione-ejb
----interfaces ( contains methods headers)
----services ( contains classes that implements interfaces and serve basically to make interactions with the DB)
----entities ( contains entities )
--epione-web
----resources ( contains endpoints that can be accessed using http requests )
----utilities ( contains utility classes like EpioneWS which extends Application  )

Api test uri : localhost:<wildfly-port>/epione-jee-web/api/test