@echo off
rem /**
rem  * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [��Ϣ] ������Ŀ�汾�š�
echo.
rem pause
echo.

cd %~dp0
cd ..

set /p new=�������°汾�ţ�
echo.

set /p choice=����ǿ��հ��밴 "y" ���������������
if /i "%choice%"=="y" set new=%new%-SNAPSHOT
echo.

pause
echo.

rem ����pom�汾��
call mvn versions:set -DnewVersion=%new%

pause