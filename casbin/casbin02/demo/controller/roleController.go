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

type Roles struct{}

// @Summary 新角色
// @Description 新角色
// @Accept  json
// @Produce  json
// @Param name query string true "name"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need name!!"
// @Router /api/v1/roles/ [post]
func (r *Roles) Add(c *gin.Context) {
    name := strings.TrimSpace(c.Query("name"))

    valid := validation.Validation{}
    valid.Required(name, "name").Message("角色名字不能为空")
    valid.MinSize(name, 2, "name").Message("角色名字不能少于2个字符")

    code := e.INVALID_PARAMS

    if !valid.HasErrors() {
        role := models.Roles{Name: name}
        if err := role.Add(); err != nil {
            log.Printf("新增角色错误: %v", err)
            code = e.ERROR
        } else {
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
        "data": make(map[string]interface{}),
    })
}

// @Summary 修改角色信息
// @Description 修改角色信息
// @Accept  json
// @Produce  json
// @Param Id query int64 true "Id"
// @Param name query string true "name"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need Id!!"
// @Failure 404 {string} string "Can not find Id"
// @Router /api/v1/roles/:id [patch]
func (r *Roles) Edit(c *gin.Context) {
    data := make(map[string]interface{})
    code := e.INVALID_PARAMS

    strId := c.Param("id")
    id, _ := strconv.Atoi(strId)
    name := strings.TrimSpace(c.Query("name"))

    valid := validation.Validation{}
    valid.Required(strId, "id").Message("id不能为空")
    valid.Min(id, 1, "id").Message("id不能小于0")
    valid.Required(name, "name").Message("角色名称不能为空")

    if !valid.HasErrors() {
        role, err := new(models.Roles).GetById(int64(id))
        if err != nil {
            log.Printf("编辑角色错误1: %v", err)
            code = e.ERROR
        }

        if role.RoleId > 0 {
            role.Name = name
            if err := role.Update("name"); err != nil {
                log.Printf("编辑角色错误2: %v", err)
                code = e.ERROR
            } else {
                data["role"] = role
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

// @Summary 删除角色
// @Description 删除角色
// @Accept  json
// @Produce  json
// @Param Id query int64 true "Id"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need Id!!"
// @Failure 404 {string} string "Can not find Id"
//@Router /api/v1/roles/:id [delete]
func (r *Roles) Delete(c *gin.Context) {
    strId := c.Param("id")
    id, _ := strconv.Atoi(strId)

    valid := validation.Validation{}
    valid.Required(strId, "id").Message("id不能为空")
    valid.Min(id, 1, "id").Message("id不能小于0")

    code := e.INVALID_PARAMS

    if !valid.HasErrors() {
        role, err := new(models.Roles).GetById(int64(id))
        if err != nil {
            log.Printf("删除角色错误1: %v", err)
            code = e.ERROR
        }
        if role.RoleId > 0 {
            if err := role.Delete(); err != nil {
                log.Printf("删除角色错误2: %v", err)
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

// @Summary 查看角色信息
// @Description 查看角色信息
// @Accept  json
// @Produce  json
// @Param Id query int64 true "Id"
// @Success 200 {string} string "OK"
// @Failure 400 {string} string "We need Id!!"
// @Failure 404 {string} string "Can not find Id"
//@Router /api/v1/roles/:id [get]
func (r *Roles) GetRole(c *gin.Context) {
    h := utils.Gin{C: c}
    data := make(map[string]interface{})

    strId := c.Param("id")
    id, _ := strconv.Atoi(strId)

    valid := validation.Validation{}
    valid.Required(strId, "id").Message("id不能为空")
    valid.Min(id, 1, "id").Message("id不能小于0")

    code := e.INVALID_PARAMS

    if valid.HasErrors() {
        utils.MarkErrors(valid.Errors)
        h.Response(http.StatusOK, e.INVALID_PARAMS, data)
        return
    }

    role, err := new(models.Roles).GetById(int64(id))
    if err != nil {
        log.Printf("查看角色信息错误1: %v", err)
        code = e.ERROR
    }
    if role.RoleId > 0 {
        data["role"] = role
        code = e.SUCCESS
    }

    h.Response(http.StatusOK, code, data)
}

// @Summary 获取角色列表
// @Description 获取角色列表
// @Accept  json
// @Produce  json
// @Success 200 {string} string "OK"
// @Router /api/v1/roles/ [get]
func (r *Roles) GetRoles(c *gin.Context) {
    h := utils.Gin{C: c}
    data := make(map[string]interface{})

    code := e.SUCCESS

    roleList, err := new(models.Roles).FindAll()
    if err != nil {
        log.Printf("获取角色列表错误: %v", err)
        code = e.ERROR
    }

    data["list"] = roleList

    h.Response(http.StatusOK, code, data)
}
