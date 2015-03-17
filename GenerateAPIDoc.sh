#!/bin/bash

APIDOC=$(which apidoc);
if [ -z $APIDOC ]
then
  echo "it seems like apidoc is not installed or in your path, install with * npm install apidoc -g *";
  exit 1;
fi
SRC=`dirname $0`;
SAVEDIR=$SRC/src/main/resources/static/api/doc;
apidoc -i $SRC -o $SAVEDIR;
exit 0;
