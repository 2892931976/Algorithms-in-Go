package models

import (
    "casbin02/demo/utils"
    "errors"
    "log"
    "time"

    "github.com/xormplus/xorm"
)

type Users struct {
    Id            int64     `xorm:"pk autoincr"`      //主键ID
    RealName      string    `xorm:"varchar(128)"`     //用户真实名称
    NamePinyin    string    `xorm:"varchar(128)"`     //名字拼音
    NamePinyinPre string    `xorm:"varchar(5)"`       //名字拼音前缀
    Portrait      string    `xorm:"varchar(512)"`     //头像地址
    Mobile        string    `xorm:"varchar(50)"`      //用户手机号
    Password      string    `xorm:"varchar(128)"`     //用户密码
    Salt          string    `xorm:"varchar(128)"`     //23位盐
    Gender        int64     `xorm:"tinyint(1)"`       //用户性别
    Status        int64     `xorm:"smallint(6)"`      // 状态：-20:逻辑删除；10:正常; 20:无效
    CreateAt      time.Time `xorm:"datetime created"` //创建时间
    UpdateAt      time.Time `xorm:"datetime updated"` //最后更新时间
}

// 构建查询
func GetUsersQS() *xorm.Session {
    return DB.Table("users")
}

// 增加
func (r *Users) Add() error {
    _, err := DB.Table("users").Insert(r)
    if err != nil {
        log.Printf("增加用户错误: %v", err)
        return err
    }
    return nil
}

// 更新给定的字段
func (r *Users) Update(cols ...string) error {
    m := make(map[string]interface{})
    data := utils.Struct2Map(r)
    for _, col := range cols {
        m[col] = data[col]
    }
    _, err := DB.Table("users").ID(r.Id).Update(m)
    if err != nil {
        log.Printf("更新用户错误: %v", err)
        return err
    }
    return nil
}

// 删除(软删除)
func (r *Users) Delete() error {
    if r.Status != -20 {
        return errors.New("传入参数错误")
    }
    _, err := DB.Table("users").ID(r.Id).Cols("status").Update(r)
    if err != nil {
        log.Printf("删除用户错误: %v", err)
        return err
    }
    return nil
}

// 查找
// 通过Id查找
func (r *Users) GetById(id int64) (*Users, error) {
    _, err := GetUsersQS().ID(id).Get(r)
    if err != nil {
        log.Printf("查找用户错误: %v", err)
        return nil, err
    }
    return r, nil
}
