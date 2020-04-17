package main

import (
    "casbin02/demo/routers"
    "casbin02/demo/service"
)

func init() {
    service.CasbinSetup()
}

func main() {
    r := routers.InitRouter()
    r.Run(":9000") //参数为空 默认监听8080端口
}
