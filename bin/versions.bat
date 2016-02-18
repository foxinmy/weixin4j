@echo off
rem /**
rem  * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [信息] 更新项目版本号。
echo.
rem pause
echo.

cd %~dp0
cd ..

set /p new=请输入新版本号：
echo.

set /p choice=如果是快照版请按 "y" 否则按任意键继续：
if /i "%choice%"=="y" set new=%new%-SNAPSHOT
echo.

pause
echo.

rem 更新pom版本号
call mvn versions:set -DnewVersion=%new%

pause