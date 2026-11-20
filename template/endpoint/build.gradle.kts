dependencies {
    // HTTP 层通过 Service 查询和试算，通过 Mix Handle 分发写入。
    implementation(projects.service)
}

description = "HTTP 接口模块"
