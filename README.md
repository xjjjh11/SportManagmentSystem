# SportManagmentSystem
# it is a project about gdou Ocean Sport Managment System
# 广东海洋大学体育管理系统

后台技术栈：springboot+springsecurity+mybatis  

本项目为前后端分离，此处只有后台代码  

jdk-version:11  

springboot-version:2.3.11  

额外使用了alibaba的druid连接池，jjwt生成token，lombok插件，swagger2接口测试框架      

由于时间较紧，项目可改进之处：  
      1、使用redis数据库存储token，实现10天免登陆功能  
      2、使用elasticsearch，实现自定义用户、场地、赛事等信息的查询功能  
