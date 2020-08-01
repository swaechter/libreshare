#!/bin/bash

sudo -u postgres psql -c "CREATE DATABASE libreshare;"
sudo -u postgres psql -c "CREATE USER libreshare WITH PASSWORD '123456aA';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE libreshare to libreshare;"
