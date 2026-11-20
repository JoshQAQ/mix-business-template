dependencies {
    api(projects.repository)
    api(libs.mix.web.spring.boot.starter)
    // Handle 分发写入请求，Plugin Pro Max 按模式选择文本转换实现。
    implementation(libs.mix.business.handle.spring.boot.starter)
    implementation(libs.plugin.pro.max.spring.boot.starter)
    implementation(libs.kotlin.reflect)
}

description = "业务服务模块"
