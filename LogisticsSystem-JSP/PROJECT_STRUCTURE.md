# 物流管理システム JSP版本 - 项目结构图

## 完整项目结构

```
LogisticsSystem-JSP/
├── WebContent/                          # Web应用根目录
│   ├── WEB-INF/                         # Web应用配置目录
│   │   ├── web.xml                      # Servlet和Jersey配置
│   │   ├── classes/                     # 编译后的Java类文件
│   │   └── lib/                         # 依赖JAR包目录
│   │       ├── jersey-*.jar             # Jersey 1.1框架
│   │       ├── jsonic-*.jar             # Jsonic JSON处理
│   │       ├── logback-*.jar            # Logback日志框架
│   │       ├── ojdbc8.jar               # Oracle数据库驱动
│   │       ├── servlet-api.jar          # Servlet API
│   │       └── README.md                # JAR包说明
│   ├── jsp/                             # JSP页面目录
│   │   ├── common/                      # 公共JSP页面
│   │   │   ├── header.jsp               # 页面头部
│   │   │   ├── footer.jsp               # 页面底部
│   │   │   └── error.jsp                # 错误页面
│   │   ├── product/                     # 商品管理页面
│   │   │   ├── list.jsp                 # 商品列表
│   │   │   ├── detail.jsp               # 商品详情
│   │   │   └── edit.jsp                 # 商品编辑
│   │   ├── customer/                    # 客户管理页面
│   │   │   ├── list.jsp                 # 客户列表
│   │   │   ├── detail.jsp               # 客户详情
│   │   │   └── edit.jsp                 # 客户编辑
│   │   ├── location/                    # 库位管理页面
│   │   │   ├── list.jsp                 # 库位列表
│   │   │   ├── detail.jsp               # 库位详情
│   │   │   └── edit.jsp                 # 库位编辑
│   │   └── dashboard.jsp                # 仪表板页面
│   ├── css/                             # 样式文件目录
│   │   └── style.css                    # 主样式文件
│   └── api/                             # API测试页面
│       └── test.html                    # REST API测试页面
├── src/                                 # Java源码目录
│   └── com/logistics/                   # 包根目录
│       ├── bean/                        # Java Beans
│       │   ├── ProductBean.java         # 商品Bean
│       │   ├── CustomerBean.java        # 客户Bean
│       │   └── LocationBean.java        # 库位Bean
│       ├── dao/                         # 数据访问层
│       │   ├── ProductDAO.java          # 商品数据访问
│       │   ├── CustomerDAO.java         # 客户数据访问
│       │   └── LocationDAO.java         # 库位数据访问
│       ├── service/                     # 业务逻辑层
│       │   ├── ProductService.java      # 商品业务逻辑
│       │   ├── CustomerService.java     # 客户业务逻辑
│       │   └── LocationService.java     # 库位业务逻辑
│       ├── servlet/                     # Servlet控制器
│       │   ├── ProductServlet.java      # 商品Servlet
│       │   ├── CustomerServlet.java     # 客户Servlet
│       │   ├── LocationServlet.java     # 库位Servlet
│       │   └── DashboardServlet.java    # 仪表板Servlet
│       ├── api/                         # Jersey REST API
│       │   ├── ProductAPI.java          # 商品REST API
│       │   ├── CustomerAPI.java         # 客户REST API
│       │   └── LocationAPI.java         # 库位REST API
│       ├── util/                        # 工具类
│       │   ├── DBConnection.java        # 数据库连接工具
│       │   └── JsonUtil.java            # JSON工具类
│       └── filter/                      # 过滤器
│           └── CharacterEncodingFilter.java  # 字符编码过滤器
├── build.xml                            # Ant构建文件
├── logback.xml                          # Logback日志配置
├── deploy.sh                            # 部署脚本
├── README.md                            # 项目说明
└── PROJECT_STRUCTURE.md                 # 项目结构说明
```

