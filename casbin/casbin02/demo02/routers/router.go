package routers

import (
    "casbin02/demo/controller"
    "casbin02/demo/middleware"

    "github.com/gin-gonic/gin"
)

func InitRouter() *gin.Engine {

    //获取router路由对象
    r := gin.New()

    //使用自定义拦截器中间件
    r.Use(middleware.Authorize())

    //创建请求
    r.GET("/api/v1/hello", controller.Hello)

    return r
}
