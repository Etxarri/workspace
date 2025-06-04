#!/bin/bash
set -e

# Espera a que el master esté listo
until mysql -hmysql-master -uroot -ppasahitza -e "SELECT 1"; do
    sleep 2
done

# Obtiene la posición del binlog del master
MASTER_STATUS=$(mysql -hmysql-master -uroot -ppasahitza -e "SHOW BINARY LOG STATUS\G")
BINLOG_FILE=$(echo "$MASTER_STATUS" | grep File: | awk '{print $2}')
BINLOG_POS=$(echo "$MASTER_STATUS" | grep Position: | awk '{print $2}')

# Configura el slave
mysql -uroot -ppasahitza -e "STOP REPLICA;"
mysql -uroot -ppasahitza -e "CHANGE MASTER TO MASTER_HOST='mysql-master',MASTER_USER='replica',MASTER_PASSWORD='replica123',
                            MASTER_LOG_FILE='$BINLOG_FILE',MASTER_LOG_POS=$BINLOG_POS, GET_SOURCE_PUBLIC_KEY = 1;"
mysql -uroot -ppasahitza -e "START REPLICA;"