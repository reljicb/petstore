########################################################
REQUIREMENTS

* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Gradle 2.9](http://gradle.org/)
	1. Download binary distribution from the official website
	2. Extract it to a local directory
* [Eclipse Mars 4.5.1] - (https://eclipse.org/downloads/)
* Eclipse plugins from Eclipse Marketplace, in the Help tab:


Note: Buildship Gradle Integration 1.0, Gradle Integration for Eclipse 3.7.2.RELEASE
* Remember to set bin folders from both Java and Gradle in Environment Variables (for Windows) or in $PATH (for Linux/OS X)
	1. Set at the end of ~/.bashrc the following path
		PATH=$PATH:<GRADLE_HOME>/bin



########################################################
QUICK START

Run Petstore API on the localhost:
-------------------------
1. Go into project folder 'api', with terminal and run `gradlew`. This will download gradle 2.9 on your machine (if you haven't already downloaded it in the previous step)
2. Run `gradle build`,
3. Run `gradle bootrun`
4. Point your API client to http://localhost:8080/   - import 'petstore.json.postman_collection' into Postman client to get all test endpoints

* To stop the localhost server, press CTRL+C in Terminal


Run automated tests
-------------------------
1. Go into project folder 'api'
2. Run `gradle test`,


Run Petstore Client
-------------------------
1. Point your webserver to the folder 'client' (for example, by executing 'node server.js' from the client home directory)
2. Point your browser to http://localhost:9000/



########################################################
DEPLOYMENT
(Bluemix)

Requirements
-------------------------
1. Install Bluemix CLI, and CloudFoundry CLI

API
-------------------------
1. Login to Bluemix:
	bluemix login -a https://api.eu-gb.bluemix.net
2. Got to API directory (/api)
3. Push app
	cf push reljicbpetstore -p build/libs/petstore-0.0.1-SNAPSHOT.jar -b https://github.com/cloudfoundry/java-buildpack.git
4. Run app
	cf start reljicbpetstore

Client
-------------------------
1. Login to Bluemix:
	bluemix login -a https://api.eu-gb.bluemix.net
2. Got to client application directory (/client)
3. Push app
	cf push reljicbpetstoreclient
4. Run app
	cf start reljicbpetstoreclient


########################################################
NOTES
	- API application is running on in-memory database. Stoping the API process wipes out all the data.
