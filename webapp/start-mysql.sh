#!/bin/bash

docker run -p 127.0.0.1:3306:3306  --name some-mariadb -e MARIADB_ROOT_PASSWORD=securedpassword -d --restart=always mariadb:10
