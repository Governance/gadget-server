Gadget Server Project:
======================

This is a project that builds on the Apache Shindig as the open social gadget containers.


Modules introduction:
=====================

Gadget-Core:                This is the domain object module.
Gadget-Shindig:             This is the module that implements the shindig SPI.
Gadget-Web:                 This is the module for GWT based UI showing the gadgets,
                             and RESTeasy powered restful services
Gadgets:		    This is the module that keeps the available gadgets, it will be deployed as a war.


Building the Gadget Server:
============================

Run:
   mvn clean install



Deploy the Gadget Server:
============================

1. Find gadget-server-1.0.0-SNAPSHOT.zip from the target directory under distribution module. 
2. Unzip gadget-server-1.0.0-SNAPSHOT.zip, follow the readme from gadget-server-1.0.0-SNAPSHOT.zip to deploy the gadget server into AS7 (The AS7 version needs to 7.1.1Final or higher)

