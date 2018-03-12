#!/bin/bash

CURRENT_DIR_PATH=`dirname ${BASH_SOURCE[0]}`
BASE_PATH="/Users/olka/Projects/excel-prices-extractor"
JAR="${CURRENT_DIR_PATH}/target/excel-prices-extractor-1.0-SNAPSHOT.jar"
DIR="${BASE_PATH}/src/test/resources/prices"
OUTPUT="${BASE_PATH}/target/workbook.xlsx"
CONFIG="${BASE_PATH}/src/main/resources/application.yml"

PROGRAM_OPTS="--spring.config.location=${CONFIG}"
PROGRAM_OPTS="${PROGRAM_OPTS} --dir=${DIR}"
PROGRAM_OPTS="${PROGRAM_OPTS} --output=${OUTPUT}"
PROGRAM_OPTS="${PROGRAM_OPTS} --search=Просекко"
PROGRAM_OPTS="${PROGRAM_OPTS} --search=Veneto"

java -jar $JAR $PROGRAM_OPTS
