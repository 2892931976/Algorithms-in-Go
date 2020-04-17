package models

import (
    "log"

//    _ "github.com/go-sql-driver/mysql"
    _ "github.com/mattn/go-sqlite3"
//   "xorm.io/core"
//    "xorm.io/xorm"
//     "github.com/xormplus/core"
     xl "github.com/xormplus/xorm/log"
     "github.com/xormplus/xorm"
)

//数据库连接
var DB *xorm.Engine

func Setup() {
    var err error
    //数据库链接
    DB, err = xorm.NewEngine("sqlite3", "./test02.db")
    if err != nil {
        log.Printf("创建DB连接错误: %v\n", err)
        return
    }

    // 控制台打印SQL语句
    DB.ShowSQL(true)
    DB.Logger().SetLevel(xl.LOG_DEBUG)
}

