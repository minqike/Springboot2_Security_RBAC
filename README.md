# Springboot2_Security_RBAC

#### 介绍
springBoot+springSecurity 数据库动态管理用户、角色、权限

#### 软件架构
软件架构说明


#### 安装教程

1. 创建数据库，导入sql，doc目录下有sql

2. 修改application.yml中的datasource的url

   ```yaml
     datasource:
       name: test
       url: jdbc:mysql://127.0.0.1:3306/rbac_db
       username: root
       password: root
       # druid 连接池
       type: com.alibaba.druid.pool.DruidDataSource
       driver-class-name: com.mysql.jdbc.Driver
   ```

3. 启动项目

4. 登录用户为admin/123456,user/123456

   123456的加密后的密码为a09fea8ff3b9c3e135414f16cf06de21


