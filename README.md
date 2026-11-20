# Mix 项目生成器

给定项目名，即可生成独立的多模块 Gradle 项目。默认 Kotlin 基础包名为 `org.example.mixdemo`，可通过 `--package` 自定义。生成器使用 Python 3 标准库。生成的服务使用 Java 21、Gradle 8.5、Kotlin、Spring Boot 和 Mix，默认包含 MyBatis-Plus 数据访问、Mix Handle 写入接口和 Plugin Pro Max 文本转换示例。

## 生成项目

```bash
python3 generate.py --name mix-demo --zip
```

项目写入 `generated/mix-demo/`，`--zip` 同时生成 `generated/mix-demo.zip`。生成器会拒绝覆盖已有的同名输出。可以用 `--output-dir` 指定其他父目录。

| 参数 | 用途 | 默认值 |
| --- | --- | --- |
| `--name` | 项目名、Spring 应用名和可执行 JAR 名 | 必填 |
| `--package` | Kotlin 基础包名和对应的源码路径 | `org.example.mixdemo` |
| `--group` | Gradle 发布组 | 同 `--package` |
| `--version` | 项目版本 | `0.1.0-SNAPSHOT` |
| `--mix-version` | Mix 版本目录插件版本 | `1.3.3` |
| `--port` | 默认 HTTP 端口 | `8080` |
| `--db-name` | 本地 MySQL 数据库名 | 项目名中的连字符改为下划线 |
| `--output-dir` | 生成项目的父目录 | `generated` |
| `--zip` | 同时生成包含项目目录的 ZIP | 关闭 |

## 生成后的项目结构

```text
mix-demo/
├── api/          DTO、动作枚举和业务错误码
├── repository/   MySQL 实体、Mapper 和建表 SQL
├── service/      业务服务、Mix Handle 和文本转换插件
├── endpoint/     HTTP 接口
├── server/       Spring Boot 启动类与 dev、beta、prod 配置
├── gradle/       Gradle Wrapper
└── README.md     数据库初始化、启动命令和接口示例
```

安装 Java 21 后可在生成的项目目录执行 `./gradlew clean build`。启动和测试 MySQL 接口的步骤见生成项目内的 `README.md`。
