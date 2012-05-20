Gadget Server Project:
======================

This is a project that builds on the Apache Shindig as the open social gadget containers.


Modules introduction:
=====================

Gadget-Core:                This is the domain object module.
Gadget-Shindig:             This is the module that implements the shindig SPI.
Gadget-Web:                 This is the module for GWT based UI showing the gadgets,
                             and RESTeasy powered restful services


Deploying the Gadget Server:
============================
Run:
   mvn clean install
Then:
  copy following two wars into the $Tomcat webapps folder. (This has been tested under Tomcat 6.x)
  1) gadget-server.war from the gadget-shindig/target folder.
  2) gadget-web.war from the gadget-web/target folder.

Start the Browser (best to use Chrome for now) to go to: 
  http://localhost:8080/gadget-web

You'll need to signup a user to login, there is no default user provided at the moment.




