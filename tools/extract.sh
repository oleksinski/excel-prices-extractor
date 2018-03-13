#!/bin/bash

CURRENT_DIR_PATH=`dirname ${BASH_SOURCE[0]}`

# relative to project
#BASE_PATH="/Users/olka/Projects/excel-prices-extractor"
#JAR="${CURRENT_DIR_PATH}/target/excel-prices-extractor-1.0-SNAPSHOT.jar"
#DIR="${BASE_PATH}/src/test/resources/prices"
#OUTPUT="${BASE_PATH}/target/workbook.xlsx"
#CONFIG="${BASE_PATH}/src/main/resources/application.yml"

# relative to tools
JAR="${CURRENT_DIR_PATH}/excel-prices-extractor-1.0-SNAPSHOT.jar"
DIR="/Users/olka/Projects/excel-prices-extractor/src/test/resources/prices"
OUTPUT="${CURRENT_DIR_PATH}/workbook.xlsx"
CONFIG="${CURRENT_DIR_PATH}/application.yml"
LOGGING="${CURRENT_DIR_PATH}/logback.xml"

JAVA_OPTS="-Dspring.main.banner-mode=off"
JAVA_OPTS="${JAVA_OPTS} -Dlogging.config=${LOGGING}"

PROGRAM_OPTS="--spring.config.location=${CONFIG}"
PROGRAM_OPTS="${PROGRAM_OPTS} --dir=${DIR}"
PROGRAM_OPTS="${PROGRAM_OPTS} --output=${OUTPUT}"
PROGRAM_OPTS="${PROGRAM_OPTS} --search=Prosecco"
PROGRAM_OPTS="${PROGRAM_OPTS} --search=Veneto"

java $JAVA_OPTS -jar $JAR $PROGRAM_OPTS
