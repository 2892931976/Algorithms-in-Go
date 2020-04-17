package middleware

import (
    "casbin02/demo/service"
    "fmt"

    "github.com/gin-gonic/gin"
)

//拦截器
func Authorize() gin.HandlerFunc {

    return func(c *gin.Context) {
        // var e *casbin.Enforcer
        e := service.Enforcer
        //从DB加载策略
        e.LoadPolicy()

        //获取请求的URI
        obj := c.Request.URL.RequestURI()
        //获取请求方法
        act := c.Request.Method
        //获取用户的角色
        sub := "admin"

        //判断策略中是否存在
        if ok, _ := e.Enforce(sub, obj, act); ok {
            fmt.Println("恭喜您,权限验证通过")
            c.Next()
        } else {
            fmt.Println("很遗憾,权限验证没有通过")
            c.Abort()
        }
    }
}
