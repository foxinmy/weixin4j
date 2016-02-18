@echo off
rem /**
rem  * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [信息] 导入Nexus服务器。
echo.
pause
echo.

cd %~dp0
cd ..

call mvn clean deploy -Dmaven.test.skip=true -Pdeploy-qd

pause