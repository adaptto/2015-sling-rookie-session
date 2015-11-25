adaptTo() 2015 - Sling Rookie Session
=====================================

This is the companion source code for the Sling Rookie Session held at adaptTo() 2015 in Berlin.

Ths demo uses the [Sightly Scripting Language](http://docs.adobe.com/docs/en/aem/6-0/develop/sightly.html).



Running Demo in AEM6
--------------------

AEM6 has already everyting in place to run the demo.


#### Deploy Demo Application

- Go to root folder of demo application
- Build demo application and deploy to AEM6
```
mvn -Dsling.url=http://localhost:4502 clean install sling:install
```

- Go to demo intro page [http://localhost:4502/content/adaptto.html](http://localhost:4502/content/adaptto.html)



Running Demo in Sling Launchpad
-------------------------------

You can use Sling 8 Lanuchpad to run the demo.


#### Install and start Sling Launchpad

- Download Sling Launchpad JAR: [org.apache.sling.launchpad-8.jar](http://repo1.maven.org/maven2/org/apache/sling/org.apache.sling.launchpad/8/org.apache.sling.launchpad-8.jar)
- Start Sling Launchpad:
```
java -jar org.apache.sling.launchpad-8.jar -f -
```

- Launchpad is now running at
[http://localhost:8080](http://localhost:8080)
- If you want to write data to the repository (e.g. add a talk to a comment) you have to log in using the "Login" link displayed on the left site of the Intro Screen.


#### Deploy Demo Application

- Go to root folder of demo application
- Build demo application and deploy to Sling Launchpad
```
mvn -Dsling.url=http://localhost:8080 clean install sling:install
```

- Go to demo intro page
[http://localhost:8080/content/adaptto.html](http://localhost:8080/content/adaptto.html)


---

Filesystem Resource Provider
----------------------------

For developing the JSPs with instant feedback in the running instance deploy the Filesystem Resource Provider bundle:
[org.apache.sling.fsresource-1.1.4.jar](http://central.maven.org/maven2/org/apache/sling/org.apache.sling.fsresource/1.1.4/org.apache.sling.fsresource-1.1.4.jar)

And create a new configuration "Apache Sling Filesystem Resource Provider" with
- Provider Root = `/apps/rookiedemo`
- Filesystem Root = `<project root>\src\main\webapp\app-root`
