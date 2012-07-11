Deploying Gadget Server in JBoss AS7:
=====================================

1) Update the as7.home to your JBoss AS7 folder.
2) Run following command to deploy the gadget server into AS7 that specified in first step.
	ant deploy

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