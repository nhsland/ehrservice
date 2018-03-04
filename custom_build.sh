#!/usr/bin/env bash

echo 'running custom mvn by ignoring failed tests'

mvn clean install site -Dmaven.test.failure.ignore=true
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar
