#!/bin/bash

sudo -u postgres psql -c "DROP DATABASE libreshare;"
sudo -u postgres psql -c "DROP ROLE libreshare;"
