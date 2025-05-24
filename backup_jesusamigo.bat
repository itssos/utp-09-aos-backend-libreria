@echo off
title HACKER BACKUP - POSTGRES JESUSAMIGO
color 0a

SET PGPASSWORD=1234

set FECHA=%date:~6,4%-%date:~3,2%-%date:~0,2%_%time:~0,2%-%time:~3,2%-%time:~6,2%
set FECHA=%FECHA: =0%
set BACKUPFILE=db/jesusamigo_%FECHA%.sql

cls
echo.
echo [ HACKER BACKUP SYSTEM INICIADO ]
echo.
echo [*] Iniciando extraccion de la base de datos "jesusamigo"...
echo.

REM ----- Spinner hacker real con retroceso (backspace) -----
REM Necesitamos una variable que contenga el carácter de backspace (ASCII 8)
for /f %%a in ('"prompt $H & for %%b in (1) do rem"') do set "BS=%%a"

setlocal enabledelayedexpansion
set "progress=|/-\"
<nul set /p "= [EXTRAYENDO DATOS... ]"
for /l %%i in (1,1,20) do (
    set /a idx=%%i %% 4
    set "chr=!progress:~%idx%,1!"
    <nul set /p "=!chr!"
    ping -n 1 127.0.0.1 >nul
    <nul set /p "=%BS%"
)
endlocal
echo.

echo [*] Ejecutando pg_dump...
echo.

pg_dump -U postgres -d jesusamigo > "%BACKUPFILE%"

IF %ERRORLEVEL% EQU 0 (
    echo [#] Backup completado exitosamente. Archivo generado: %BACKUPFILE%
    echo [#] Proceso finalizado. Los datos han sido extraidos.
) ELSE (
    echo [!] ERROR: Hubo un problema durante el backup.
)

echo.
echo [Presiona una tecla para cerrar...]
pause >nul
exit
