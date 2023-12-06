<<<<<<< HEAD
# capstone-project-9900h09a5tigers
capstone-project-9900h09a5tigers created by GitHub Classroom

# Tutor Management Web Platform

## Project Overview

The Tutor Management Web Platform is a web application designed to connect students with tutors. It facilitates one-on-one tutoring appointments with private tutors, allowing students to browse, select, and schedule tutorials with tutors based on their courses and availability.
=======

## Front-end

To set up and run the client, follow these steps:

Open a New Terminal: Start by opening a new terminal window on your computer.
Navigate to Project Root Directory: In the terminal, navigate to the project's root directory by entering the following command:	

```
cd .\tutor-manager\tutor-manager-web\src\main\wepapp
```

Installing npm
Next, you will need to install npm (Node Package Manager). To do this:
Install npm: Run the following command in the terminal:

```
npm install
npm run server
```

Setting Up User Avatar Paths
Finally, to ensure that user avatars are correctly fetched from local paths, you need to perform actions in the tutor_photo and student_photo directories:

```
cd .\tutor_photo\
http-server ./
```

```
cd ..
cd .\student_photo\
http-server ./
```

## Database
Initial Setup: The user needs to install MySql first.
Run the SQL file: Login to MySql and run the SQL which is in the capstone-project-9900h09a5tigers. Users may need to uncomment the create schema which is in the SQL file at the first line to create a schema.

```
mysql -u root -ppassword mydb < /home/yourusername/capstone-project-9900h09a5tigers/DB_V3_10.sql
```

## Back-end
Initial setup: The user needs to install Maven at https://maven.apache.org/ and Tomcat first.
Open Terminal: First, open the terminal of your operating system. On Windows, it might be a command prompt or PowerShell, on macOS or Linux, it's Terminal.
Navigate to the project directory: Use the cd command to navigate to the folder containing the Maven project. For example:

```
$> cd .\UNSW\T3\comp9900\workSpace\capstone-project-9900h09a5tigers
```

Make sure the project is a Maven project: Make sure you have a pom.xml file in your project, which is the Maven project configuration file. Then run Tomcat: Under the project directory, execute the following command to start the Tomcat server:

```
[INFO] Scanning for projects...
```

Access the application: Once the server is started, the front-end can call the API through the back-end interface

```
??: Starting ProtocolHandler ["http-bio-8085"]
```
