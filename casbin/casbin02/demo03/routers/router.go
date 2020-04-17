package routers

import (
    "casbin02/demo/controller"
    "casbin02/demo/middleware"

    "github.com/gin-gonic/gin"
)

func InitRouter() *gin.Engine {

    //获取router路由对象
  //  r := gin.New()

    //使用自定义拦截器中间件
//    r.Use(middleware.Authorize())

    //创建请求
    //r.GET("/api/v1/hello", controller.Hello)

    //获取router路由对象
    r := gin.New()

    apiv1 := r.Group("/api/v1")
    //使用自定义拦截器中间件
    apiv1.Use(middleware.Authorize())
    {
        //创建请求
        apiv1.GET("/hello", controller.Hello)

        userController := new(controller.UserInfo)
        apiv1.GET("/users", userController.GetUsers)
        apiv1.GET("/users/:id", userController.GetUser)
        apiv1.POST("/users", userController.Add)
        apiv1.PATCH("/users/:id", userController.Edit)
        apiv1.DELETE("/users/:id", userController.Delete)
    }

    return r
}
