adaptTo() 2015 - Sling Rookie Session - Sightly Demo
====================================================

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

It is not possible to run this demo in Sling Launchpad because it does not support Sightly.



---

Filesystem Resource Provider
----------------------------

For developing the JSPs with instant feedback in the running instance deploy the Filesystem Resource Provider bundle:
[org.apache.sling.fsresource-1.1.4.jar](http://central.maven.org/maven2/org/apache/sling/org.apache.sling.fsresource/1.1.4/org.apache.sling.fsresource-1.1.4.jar)

And create a new configuration "Apache Sling Filesystem Resource Provider" with
- Provider Root = `/apps/rookiedemo`
- Filesystem Root = `<project root>\src\main\webapp\app-root`
