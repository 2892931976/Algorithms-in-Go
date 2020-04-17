package models

import (
    "casbin02/demo/utils"
    "errors"
    "log"
    "time"

    "github.com/xormplus/xorm"
)

type Roles struct {
    RoleId    int64     `xorm:"pk autoincr"`  // 主键
    RegionId  int64     `xorm:"int(11)"`      // 域id
    Name      string    `xorm:"varchar(300)"` // 名称
    Status    int       `xorm:"smallint(6)"`  // 状态：-20:逻辑删除；10:正常; 20:无效
    CreatedAt time.Time `xorm:"datetime created"`
    UpdatedAt time.Time `xorm:"datetime updated"`
}

// 构建查询
func GetRolesQS() *xorm.Session {
    return DB.Table("roles")
}

// 增加
func (r *Roles) Add() error {
    _, err := DB.Table("roles").Insert(r)
    if err != nil {
        log.Printf("增加角色错误: %v", err)
        return err
    }
    return nil
}

// 更新给定的字段
func (r *Roles) Update(cols ...string) error {
    m := make(map[string]interface{})
    data := utils.Struct2Map(r)
    for _, col := range cols {
        m[col] = data[col]
    }
    _, err := DB.Table("roles").ID(r.RoleId).Update(m)
    if err != nil {
        log.Printf("更新角色错误: %v", err)
        return err
    }
    return nil
}

// 删除(软删除)
func (r *Roles) Delete() error {
    if r.Status != -20 {
        return errors.New("传入参数错误")
    }
    _, err := DB.Table("roles").ID(r.RoleId).Cols("status").Update(r)
    if err != nil {
        log.Printf("删除角色错误: %v", err)
        return err
    }
    return nil
}

// 查找
// 通过Id查找
func (r *Roles) GetById(id int64) (*Roles, error) {
    _, err := GetRolesQS().ID(id).Get(r)
    if err != nil {
        log.Printf("查找角色错误: %v", err)
        return nil, err
    }
    return r, nil
}
