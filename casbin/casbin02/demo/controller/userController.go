package controller

import (
    "casbin02/demo/models"
    "casbin02/demo/utils"
    "casbin02/demo/utils/e"
    "log"
    "net/http"
    "strconv"
    "strings"

    "github.com/astaxie/beego/validation"
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
// @Router /api/v1/users/ [post]
func (u *UserInfo) Add(c *gin.Context) {
    data := make(map[string]interface{})

    mobile := c.Query("mobile")
    password := c.Query("password")
    realname := strings.TrimSpace(c.Query("realname"))
    gender := c.DefaultQuery("gender", "1")

    valid := validation.Validation{}
    valid.Required(mobile, "mobile").Message("请输入手机号码")
    valid.Mobile(mobile, "mobile").Message("不是有效的手机号码")
    valid.Required(password, "password").Message("请输入密码")
    valid.MinSize(password, 6, "password").Message("密码至少为6位")
    valid.MaxSize(realname, 20, "realname").Message("姓名最多不能超过20个字")

    code := e.INVALID_PARAMS
    if !valid.HasErrors() {
        user, err := new(models.Users).GetByMobile(mobile)
        if err != nil {
            log.Printf("手机查找用户错误: %v", err)
            code = e.ERROR
        } else if user.Id < 1 {
            user.Mobile = mobile
            user.RealName = realname
            user.Gender, _ = strconv.Atoi(gender)
            user.Status = 10

            // 获取salt 和 加密密码
            user.Salt = utils.RandomString(23)
            // 两次加密
            user.Password = utils.SHA256(utils.Md5(password) + user.Salt)

            if err := user.Add(); err != nil {
                log.Printf("添加用户失败: %v", err)
                code = e.ERROR
            } else {
                data["user"] = user
                code = e.SUCCESS
            }
        }
    } else {
        for _, err := range valid.Errors {
            log.Printf("err.key: %s, err.message: %s", err.Key, err.Message)
        }
    }

    c.JSON(http.StatusOK, gin.H{
        "code": code,
        "msg":  e.GetMsg(code),
        "data": data,
    })
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
    data := make(map[string]interface{})

    strId := c.Param("id")
    id, _ := strconv.Atoi(strId)
    realname := strings.TrimSpace(c.Query("realname"))
    gender, _ := strconv.Atoi(c.Query("gender"))

    valid := validation.Validation{}
    valid.Required(strId, "id").Message("id不能为空")
    valid.Min(id, 1, "id").Message("id必须大于0")
    valid.MaxSize(realname, 20, "realname").Message("姓名最多不能超过20个字")
    valid.Range(gender, 0, 1, "gender").Message("性别只能是0或者1")

    code := e.INVALID_PARAMS

    if !valid.HasErrors() {
        user, err := new(models.Users).GetById(int64(id))
        if err != nil {
            log.Printf("编辑用户错误1: %v", err)
            code = e.ERROR
        } 

        if user.Id > 0 {
            user.RealName = realname
            user.Gender = gender
            if err := user.Update("realname", "gender"); err != nil {
                log.Printf("编辑用户错误2: %v", err)
                code = e.ERROR
            } else {
                data["user"] = user
                code = e.SUCCESS
            }
        }
    } else {
        for _, err := range valid.Errors {
            log.Printf("err.key: %s. err.message: %s", err.Key, err.Message)
        }
    }

    c.JSON(http.StatusOK, gin.H{
        "code": code,
        "msg":  e.GetMsg(code),
        "data": data,
    })
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
    strId := c.Param("id")
    id, _ := strconv.Atoi(strId)

    valid := validation.Validation{}
    valid.Required(strId, "id").Message("id不能为空")
    valid.Min(id, 1, "id").Message("id必须大于0")

    code := e.INVALID_PARAMS

    if !valid.HasErrors() {
        user, err := new(models.Users).GetById(int64(id))
        if err != nil {
            log.Printf("删除用户错误1: %v", err)
            code = e.ERROR
        }
        if user.Id > 0 {
            if err := user.Delete(); err != nil {
                log.Printf("删除用户错误2: %v", err)
                code = e.ERROR
            } else {
                code = e.SUCCESS
            }
        }
    } else {
        for _, err := range valid.Errors {
            log.Printf("err.key: %s, err.message: %s", err.Key, err.Message)
        }
    }

    c.JSON(http.StatusOK, gin.H{
        "code": code,
        "msg":  e.GetMsg(code),
        "data": make(map[string]interface{}),
    })
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
    code := e.INVALID_PARAMS

    strId := c.Param("id")
    id, _ := strconv.Atoi(strId)

    valid := validation.Validation{}
    valid.Required(strId, "id").Message("id不能为空")
    valid.Min(id, 1, "id").Message("id必须大于0")

    if !valid.HasErrors() {
        user, err := new(models.Users).GetById(int64(id))
        if err != nil {
            log.Printf("获取用户信息错误: %v", err)
            code = e.ERROR
        } else {
            data["user"] = user
            code = e.SUCCESS
        }
    } else {
        for _, err := range valid.Errors {
            log.Printf("err.key: %s, err.message: %s", err.Key, err.Message)
        }
    }

    c.JSON(http.StatusOK, gin.H{
        "code": code,
        "msg":  e.GetMsg(code),
        "data": data,
    })
}

// @Summary 获取用户列表
// @Description 获取用户列表
// @Accept  json
// @Produce  json
// @Success 200 {string} string "OK"
// @Router /api/v1/users/ [get]
func (u *UserInfo) GetUsers(c *gin.Context) {
    data := make(map[string]interface{})

    code := e.SUCCESS

    userList, err := new(models.Users).FindAll()
    if err != nil {
        log.Printf("获取用户列表错误: %v", err)
        code = e.ERROR
    }

    data["user_list"] = userList

    c.JSON(http.StatusOK, gin.H{
        "code": code,
        "msg":  e.GetMsg(code),
        "data": data,
    })
}
