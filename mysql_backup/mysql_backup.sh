#!/bin/bash
# Pasos para automatizar este proceso:
# 1. Instalar cron
# sudo apt update
# sudo apt install cron

# 2. Arranca el servicio
# sudo systemctl enable cron
# sudo systemctl start cron

# 3. Editar las tareas programadas
# crontab -e

# 4. Agregar esta linea para que a las 3:00 de la mañana haga un backup
# 0 3 * * * /workspace/mysql_backup/mysql_backup.sh >> /workspace/mysql_backup/mysql_backup.log 2>&1

# 5. Darle permisos al script y a la carpeta de backups
# chmod +x /workspace/mysql_backup/mysql_backup.sh
# chmod 700 /workspace/mysql_backup/backups


# Configura tus datos
USER="root"
PASSWORD="pasahitza"
DATABASE="welco"
OUTPUT_DIR="/workspace/mysql_backup/backups"
FECHA=$(date +\%Y-\%m-\%d)
FILENAME="$OUTPUT_DIR/backup_$DATABASE_$FECHA.sql"

# Crear backup
mysqldump -h 172.18.0.2 -u $USER -p$PASSWORD $DATABASE > $FILENAME

