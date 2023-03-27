<h1>Recommended configuration</h1>

<ul>
	<li>DB : postgresSQL 12</li>
	<li>Java :jdk-11.0.16 or jdk-19.0.1</li>
</ul>

<h2>Perform DB installation</h2>
	apt-get install postgresql12<br>
	service postgresql start<br>
	Use this to change the default password in postgres<br>
	sudo su postgres<br>
	psql -d postgres<br>
	ALTER USER postgres WITH PASSWORD ‘yournewpassword';
<h2>Please do so in the directory directly under this project</h2>
	mvn package<br>
	java -jar target/mySolution-0.0.1.jar
