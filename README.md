# ResEval Mash

ResEval Mash is a web-based mashup tool to empower non-technical people such as non-programmers to develop complex and non-trivial adhoc applications. The tool provides a mashup based composition editor with drag and drop support to build complex compositions. Currently, for testing purposes, we have created a number of components useful for a number of research evaluation use cases. However, one can extend it to other domains.

## System requirements

- Latest Java version
- MySQL database
- GlassFish or Tomcat application server
- J2EE application compilation support

## Installation Procedure

- The code is divided into two parts. 1) the client-side code, 2) the server-side code. 

**The Client-Side**

The client side code is written in Python and JavaScript languages. Any Python-supported web application server is needed to run the client-side.

**The Server-side**

The server-side is compeletly written in Java. We use JSE, and J2EE. So, you need an application server that support EJB container for the installation of the server-side. Priori to deploy the server-sdie, make sure you have installed MySQL database. 

