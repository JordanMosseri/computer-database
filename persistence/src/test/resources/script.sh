#!/bin/bash

DIR="$( dirname "${BASH_SOURCE[0]}" )"

#echo 'regeneration de la bdd'
mysql -h localhost -u admincdb -pqwerty1234 -e "source $DIR/scriptForTests.sql"
