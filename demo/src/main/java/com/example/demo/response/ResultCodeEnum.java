package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 请求失败!  客户端请求的语法错误，服务器无法理解
     */
    FAIL(400, "请求失败!"),

    /**
     * 未认证（签名错误） 请求要求用户的身份认证
     */
    UNAUTHORIZED(401, "未认证!"),

    /**
     * <p>请求错误,拒绝访问请求错误,拒绝访问!</p>
     * <p>服务器理解请求客户端的请求，但是拒绝执行此请求</p>
     */
    FORBIDDEN(403, "请求错误,拒绝访问!"),

    /**
     * 接口不存在 服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
     */
    NOT_FOUND(404, "接口不存在!"),

    /**
     * 资源不存在 客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
     */
    GONE(410, "资源不存在!"),

    /**
     * 参数错误,缺少必要的参数服务器无法根据客户端请求的内容特性完成请求
     */
    NOT_ACCEPTABLE(406, "参数错误,缺少必要的参数!"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器错误!");

    /**
     * 状态码
     */
    @Getter
    @Setter
    private int code;

    /**
     * 请求消息
     */
    @Getter
    @Setter
    private String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
