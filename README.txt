Gadget Server Project:
======================

This is a project that builds on the Apache Shindig as the open social gadget containers.


Modules introduction:
=====================

Gadget-Core:                This is the domain object module.
Gadget-Shindig:             This is the module that implements the shindig SPI.
Gadget-Web:                 This is the module for GWT based UI showing the gadgets,
                             and RESTeasy powered restful services


Building the Gadget Server:
============================

Run:
   mvn clean install


Deploying Gadget Server in JBoss AS7:
=====================================

1) Add following datasource into $JBoss-AS7/standalone/configuration/standalone.xml, inside the '<datasources>' tag.

<datasource jndi-name="java:jboss/GadgetServer" pool-name="GadgetServer" enabled="true" use-java-context="true">
    <connection-url>jdbc:h2:mem:gadget-server;DB_CLOSE_DELAY=-1</connection-url>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
        <password>sa</password>
    </security>
</datasource>

2) copy following wars into the $JBoss-AS7/standalone/deployments folder. (This has been tested under JBoss AS7.1.1.Final)
  * gadget-server.war from the gadget-shindig/target folder.
  * gadget-web.war from the gadget-web/target folder.
  * gadgets.war from the gadgets/target folder.

3) Start the server via following command in $JBoss-AS7 folder:
    bin/standalone.sh

Deploying Gadget Server in Tomcat:
=================================
To be done here, need to work out the H2 JNDI datasource in Tomcat, or create a separate war file for it.


Trying the Gadget Server UI:
============================

Start the Browser (best to use Chrome for now) to go to: 
  http://localhost:8080/gadget-web

You'll need to signup a user to login, there is no default user provided at the moment.

