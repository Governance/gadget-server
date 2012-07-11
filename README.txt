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

