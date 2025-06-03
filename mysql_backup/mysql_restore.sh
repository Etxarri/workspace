#!/bin/bash

USER="root"
PASSWORD="pasahitza"
DATABASE="welco"
BACKUP_FILE="/workspace/mysql_backup/backups/backup_2025-06-03.sql"

mysql -h 172.18.0.2 -u $USER -p$PASSWORD $DATABASE < $BACKUP_FILE

if [ $? -eq 0 ]; then
    echo "Restauración completada correctamente."
else
    echo "Error en la restauración."
fi