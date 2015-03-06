#!/bin/bash

#echo 'regeneration de la bdd'
mysql -h localhost -u admincdb -pqwerty1234 -e "source /home/excilys/workspace_jee/computerdatabase_test_maven/src/main/resources/scriptForTests.sql"
#admincdb qwerty1234