## 技术架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        前端层 (JSP)                          │
├─────────────────────────────────────────────────────────────┤
│  JSP页面 (HTML + JSP + Java Beans + JavaScript + CSS)      │
│  ├── dashboard.jsp (仪表板)                                │
│  ├── product/ (商品管理页面)                               │
│  ├── customer/ (客户管理页面)                              │
│  ├── location/ (库位管理页面)                              │
│  └── common/ (公共页面)                                    │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     控制器层 (Servlet)                       │
├─────────────────────────────────────────────────────────────┤
│  Servlet控制器                                              │
│  ├── ProductServlet (商品控制器)                           │
│  ├── CustomerServlet (客户控制器)                          │
│  ├── LocationServlet (库位控制器)                          │
│  └── DashboardServlet (仪表板控制器)                       │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     业务逻辑层 (Service)                     │
├─────────────────────────────────────────────────────────────┤
│  业务逻辑处理                                               │
│  ├── ProductService (商品业务逻辑)                         │
│  ├── CustomerService (客户业务逻辑)                        │
│  └── LocationService (库位业务逻辑)                        │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     数据访问层 (DAO)                         │
├─────────────────────────────────────────────────────────────┤
│  数据库操作                                                 │
│  ├── ProductDAO (商品数据访问)                             │
│  ├── CustomerDAO (客户数据访问)                            │
│  └── LocationDAO (库位数据访问)                            │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     数据库层 (Oracle)                        │
├─────────────────────────────────────────────────────────────┤
│  Oracle数据库                                               │
│  ├── PRODUCT_MASTER (商品主表)                             │
│  ├── CUSTOMER_MASTER (客户主表)                            │
│  └── LOCATION_MASTER (库位主表)                            │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    REST API层 (Jersey)                      │
├─────────────────────────────────────────────────────────────┤
│  Jersey 1.1 REST API                                       │
│  ├── ProductAPI (商品REST接口)                             │
│  ├── CustomerAPI (客户REST接口)                            │
│  └── LocationAPI (库位REST接口)                            │
└─────────────────────────────────────────────────────────────┘
```

## 数据流转图

```
用户请求 → Servlet → Service → DAO → 数据库
    ↓
JSP页面 ← Java Beans ← 业务逻辑 ← 数据访问 ← 查询结果
    ↓
HTML响应 → 浏览器显示
```

## 技术栈对应关系

| 技术栈 | 实现位置 | 说明 |
|--------|----------|------|
| JSP | WebContent/jsp/ | 页面生成和显示 |
| Java Beans | src/com/logistics/bean/ | 数据传输对象 |
| Servlet | src/com/logistics/servlet/ | 请求控制器 |
| Jersey | src/com/logistics/api/ | REST API框架 |
| Jsonic | 工具类中使用 | JSON处理 |
| Logback | logback.xml | 日志记录 |
| Oracle | DAO层 | 数据库操作 |

## 部署流程

1. **环境准备**
   - 安装Java 8+
   - 安装Apache Tomcat 8.5+
   - 安装Oracle Database 11g+
   - 安装Ant 1.9+

2. **依赖配置**
   - 将JAR包放入WebContent/WEB-INF/lib/
   - 配置数据库连接信息
   - 配置日志输出路径

3. **构建部署**
   - 运行 `ant build` 构建项目
   - 运行 `ant deploy` 部署到Tomcat
   - 启动Tomcat服务器

4. **访问测试**
   - 访问 http://localhost:8080/LogisticsSystem/dashboard
   - 测试各个功能模块
   - 使用API测试页面验证REST接口

## 文件说明

### 核心配置文件
- `web.xml`: Servlet和Jersey配置
- `logback.xml`: 日志配置
- `build.xml`: Ant构建配置

### 主要功能模块
- **商品管理**: 完整的CRUD操作，支持分页和搜索
- **客户管理**: 客户信息管理，支持分页和搜索
- **库位管理**: 仓库位置管理，支持分页和搜索
- **仪表板**: 数据统计和最近记录展示

### 技术特点
- 使用原生JDBC进行数据库操作
- 支持分页查询和条件搜索
- 统一的错误处理和日志记录
- 响应式Web设计
- REST API和JSP页面双重访问方式
