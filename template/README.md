# @@PROJECT_NAME@@

Mix 业务项目脚手架示例，使用 Kotlin、Java 21、Spring Boot、Mix @@MIX_VERSION@@ 和 MyBatis-Plus。业务只有一张 `test` 表，包含自增主键 `id` 和名称 `name`。

| 模块 | 示例代码 |
| --- | --- |
| `api` | 请求、响应、动作枚举和示例错误码 |
| `repository` | `Test` 实体、`TestRepository` 和建表 SQL |
| `service` | 增删改查、Mix Handle 写入和 Mix Plugin 文本转换示例 |
| `endpoint` | `TestEndpoint` 提供查询、新增和更新接口，`TextTransformEndpoint` 提供文本转换 |
| `server` | Spring Boot 启动入口、数据源配置和打包 |

## 本地启动

准备 Java 21 和本地 MySQL，在项目根目录执行以下命令。按本地 MySQL 设置替换连接用户名和密码。

```bash
mysql -u root -p -e 'CREATE DATABASE IF NOT EXISTS `@@DATABASE_NAME@@` CHARACTER SET utf8mb4'
mysql -u root -p @@DATABASE_NAME@@ < repository/src/main/resources/db/test.sql

export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD='你的本地 MySQL 密码'
./gradlew :server:bootRun
```

数据源地址可以通过 `SPRING_DATASOURCE_URL` 覆盖。服务默认在 `@@SERVER_PORT@@` 端口启动，Swagger UI 路径为 `/swagger-ui/index.html`。示例接口无需登录。

## HTTP 示例

| 方法 | 路径 | 行为 |
| --- | --- | --- |
| GET | `/api/test?id=1` | 按 ID 查询 |
| POST | `/api/test/addTest` | 新增记录 |
| POST | `/api/test/updateTest` | 按 ID 更新 |

```bash
# 新增
curl -sS http://localhost:@@SERVER_PORT@@/api/test/addTest \
  -H 'Content-Type: application/json' \
  -d '{"name":"示例"}'

# 查询和更新时使用新增响应中 data.id 的值
curl -sS 'http://localhost:@@SERVER_PORT@@/api/test?id=1'
curl -sS http://localhost:@@SERVER_PORT@@/api/test/updateTest \
  -H 'Content-Type: application/json' \
  -d '{"id":"1","name":"更新后的示例"}'
```

`TestAction` 将各请求类绑定到 Mix Handle 动作。两个写入接口接收具体请求 DTO，并共用 Mix Handle 分发入口。`name` 必须为非空白文本且最多 100 个字符，更新必须提供正整数 `id`，响应中的 `data.id` 为字符串。查询或更新不存在的记录返回 HTTP 404。删除只在 `TestService` 中演示。

`./gradlew clean build` 构建后，可执行 JAR 位于 `server/build/libs/@@PROJECT_NAME@@.jar`。

如需为 Mix 组件指定 Maven 仓库，可通过 `-PmixRepositoryUrl=https://你的仓库地址` 同时覆盖插件和依赖的优先解析仓库。

## Mix Plugin 文本转换

`TextTransformPluginConfiguration` 用 `@EnablePluginProMax` 注册 `TextTransformPlugin`。`TextTransformService` 用 `PluginRegistry` 按 `mode` 选择实现。`text` 须为非空白文本且最多 200 个字符。

| 模式 | 转换结果 |
| --- | --- |
| `UPPERCASE` | `Hello Mix` → `HELLO MIX` |
| `LOWERCASE` | `Hello Mix` → `hello mix` |

```bash
curl -sS -G 'http://localhost:@@SERVER_PORT@@/api/text/transform' \
  --data-urlencode 'mode=UPPERCASE' \
  --data-urlencode 'text=Hello Mix'
```

转换结果见 `data.text`。新增模式时，添加一个实现 `TextTransformPlugin` 的 `@Component` 并声明唯一的 `mode`。未注册的模式返回 HTTP 400，重复的键会在应用启动时被拒绝。
