package controller

import (
    "fmt"
    "net/http"

    "github.com/gin-gonic/gin"
)

func Hello(c *gin.Context) {
    fmt.Println("Hello 接收到GET请求..")
    c.JSON(http.StatusOK, gin.H{
        "code": 200,
        "msg":  "Success",
        "data": "Hello 接收到GET请求..",
    })
}
