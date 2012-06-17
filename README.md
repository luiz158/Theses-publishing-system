Theses publishing system
========================
Project for the PV243 course at the Faculty of Informatics, Masaryk University.

Developers
----------
* [Vaclav Dedik](https://github.com/VaclavDedik)
* [Jakub Cechacek](https://github.com/pseudoem)

Build
-----
Firstly, you need seam-faces 3.2.0 from [here](https://github.com/VaclavDedik/faces) to have in your local maven repository. You can install it with file `install-seam-faces-3.2.0.sh`. Just run this command:

`$ ./install-seam-faces-3.2.0.sh`

Then you can build this application issuing the following command:

`$ mvn package`

Or if you don't want to run the testsuite, run:

`$ mvn package -DskipTests=true`

Deployment
----------
To deploy this application on JBoss AS server, run this command:

`$ mvn jboss-as:deploy`

You need to have JBoss AS server running at localhost:9999 (by default).

Live demo
---------
`http://tps-psdx.rhcloud.com/`

To login, use one of these accounts:
* Username: admin, password: password
* Username: supervisor, password: password
* Username: student, password: password
