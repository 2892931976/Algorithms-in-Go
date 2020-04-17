package service

import (
//    "fmt"
    "log"

    "github.com/casbin/casbin"
    // xormadapter "github.com/casbin/xorm-adapter"
    gormadapter "github.com/casbin/gorm-adapter"
  //  "github.com/gin-gonic/gin"
    //    _ "github.com/go-sql-driver/mysql"
    _ "github.com/mattn/go-sqlite3"
)

var Enforcer *casbin.Enforcer

// 初始化casbin
func CasbinSetup() {
        // 要使用自己定义的数据库rbac_db,最后的true很重要.默认为false,使用缺省的数据库名casbin,不存在则创建
    a, err := gormadapter.NewAdapter("sqlite3", "./test.db") 
    if err != nil {
        log.Printf("连接数据库错误: %v", err)
        return
    }
    e, err := casbin.NewEnforcer("conf/rbac_models.conf", a)
    if err != nil {
        log.Printf("初始化casbin错误: %v", err)
        return
    }

    Enforcer = e
}
