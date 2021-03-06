// Code generated by Wire. DO NOT EDIT.

//go:generate wire
//+build !wireinject

package initialize

import (
	"github.com/LyricTian/gin-admin/internal/app/api"
	"github.com/LyricTian/gin-admin/internal/app/api/mock"
	"github.com/LyricTian/gin-admin/internal/app/bll/impl/bll"
	"github.com/LyricTian/gin-admin/internal/app/initialize/data"
	"github.com/LyricTian/gin-admin/internal/app/model/impl/gorm/model"
	"github.com/LyricTian/gin-admin/internal/app/module/adapter"
	"github.com/LyricTian/gin-admin/internal/app/router"
)

// Injectors from wire.go:

func BuildInjector() (*Injector, func(), error) {
	auther, cleanup, err := InitAuth()
	if err != nil {
		return nil, nil, err
	}
	db, cleanup2, err := InitGormDB()
	if err != nil {
		cleanup()
		return nil, nil, err
	}
	role := &model.Role{
		DB: db,
	}
	roleMenu := &model.RoleMenu{
		DB: db,
	}
	menuActionResource := &model.MenuActionResource{
		DB: db,
	}
	user := &model.User{
		DB: db,
	}
	userRole := &model.UserRole{
		DB: db,
	}
	casbinAdapter := &adapter.CasbinAdapter{
		RoleModel:         role,
		RoleMenuModel:     roleMenu,
		MenuResourceModel: menuActionResource,
		UserModel:         user,
		UserRoleModel:     userRole,
	}
	syncedEnforcer, cleanup3, err := InitCasbin(casbinAdapter)
	if err != nil {
		cleanup2()
		cleanup()
		return nil, nil, err
	}
	demo := &model.Demo{
		DB: db,
	}
	bllDemo := &bll.Demo{
		DemoModel: demo,
	}
	apiDemo := &api.Demo{
		DemoBll: bllDemo,
	}
	mockDemo := &mock.Demo{}
	menu := &model.Menu{
		DB: db,
	}
	menuAction := &model.MenuAction{
		DB: db,
	}
	login := &bll.Login{
		Auth:            auther,
		UserModel:       user,
		UserRoleModel:   userRole,
		RoleModel:       role,
		RoleMenuModel:   roleMenu,
		MenuModel:       menu,
		MenuActionModel: menuAction,
	}
	apiLogin := &api.Login{
		LoginBll: login,
	}
	mockLogin := &mock.Login{}
	trans := &model.Trans{
		DB: db,
	}
	bllMenu := &bll.Menu{
		TransModel:              trans,
		MenuModel:               menu,
		MenuActionModel:         menuAction,
		MenuActionResourceModel: menuActionResource,
	}
	apiMenu := &api.Menu{
		MenuBll: bllMenu,
	}
	mockMenu := &mock.Menu{}
	bllRole := &bll.Role{
		Enforcer:      syncedEnforcer,
		TransModel:    trans,
		RoleModel:     role,
		RoleMenuModel: roleMenu,
		UserModel:     user,
	}
	apiRole := &api.Role{
		RoleBll: bllRole,
	}
	mockRole := &mock.Role{}
	bllUser := &bll.User{
		Enforcer:      syncedEnforcer,
		TransModel:    trans,
		UserModel:     user,
		UserRoleModel: userRole,
		RoleModel:     role,
	}
	apiUser := &api.User{
		UserBll: bllUser,
	}
	mockUser := &mock.User{}
	routerRouter := &router.Router{
		Auth:           auther,
		CasbinEnforcer: syncedEnforcer,
		DemoAPI:        apiDemo,
		DemoMock:       mockDemo,
		LoginAPI:       apiLogin,
		LoginMock:      mockLogin,
		MenuAPI:        apiMenu,
		MenuMock:       mockMenu,
		RoleAPI:        apiRole,
		RoleMock:       mockRole,
		UserAPI:        apiUser,
		UserMock:       mockUser,
	}
	engine := InitGinEngine(routerRouter)
	dataMenu := &data.Menu{
		TransModel: trans,
		MenuBll:    bllMenu,
	}
	injector := &Injector{
		Engine:         engine,
		Auth:           auther,
		CasbinEnforcer: syncedEnforcer,
		Menu:           dataMenu,
	}
	return injector, func() {
		cleanup3()
		cleanup2()
		cleanup()
	}, nil
}
