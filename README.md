# Online-Education-Platform

## Introduction
This project is a full-stack website, with the backend developed using Spring Boot and the frontend built with Vue 3. The project simulates basic functionalities within a school setting, such as students selecting courses and leaving reviews, teachers updating new chapters for courses and viewing feedback, and administrators managing users.



## To run the project
Backend:  
IntelliJ is recommended for running backend.  
MySQL is required for the database. Choosing other databases may require additional configuration.  
To set up the database configuration, go to `src/main/resources/application.properties` and change the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` to your own database configuration.

Frontend:  
Follow the instructions in this url:https://docs.npmjs.com/downloading-and-installing-node-js-and-npm to install node.js and npm.  
To run the frontend, go to the folder where the frontend files are, then  run this command in your terminal: 
```bash
npm run dev  
```
Packaging the frontend: npm run build

## Quick Start
#### The Login Page
![Login Page](CourseAccessFront/src/assets/login_page.png)  
To start with, you should log in as an administrator.  
The default administrator account is:
- username: admin
- password: admin
- role: admin

As an administrator, you can manage users and courses.  
You can create accounts for lecturers and students, and you can also create courses.  
Then you can log in as a lecturer or a student to see the functionalities available to them.  


## Note
The project is still in development and may contain many bugs.
New functions, adjustments and modifications will continue to be updated.





