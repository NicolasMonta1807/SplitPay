# SplitPay
JavaFX application for splitting bills between users. Project for Database and JavaFX UI integration

## Cloning this project
 - Once you clone this project using Git or downloading it from zip, load the maven project to retrieve all dependencies. Or use your IDE maven manager.

    ```bash
        mvn clean install -U
    ```

- In the root folder of the project create your .env file for setting database connection variables. Since this project is made with Oracle XE database, you should keep in mind that some integrations could not work with other DBMS.
    ```dosini
    DB_USERNAME=your_username
    DB_PASSWORD=your_password
    DB_URI=url_for_db
    ```

## Deploy
- As can be seen in the pom.xml file, the maven-shade-plugin is in charge of creating the final .jar after running a mvn package. This plugin will embed all JavaFX, JDBC and Dotenv dependencies in a single runnable jar that can be run in any platform.

    This final jar will be created in the target directory that is ignored for this repo.

    ```bash
        mvn clean
        mvn package -Dmaven.test.skip=true
    ```

    The cleanning and packaging process can be done with your IDE tools, specially proved with IntelliJ Idea.

