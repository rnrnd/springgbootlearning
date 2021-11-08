package com.example.demo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode()
@Getter
@ApiModel(value = "响应对象", description = "统一响应")
public class CommonResult<T> {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;


    public CommonResult(){}
    public CommonResult(T data) {
        this.data = data;
    }
    public CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    @ApiOperation(value="请求成功",notes="请求成功",response=CommonResult.class)
    public static<T> CommonResult<T> success(T data) {
        CommonResult<T> commonResult = new CommonResult<T>(data);
        commonResult.code = ResultCodeEnum.SUCCESS.getCode();
        commonResult.message = ResultCodeEnum.SUCCESS.getMessage();
        return commonResult;
    }
    @ApiOperation(value="请求失败",notes="客户端请求的语法错误，服务器无法理解",response=CommonResult.class)
    public static<T> CommonResult<T> fail() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.FAIL.getCode();
        commonResult.message = ResultCodeEnum.FAIL.getMessage();
        return commonResult;
    }
    @ApiOperation(value="未认证",notes="认证（签名错误） 请求要求用户的身份认证",response=CommonResult.class)
    public static<T> CommonResult<T> unauthorized() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.UNAUTHORIZED.getCode();
        commonResult.message = ResultCodeEnum.UNAUTHORIZED.getMessage();
        return commonResult;
    }
    @ApiOperation(value="请求错误,拒绝访问!",notes="服务器理解请求客户端的请求，但是拒绝执行此请求",response=CommonResult.class)
    public static<T> CommonResult<T> forbidden() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.FORBIDDEN.getCode();
        commonResult.message = ResultCodeEnum.FORBIDDEN.getMessage();
        return commonResult;
    }
    @ApiOperation(value="接口不存在",notes="服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置\"您所请求的资源无法找到\"的个性页面",response=CommonResult.class)
    public static<T> CommonResult<T> notFound() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.NOT_FOUND.getCode();
        commonResult.message = ResultCodeEnum.NOT_FOUND.getMessage();
        return commonResult;
    }
    @ApiOperation(value="资源不存在",notes="客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置",response=CommonResult.class)
    public static<T> CommonResult<T> gone() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.GONE.getCode();
        commonResult.message = ResultCodeEnum.GONE.getMessage();
        return commonResult;
    }
    @ApiOperation(value="参数错误",notes="参数错误,缺少必要的参数服务器无法根据客户端请求的内容特性完成请求",response=CommonResult.class)
    public static<T> CommonResult<T> notAcceptable() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.NOT_ACCEPTABLE.getCode();
        commonResult.message = ResultCodeEnum.NOT_ACCEPTABLE.getMessage();
        return commonResult;
    }
    @ApiOperation(value="服务器错误",notes="服务器内部错误",response=CommonResult.class)
    public static<T> CommonResult<T> internalServerError() {
        CommonResult<T> commonResult = new CommonResult<T>();
        commonResult.code = ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode();
        commonResult.message = ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage();
        return commonResult;
    }
}
