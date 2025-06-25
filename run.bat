@echo off

:: 启动 Nacos（传统 cmd 方式）
start "Nacos" cmd /k "cd /d D:\Software\Devtools\NACOS\nacos-server-2.5.1\nacos\bin && startup.cmd -m standalone"

:: 启动 Redis（传统 cmd 方式）
start "Redis" cmd /k "cd /d D:\Software\Devtools\REDIS\Redis-x64-3.2.100 && redis-server.exe"