#!/bin/sh
LOG_DIR="/apps/contentMigrate/"
LOG_FILE_NAME=content-migrate.log
cd $LOG_DIR
if [ -s $LOG_FILE_NAME ]
then
    mv $LOG_FILE_NAME $LOG_FILE_NAME.`date +%F.%H.%M.%S`
fi

JAVA=/apps/vignette/cms/Content/8_1/java/jre/bin/java
LOG4J_JAR=/apps/vignette/cms/Content/8_1/lib/log4j.jar
ORACLE_JAR=/apps/vignette/cms/Content/8_1/ojdbc5.jar
CMA_JAR=/apps/vignette/cms/Content/8_1/lib/sdk/vgn-appsvcs-cma.jar
THIRDPARTY_JAR=/apps/vignette/cms/Content/8_1/lib/sdk/thirdparty-combined.jar
WEBLOGIC_JAR=/apps/vignette/cms/Content/8_1/rtsvcs/server/lib/wlfullclient.jar
VGN_SHARED_LOG_JAR=/apps/vignette/cms/Content/8_1/lib/vgn-shared-logging.jar
SQL_JAR=/apps/contentMigrate/sqljdbc4.jar

CLASSPATH=$CLASSPATH:./content-migrate.jar

$JAVA -cp .:$LOG4J_JAR:$SQL_JAR:$ORACLE_JAR:$CMA_JAR:$THIRDPARTY_JAR:$WEBLOGIC_JAR:$VGN_SHARED_LOG_JAR:$CLASSPATH za.co.standardbank.sbg.cma.migration.MigrateData $1 $2
code=$?
if [ $code -eq "4" ]
then
    echo "[`date +%F' '%H:%M:%S`][ERROR] Content migration not completed successfully."
	exit 1;
else
    echo "[`date +%F' '%H:%M:%S`][INFO] User migration completed successfully."
    exit 0;
fi