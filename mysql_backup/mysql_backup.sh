#!/bin/bash

# Configura tus datos
USER="root"
PASSWORD="pasahitza"
DATABASE="welco"
OUTPUT_DIR="/workspace/mysql_backup/backups"
FECHA=$(date +\%Y-\%m-\%d)
FILENAME="$OUTPUT_DIR/backup_$DATABASE_$FECHA.sql"

# Crear backup
mysqldump -h 172.18.0.2 -u $USER -p$PASSWORD $DATABASE > $FILENAME
