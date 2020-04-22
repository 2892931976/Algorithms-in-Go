package user_service

import (
	"errors"
	"fmt"
	"github.com/casbin/casbin"
	"go-admin/models"
	"go-admin/pkg/util"
)

type User struct {
	ID       int
	Username string
	Password string
	Role     int

	CreatedBy  string
	ModifiedBy string

	PageNum  int
	PageSize int

	Enforcer *casbin.Enforcer `inject:""`
}

func (a *User) Check() (bool, error) {
	return models.CheckUser(a.Username, util.EncodeMD5(a.Password))
}

func (a *User) Add() (id int, err error) {
	menu := map[string]interface{}{
		"username": a.Username,
		"password": util.EncodeMD5(a.Password),
		"role_id":  a.Role,
	}
	username, _ := models.CheckUserUsername(a.Username)

	if username {
		return 0, errors.New("username 名字重复,请更改！")
	}

	if id, err := models.AddUser(menu); err == nil {
		return id, err
	} else {
		return 0, err
	}
}

func (a *User) Edit() error {
	data := map[string]interface{}{
		"username": a.Username,
		"password": a.Password,
		"role_id":  a.Role,
	}

	username, _ := models.CheckUserUsernameId(a.Username, a.ID)

	if username {
		return errors.New("username 名字重复,请更改！")
	}
	err := models.EditUser(a.ID, data)
	if err != nil {
		return err
	}

	return nil
}

func (a *User) Get() (*models.User, error) {

	user, err := models.GetUser(a.Username)
	if err != nil {
		return nil, err
	}

	return user, nil
}

func (a *User) GetAll() ([]*models.User, error) {
	if a.ID != 0 {
		maps := make(map[string]interface{})
		maps["deleted_on"] = 0
		maps["id"] = a.ID
		user, err := models.GetUsers(a.PageNum, a.PageSize, maps)
		if err != nil {
			return nil, err
		}
		return user, nil
	} else {
		user, err := models.GetUsers(a.PageNum, a.PageSize, a.getMaps())
		if err != nil {
			return nil, err
		}
		return user, nil
	}
}

func (a *User) Delete() error {
	err := models.DeleteUser(a.ID)
	if err != nil {
		return err
	}
	return nil
}

func (a *User) ExistByID() (bool, error) {
	return models.ExistUserByID(a.ID)
}

func (a *User) Count() (int, error) {
	return models.GetUserTotal(a.getMaps())
}

func (a *User) getMaps() map[string]interface{} {
	maps := make(map[string]interface{})
	maps["deleted_on"] = 0
	return maps
}

// LoadAllPolicy 加载所有的用户策略
func (a *User) LoadAllPolicy() error {
	users, err := models.GetUsersAll()
	if err != nil {
		return err
	}
	for _, user := range users {
		if len(user.Role) != 0 {
			err = a.LoadPolicy(user.ID)
			if err != nil {
				return err
			}
		}
	}
	fmt.Println("角色权限关系", a.Enforcer.GetGroupingPolicy())
	return nil
}

// LoadPolicy 加载用户权限策略
func (a *User) LoadPolicy(id int) error {

	user, err := models.GetUserId(id)
	if err != nil {
		return err
	}

	a.Enforcer.DeleteRolesForUser(user.Username)

	for _, ro := range user.Role {
		a.Enforcer.AddRoleForUser(user.Username, ro.Name)
	}
	fmt.Println("更新角色权限关系", a.Enforcer.GetGroupingPolicy())
	return nil
}
