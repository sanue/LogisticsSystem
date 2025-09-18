#!/bin/bash

# 物流管理システム JSP版本 部署脚本

echo "=========================================="
echo "物流管理システム JSP版本 部署脚本"
echo "=========================================="

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java环境，请先安装Java 8+"
    exit 1
fi

# 检查Ant环境
if ! command -v ant &> /dev/null; then
    echo "错误: 未找到Ant环境，请先安装Ant 1.9+"
    exit 1
fi

# 检查Tomcat环境
if [ -z "$CATALINA_HOME" ]; then
    echo "错误: 未设置CATALINA_HOME环境变量"
    echo "请设置Tomcat安装目录，例如: export CATALINA_HOME=/opt/tomcat"
    exit 1
fi

if [ ! -d "$CATALINA_HOME" ]; then
    echo "错误: Tomcat目录不存在: $CATALINA_HOME"
    exit 1
fi

echo "Java版本:"
java -version

echo ""
echo "Ant版本:"
ant -version

echo ""
echo "Tomcat目录: $CATALINA_HOME"

# 检查依赖JAR包
LIB_DIR="WebContent/WEB-INF/lib"
echo ""
echo "检查依赖JAR包..."

REQUIRED_JARS=(
    "jersey-core-1.1.jar"
    "jersey-server-1.1.jar"
    "jersey-servlet-1.1.jar"
    "jsonic-1.3.10.jar"
    "logback-classic-1.2.3.jar"
    "logback-core-1.2.3.jar"
    "slf4j-api-1.7.25.jar"
    "ojdbc8.jar"
    "servlet-api-3.1.jar"
)

MISSING_JARS=()

for jar in "${REQUIRED_JARS[@]}"; do
    if [ ! -f "$LIB_DIR/$jar" ]; then
        MISSING_JARS+=("$jar")
    fi
done

if [ ${#MISSING_JARS[@]} -gt 0 ]; then
    echo "警告: 以下JAR包缺失:"
    for jar in "${MISSING_JARS[@]}"; do
        echo "  - $jar"
    done
    echo ""
    echo "请将缺失的JAR包放置到 $LIB_DIR 目录下"
    echo "详细说明请查看 $LIB_DIR/README.md"
    echo ""
    read -p "是否继续部署? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "部署已取消"
        exit 1
    fi
fi

# 清理项目
echo ""
echo "清理项目..."
ant clean

# 构建项目
echo ""
echo "构建项目..."
ant build

if [ $? -ne 0 ]; then
    echo "错误: 项目构建失败"
    exit 1
fi

# 停止Tomcat（如果正在运行）
echo ""
echo "检查Tomcat状态..."
if pgrep -f "catalina" > /dev/null; then
    echo "Tomcat正在运行，正在停止..."
    $CATALINA_HOME/bin/shutdown.sh
    sleep 5
fi

# 部署WAR文件
echo ""
echo "部署WAR文件到Tomcat..."
cp build/LogisticsSystem.war $CATALINA_HOME/webapps/

# 复制日志配置文件
echo ""
echo "复制日志配置文件..."
mkdir -p $CATALINA_HOME/logs
cp logback.xml $CATALINA_HOME/conf/

# 启动Tomcat
echo ""
echo "启动Tomcat..."
$CATALINA_HOME/bin/startup.sh

# 等待Tomcat启动
echo ""
echo "等待Tomcat启动..."
sleep 10

# 检查部署状态
echo ""
echo "检查部署状态..."
if curl -s http://localhost:8080/LogisticsSystem/dashboard > /dev/null; then
    echo "✅ 部署成功！"
    echo ""
    echo "访问地址:"
    echo "  主页: http://localhost:8080/LogisticsSystem/dashboard"
    echo "  商品管理: http://localhost:8080/LogisticsSystem/product"
    echo "  客户管理: http://localhost:8080/LogisticsSystem/customer"
    echo "  库位管理: http://localhost:8080/LogisticsSystem/location"
    echo "  API测试: http://localhost:8080/LogisticsSystem/api/test.html"
    echo ""
    echo "日志文件位置:"
    echo "  Tomcat日志: $CATALINA_HOME/logs/"
    echo "  应用日志: $CATALINA_HOME/logs/logistics-system.log"
else
    echo "❌ 部署可能失败，请检查Tomcat日志"
    echo "日志位置: $CATALINA_HOME/logs/catalina.out"
fi

echo ""
echo "部署脚本执行完成"
