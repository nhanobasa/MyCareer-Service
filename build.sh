#!/bin/sh

git pull
rm -rf target/*
mvn clean package -DskipTests
