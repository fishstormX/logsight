# Logsight
[访问中文版wiki](https://github.com/fishstormX/logsight/wiki)  
[quick start](https://github.com/fishstormX/logsight/wiki/quick_start)  
[示例页面(备案中)](http://io.logsight.club)  

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d7ce82f09f214cdc9a83dbc5764fb1df)](https://www.codacy.com/manual/fishstormX/logsight?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fishstormX/logsight&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/fishstormX/logsight.svg?branch=master)](https://travis-ci.org/fishstormX/logsight) 
[![GitHub last commit](https://img.shields.io/github/last-commit/fishstormX/logsight)](https://github.com/fishstormX/logsight/commit) 
[![GitHub LICENSE](https://img.shields.io/github/license/fishstormX/logsight)](https://github.com/fishstormX/logsight/blob/master/LICENSE)
---------
### version 
[![GitHub Tag](https://img.shields.io/github/v/release/fishstormX/logsight)](https://github.com/fishstormX/logsight/releases/tag/1.1.0-RELEASE)
[![GitHub Tag](https://img.shields.io/github/v/release/fishstormX/logsight?color=orange&include_prereleases&label=SNAPSHOT)](https://github.com/fishstormX/logsight/releases/tag/1.1.2-alpha)
---------
日志可视化系统(尚在开发中)  
已有小规模生产实践

Logsight为基于文件的开源日志查询系统，具有以下应用特点
*   基于跨平台Shell指令的查询，封装了常用的日志查看细节，可以轻松的兼有指令集操作的速度和基于日志持久化存储的便捷  
*   分布式部署，不必将文件集中在某一台数据中
*   日志文件打包和分割下载，基于时间戳和自定义的时间格式化
*   可配置的日志源日志压缩、归档和清理
*   在多个节点间共享日志文件，可选择性的集中存储
*   流式传输和筛选日志，更方便的查看实时日志
*   日志的watch机制，基于此可扩展监控系统
