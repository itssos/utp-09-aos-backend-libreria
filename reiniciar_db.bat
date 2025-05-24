@echo off
set PGPASSWORD=1234
psql -U postgres -d jesusamigo -c "DROP SCHEMA PUBLIC CASCADE;"
psql -U postgres -d jesusamigo -c "CREATE SCHEMA PUBLIC;"
set PGPASSWORD=
