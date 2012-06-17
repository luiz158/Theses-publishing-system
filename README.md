Theses publishing system
========================
Project for the PV243 course at the Faculty of Informatics, Masaryk University.

Developers
----------
* [Vaclav Dedik](https://github.com/VaclavDedik)
* [Jakub Cechacek](https://github.com/pseudoem)

Build
-----
To build this application, run:
`$ mvn package`
To build this application without tests, run:
`$ mvn package -DskipTests=true

Deployment
----------
To deploy this application on JBoss AS server, run this command:
`$ mvn jboss-as:deploy`

Live demo
---------
`http://tps-psdx.rhcloud.com/`

To login, use one of these accounts:
* Username: admin, password: password
* Username: supervisor, password: password
* Username: student, password: password
