# 依赖JAR包说明

请将以下JAR包放置在此目录下：

## 必需JAR包

### 1. Jersey 1.1 相关
- `jersey-core-1.1.jar`
- `jersey-server-1.1.jar`
- `jersey-servlet-1.1.jar`
- `jersey-client-1.1.jar`
- `jersey-json-1.1.jar`

### 2. Jsonic
- `jsonic-1.3.10.jar`

### 3. Logback
- `logback-classic-1.2.3.jar`
- `logback-core-1.2.3.jar`
- `slf4j-api-1.7.25.jar`

### 4. Oracle数据库驱动
- `ojdbc8.jar` 或 `ojdbc6.jar`

### 5. Servlet API
- `servlet-api-3.1.jar`

## 下载地址

### Jersey 1.1
- 官方下载：https://jersey.java.net/download.html
- Maven Central：https://mvnrepository.com/artifact/com.sun.jersey

### Jsonic
- 官方下载：https://jsonic.sourceforge.net/
- Maven Central：https://mvnrepository.com/artifact/net.arnx/jsonic

### Logback
- 官方下载：http://logback.qos.ch/download.html
- Maven Central：https://mvnrepository.com/artifact/ch.qos.logback

### Oracle JDBC驱动
- 官方下载：https://www.oracle.com/database/technologies/maven-central-guide.html

### Servlet API
- Maven Central：https://mvnrepository.com/artifact/javax.servlet/servlet-api

## 版本兼容性

- Java 8+
- Tomcat 8.5+
- Oracle Database 11g+

## 注意事项

1. 确保所有JAR包版本兼容
2. 不要包含冲突的JAR包
3. 建议使用Maven或Gradle管理依赖
4. 在生产环境中使用稳定版本
