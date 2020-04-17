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
//    r := gin.New()

  //  apiv1 := r.Group("/api/v1")
    //使用自定义拦截器中间件
  //  apiv1.Use(middleware.Authorize())
  //  {
        //创建请求
    //    apiv1.GET("/hello", controller.Hello)

      //  userController := new(controller.UserInfo)
      //  apiv1.GET("/users", userController.GetUsers)
      //  apiv1.GET("/users/:id", userController.GetUser)
      //  apiv1.POST("/users", userController.Add)
      //  apiv1.PATCH("/users/:id", userController.Edit)
      //  apiv1.DELETE("/users/:id", userController.Delete)
  //  }

  //  return r

       //获取router路由对象
    r := gin.New()

    apiv1 := r.Group("/api/v1")
    //使用自定义拦截器中间件
    apiv1.Use(middleware.Authorize())
    {
        //hello测试
        apiv1.GET("/hello", controller.Hello)

        userRoutes := apiv1.Group("/users")
        {
            userController := new(controller.UserInfo)
            // 用户列表
            userRoutes.GET("/", userController.GetUsers)
            // 单个用户信息
            userRoutes.GET("/:id", userController.GetUser)
            // 新增用户
            userRoutes.POST("/", userController.Add)
            // 修改用户信息
            userRoutes.PATCH("/:id", userController.Edit)
            // 删除用户
            userRoutes.DELETE("/:id", userController.Delete)
        }

        roleRoutes := apiv1.Group("/roles")
        {
            roleController := new(controller.Roles)
            // 角色列表
            roleRoutes.GET("/", roleController.GetRoles)
            // 单个角色信息
            roleRoutes.GET("/:id", roleController.GetRole)
            // 新增角色
            roleRoutes.POST("/", roleController.Add)
            // 修改角色信息
            roleRoutes.PATCH("/:id", roleController.Edit)
            // 删除角色
            roleRoutes.DELETE("/:id", roleController.Delete)
        }
    }

    return r

}
