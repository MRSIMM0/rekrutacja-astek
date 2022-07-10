# Reimbursement Calculation Application

This project provides a simple reimbursement calculator. Backend is written in Java 11 and frontend is written in React.js

## Requirements

  * Java 11
  
  * Maven
  
  * Node (for frontend)


First you have to make sure that you have all of the required technologies installed.

#### Java
 Open terminal and run `java --version` if you have already installed java 11 you can go to next step, if don't you have to download java JDK from the [link](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html).  If the installed JDK is not detected by the system you have to set a new environment variable.
 
Open Windows Settings -> Select System -> click on About ->  click on Advanced systems settings ->The System Properties box will open.

Now select Advanced settings, next click on Environment Variables select Path under System Variables section and click on Edit. We need to add the path of installed JDK to system Path.

Click on New Button and add the path to the installed JDK. Then press ok.

#### Maven
Open terminal and run `mvn --version` if you have already installed maven you can go to next step, if don't you can follow instructions on official page 
[link](https://maven.apache.org/install.html).

#### Node
Open terminal and run `node -v` if you have already installed maven you can go to the next step, if you don't download and install node from the official page [link](https://nodejs.org/en/download/).

## Setup

First you have to either clone this project via `git clone` or download the zip file and extract it in the desired folder.

#### Backend
  * Tests - to run tests you have to navigate into backend folder next  use `mvn clean test`
  
  * Build - to build a project you have to navigate into the backend folder, next use `mvn package` to build the project. Jar file will be located in `/backend/src/target` 
  
  * Run - to run backend you have to navigate into backend folder next to  use <br> ` mvn clean compile exec:java -Dexec.mainClass="src.Server"`
  
#### Frontend
To run frontend navigate to frontend folder and execute `npm i` to install all required dependencies then run `npm run`


