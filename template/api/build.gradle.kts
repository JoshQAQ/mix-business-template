dependencies {
    // 向下游模块暴露 DTO 和 Mix Handle 契约所需的框架类型。
    api(libs.mix.core.api)
    api(libs.mix.common.tools)
    api(libs.mix.business.handle.autoconfigure)
    // 为 DTO 提供校验、序列化和 OpenAPI 注解。
    compileOnly(libs.hibernate.validator)
    compileOnly(libs.jackson.databind)
    compileOnly(libs.swagger.annotations.jakarta)
}

description = "接口契约模块"
