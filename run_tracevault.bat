@echo off
setlocal

set "ROOT=%~dp0"
set "CLASSPATH=%ROOT%;%ROOT%lib\postgresql-42.7.5.jar"

java -cp "%CLASSPATH%" gui.MainGUI

endlocal
