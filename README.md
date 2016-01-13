vehicle simulator for OSM Sekai :https://github.com/rsabir/josm

##Installation:

###Prerequisites
Before installing the vehicle simulator, you’ll need to install  JRE  or JDK , Apache Tomcat and OSM Sekai Server:
```
# apt-get install default-jre
https://tomcat.apache.org/download-80.cgi
https://github.com/rsabir/josm
```
After that, you need to get client.war in the drive or generate it with the sources present in the git.
drive link:https://drive.google.com/folderview?id=0BywXTC21h2yVa0M3UTJoUnpFZHM&usp=sharing
Then you have to rename client.war to ROOT.war and put it in ...\apache-tomcat-directory\webapps

##Quick Start

To lunch Apache Tomcat you have execute ...\apache-tomcat-directory\webapps\startup.exe for Windows or 
...\apache-tomcat-directory\webapps\startup.sh for linux.
 
After that, you have to configure your default server http://localhost:port/Config

Then you can use the vehicle simulator with http://localhost:port/

if you have any problem please contact us by zakariahili@gmail.com
