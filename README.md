Yaroslav Shukh's blog (fartt)

Next software should be available on target machine to start the application. 
App was tested on the Win10 OS. 

1. _JDK9_
2. _PostgreSQL_ (_v14.1_; win64 installer - https://drive.google.com/file/d/1sv5lpaUeOYGQXS7KjVTKDAkdaf-BP2L_/view?usp=sharing, during installation input the username "postgres", password "postgres" and port - 5432, otherwise you will need to do code changes then. After installation add "POSTGRES_INSTALLATION_PATH\14\bin" to the system variable "Path")
3. _Gradle_ (_v7.3.3_ is suitable, link - https://drive.google.com/file/d/1r7SdM1uuhN0gfoWRZDbn0762Jg_6n5sa/view?usp=sharing, just unpack and add "GRADLE_PATH\bin" to the system variable "Path")
4. _Tomcat_ (works with _9.0.58_, link - https://drive.google.com/file/d/19S7I6ZMbddLl3gpQYbDSNFK0zdRlxnMD/view?usp=sharing, just unpack somewhere you prefer)

Getting started:

1. Clone repository https://github.com/yshukh/card-based-site.git or download ZIP
2. Run in CMD: `psql -U postgres -f PROJECT_DIR\database.sql`
3. Run in CMD: `gradle build -p PROJECT_DIR`
4. Copy the generated WAR-file from **PROJECT_DIR\build\libs\blog.war** into the **TOMCAT_PATH\webapps**
7. Copy the directory **PROJECT_DIR\pictures** into the same directory where you've just moved the WAR-file
8. Run Tomcat (`cd TOMCAT_PATH\bin; startup.bat`)
9. Open the _http://localhost:8080/blog/showPosts_ URL in the web browser
