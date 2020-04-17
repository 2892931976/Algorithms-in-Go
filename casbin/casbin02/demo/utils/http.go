package utils

import (
    "casbin02/demo/utils/e"
    "log"

    "github.com/astaxie/beego/validation"
    "github.com/gin-gonic/gin"
)

// 简化response代码
type Gin struct {
    C *gin.Context
}

func (g *Gin) Response(httpCode, errCode int, data interface{}) {
    g.C.JSON(httpCode, gin.H{
        "code": errCode,
        "msg":  e.GetMsg(errCode),
        "data": data,
    })

    return
}

// 输入验证的错误处理
func MarkErrors(errors []*validation.Error) {
    for _, err := range errors {
        log.Println(err.Key, err.Message)
    }

    return
}
