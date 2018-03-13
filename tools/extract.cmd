@ECHO OFF

SETLOCAL ENABLEDELAYEDEXPANSION
set BASE_DIR_PATH=C:\Temp\excel-prices-extractor
set PROGRAM_JAR=%BASE_DIR_PATH%\excel-prices-extractor-1.0-SNAPSHOT.jar
set PRICES_DIR=%BASE_DIR_PATH%\prices
set OUTPUT_FILE=%BASE_DIR_PATH%\workbook.xlsx
set APPLICATION_CONFIG=%BASE_DIR_PATH%\application.yml
set LOG_CONFIG=%BASE_DIR_PATH%\logback.xml

::echo %BASE_DIR_PATH%

set JAVA_OPTS=-Dspring.main.banner-mode=off
set JAVA_OPTS=%JAVA_OPTS% -Dlogging.config=%LOG_CONFIG%

::echo %JAVA_OPTS%

set PROGRAM_OPTS=--spring.config.location=%APPLICATION_CONFIG%
set PROGRAM_OPTS=%PROGRAM_OPTS% --dir=%PRICES_DIR%
::set PROGRAM_OPTS=%PROGRAM_OPTS% --output=%OUTPUT_FILE%

if not "%~1"=="" (
:loop
    ::echo %1
    set PROGRAM_OPTS=%PROGRAM_OPTS% --search="%1"
    shift
    if not "%~1"=="" goto loop
)

::echo %PROGRAM_OPTS%

java %JAVA_OPTS% -jar %PROGRAM_JAR% %PROGRAM_OPTS%

ENDLOCAL
