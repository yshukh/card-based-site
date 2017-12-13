# BlogRelease1.0
Yaroslav Shukh 's blog (fatiz)

Project requirements: 

1. JDK9
2. PostgreSQL (tested with version 9.0.1)
3. Gradle
4. Tomcat (or something else)

Getting started:

1. Clone repository https://github.com/fatiz1997/BlogRelease1.0 or download ZIP.
2. Create database with PostgreSQL.
(optional) Run database dump.sql from PROJECT_DIR/db/ to restore test data with next command: psql -U postgres ss_blog < dump.sql
3. Edit database properties like host, port, database name, database user and database password at PROJECT_DIR/src/main/java/com/shrralis/ssblog/config/DatabaseConfig.
4. Default values are:
host: localhost,
port: 5432,
database name: blog
database username: "postgres",
database user password: "iknow1997"
5. Download Gradle (https://gradle.org/releases/, complete vers(second link, NOT BINARY), unpack somewhere gradle. Configure your system
environment on your PC (read https://gradle.org/install/#install Step3). Run gradle build if there is any error with tests then run gradle
build -x test for building the project without tests.
6. Deploy generated WAR-file from PROJECT_DIR/build/libs/***.war into the directory where your Tomcat is looking for WAR files.
7. Move the directory PROJECT_DIR/uploaded_image into the same directory where you have moved the WAR-file.
8. Run Tomcat.
