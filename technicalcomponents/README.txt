To test the application, you need to :


1- Have an instance of PostgreSQL 9.4
----------------------------------
- Create a database with name 'ism', owned by a user 'ism' with password 'ism'
- PostgreSQL must be started on default port 5432 (if you change the port, change it in src/main/resources/config/application.properties)

2- Create default storage directory for file upload
-------------------------------------------------
 > mkdir c:/ismstorage/store1

3- Launch the application
----------------------
- mvn clean package
- java -jar target/technicalcomponents-0.0.1-SNAPSHOT.jar

4- Open Web Explorer
-----------------
- Got to url : http://localhost:7080

or

- Got to url : http://localhost:7080/docs

# Au 09 avril 2015
# ----------------
Quoi regarde pour le moment ?
  - Upload : vous pouvez allez cherche un fichier quelconque avec "choose" puis faire "save". Le fichier doit être uploadé dans c:/ismstorage/store1
  - Catalog : 
  		- Constater que l'on a 2 composants dans le tableau
  		- Cliquer sur la combo et voir que l'on a 1 catalogue "default" (ça sera le seul à l'avenir en fait). L'affichage très très moche de l'arbre du catalogue