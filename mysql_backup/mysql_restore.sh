#!/bin/bash

USER="root"
PASSWORD="pasahitza"
DATABASE="welco"
BACKUP_FILE="/workspace/mysql_backup/backups/backup_2025-06-11.sql"
BINLOG_DIR="/var/lib/mysql/mysql-bin" # Cambia esto si tus binlogs están en otra ruta
BINLOG_FILE="mysql-bin.000055" # Cambia esto por el nombre real de tu binlog

# 1. Restaurar el backup
mysql -h 172.18.0.2 -u $USER -p$PASSWORD $DATABASE < $BACKUP_FILE

if [ $? -eq 0 ]; then
    echo "Restauración completada correctamente."
    # Verificar que mysqlbinlog existe
    if ! command -v mysqlbinlog >/dev/null 2>&1; then
        echo "Error: mysqlbinlog no está instalado o no está en el PATH."
        exit 1
    fi
    # 2. Aplicar los binlogs
    mysqlbinlog $BINLOG_DIR/$BINLOG_FILE | mysql -h 172.18.0.2 -u $USER -p$PASSWORD $DATABASE
    if [ $? -eq 0 ]; then
        echo "Binlogs aplicados correctamente."
    else
        echo "Error al aplicar los binlogs."
    fi
else
    echo "Error en la restauración."
fi