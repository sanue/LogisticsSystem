# 物流管理システム (JSP版本)

基于JSP + Jersey + Jsonic技术栈的物流管理系统。

## 技术栈

### 前端 (UI層)
- **JSP** (HTML + JSP + Java Beans + JavaScript + CSS) 👉 画面生成
- **HTML / CSS** 👉 标记与样式
- 屏幕参数（DB、环境、据点等）由 URL 传入，通过 Servlet → JSP → Beans 流转

### 后端 (API & 业务逻辑)
- **Java (JDK)** 👉 核心开发语言
- **Servlet** 👉 与 JSP 的控制器
- **Jersey (1.1)** 👉 REST API 框架（处理 JSON 请求/回应）
- **Jsonic** 👉 JSON 解析/序列化
- **Logback** 👉 日志框架

## 项目结构

```
LogisticsSystem-JSP/
├── WebContent/
│   ├── WEB-INF/
│   │   ├── web.xml                    # Servlet和Jersey配置
│   │   ├── classes/                   # 编译后的Java类
│   │   └── lib/                       # JAR包
│   ├── jsp/
│   │   ├── common/                    # 公共JSP页面
│   │   ├── product/                   # 商品管理页面
│   │   ├── customer/                  # 客户管理页面
│   │   ├── location/                  # 库位管理页面
│   │   └── dashboard.jsp              # 仪表板页面
│   ├── css/
│   │   └── style.css                  # 样式文件
│   └── api/
│       └── test.html                  # REST API测试页面
├── src/
│   └── com/logistics/
│       ├── bean/                      # Java Beans
│       ├── dao/                       # 数据访问层
│       ├── service/                   # 业务逻辑层
│       ├── servlet/                   # Servlet控制器
│       ├── api/                       # Jersey REST API
│       ├── util/                      # 工具类
│       └── filter/                    # 过滤器
├── build.xml                          # Ant构建文件
└── README.md                          # 项目说明
```

## 功能模块

### 1. 商品管理
- 商品列表（分页、搜索、排序）
- 商品详情查看
- 商品新增/编辑/删除
- REST API接口

### 2. 客户管理
- 客户列表（分页、搜索、排序）
- 客户详情查看
- 客户新增/编辑/删除
- REST API接口

### 3. 库位管理
- 库位列表（分页、搜索、排序）
- 库位详情查看
- 库位新增/编辑/删除
- REST API接口

### 4. 仪表板
- 统计数据展示
- 最近记录查看

## 部署说明

### 环境要求
- Java 8+
- Apache Tomcat 8.5+
- Oracle Database 11g+
- Ant 1.9+

### 数据库配置
1. 确保Oracle数据库运行正常
2. 创建数据库用户和表（使用提供的SQL脚本）
3. 修改 `src/com/logistics/util/DBConnection.java` 中的数据库连接信息

### 构建和部署

#### 方法1：使用Ant构建
```bash
# 清理项目
ant clean

# 构建项目
ant build

# 部署到Tomcat
ant deploy

# 运行项目
ant run
```

#### 方法2：手动部署
1. 编译Java源码
2. 将编译后的class文件复制到 `WebContent/WEB-INF/classes/`
3. 将整个 `WebContent` 目录复制到Tomcat的 `webapps/LogisticsSystem/`
4. 启动Tomcat服务器

### 访问地址
- 主页：http://localhost:8080/LogisticsSystem/dashboard
- 商品管理：http://localhost:8080/LogisticsSystem/product
- 客户管理：http://localhost:8080/LogisticsSystem/customer
- 库位管理：http://localhost:8080/LogisticsSystem/location
- API测试：http://localhost:8080/LogisticsSystem/api/test.html

## REST API接口

### 商品API
- `GET /api/products` - 获取商品列表
- `GET /api/products/{id}` - 获取商品详情
- `POST /api/products` - 创建商品
- `PUT /api/products/{id}` - 更新商品
- `DELETE /api/products/{id}` - 删除商品

### 客户API
- `GET /api/customers` - 获取客户列表
- `GET /api/customers/{id}` - 获取客户详情
- `POST /api/customers` - 创建客户
- `PUT /api/customers/{id}` - 更新客户
- `DELETE /api/customers/{id}` - 删除客户

### 库位API
- `GET /api/locations` - 获取库位列表
- `GET /api/locations/{id}` - 获取库位详情
- `POST /api/locations` - 创建库位
- `PUT /api/locations/{id}` - 更新库位
- `DELETE /api/locations/{id}` - 删除库位

## 开发说明

### 添加新功能
1. 在 `bean` 包中创建对应的Bean类
2. 在 `dao` 包中创建数据访问层
3. 在 `service` 包中创建业务逻辑层
4. 在 `servlet` 包中创建Servlet控制器
5. 在 `api` 包中创建REST API接口
6. 在 `jsp` 目录中创建对应的JSP页面
7. 在 `web.xml` 中配置Servlet映射

### 数据库操作
- 使用原生JDBC进行数据库操作
- 通过 `DBConnection` 类获取数据库连接
- 在DAO层实现具体的SQL操作

### 日志记录
- 使用Logback进行日志记录
- 在关键操作处添加日志输出
- 日志级别：DEBUG, INFO, WARN, ERROR

## 注意事项

1. 确保所有JAR包都放在 `WebContent/WEB-INF/lib/` 目录下
2. 数据库连接信息需要根据实际环境进行配置
3. 字符编码统一使用UTF-8
4. 所有JSP页面都需要包含 `header.jsp` 和 `footer.jsp`
5. 表单提交使用POST方法，查询使用GET方法

## 故障排除

### 常见问题
1. **数据库连接失败**：检查数据库连接配置和Oracle服务状态
2. **页面乱码**：确保所有文件都使用UTF-8编码
3. **404错误**：检查Servlet映射配置
4. **500错误**：查看Tomcat日志文件

### 日志查看
- Tomcat日志：`$CATALINA_HOME/logs/`
- 应用日志：通过Logback配置输出到指定文件

## 许可证

本项目仅供学习和研究使用。
