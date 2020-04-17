package controller

import (
    "net/http"

    "github.com/gin-gonic/gin"
)

type UserInfo struct{}

// @Summary 新用户
// @Description 新用户
// @Accept  json
// @Produce  json
// @Param mobile query string true "mobile"
// @Param password query string true "password"
// @Param realname query string false "realname"
// @Param gender query string false "gender"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need mobile or password!!"
// @Router /api/v1/users [post]
func (u *UserInfo) Add(c *gin.Context) {

}

// @Summary 修改用户信息
// @Description 修改用户信息
// @Accept  json
// @Produce  json
// @Param Id query int64 true "Id"
// @Param realname query string false "realname"
// @Param gender query string false "gender"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need Id!!"
// @Failure 404 {string} string "Can not find Id"
// @Router /api/v1/users/:id [patch]
func (u *UserInfo) Edit(c *gin.Context) {

}

// @Summary 删除用户
// @Description 删除用户
// @Accept  json
// @Produce  json
// @Param Id query int64 true "Id"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need Id!!"
// @Failure 404 {string} string "Can not find Id"
//@Router /api/v1/users/:id [delete]
func (u *UserInfo) Delete(c *gin.Context) {

}

// @Summary 查看用户信息
// @Description 查看用户信息
// @Accept  json
// @Produce  json
// @Param Id query int64 true "Id"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need Id!!"
// @Failure 404 {string} string "Can not find Id"
//@Router /api/v1/users/:id [get]
func (u *UserInfo) GetUser(c *gin.Context) {
    data := make(map[string]interface{})

    id := c.Param("id")

    data["id"] = id

    c.JSON(http.StatusOK, gin.H{
        "code": 200,
        "msg":  "success",
        "data": data,
    })
}

// @Summary 获取用户列表
// @Description 获取用户列表
// @Accept  json
// @Produce  json
// @Success 200 {string} string "OK"
// @Router /api/v1/users [get]
func (u *UserInfo) GetUsers(c *gin.Context) {

}
