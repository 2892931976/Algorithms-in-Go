// GENERATED BY THE COMMAND ABOVE; DO NOT EDIT
// This file was generated by swaggo/swag at
// 2020-02-04 23:02:36.3025152 +0800 CST m=+0.136900601

package docs

import (
	"github.com/swaggo/swag"
)

var doc = `{
    "swagger": "2.0",
    "info": {
        "description": "go-admin",
        "title": "go-admin",
        "termsOfService": "https://github.com/hequan2017/go-admin",
        "contact": {
            "name": "hequan",
            "url": "https://github.com/hequan2017",
            "email": "hequan2011@sina.com"
        },
        "license": {
            "name": "MIT",
            "url": "https://github.com/hequan2017/go-admin/blob/master/LICENSE"
        },
        "version": "1.5.1"
    },
    "host": "127.0.0.1:8000",
    "paths": {
        "/api/v1/menus": {
            "get": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "menu"
                ],
                "summary": "获取所有菜单",
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            },
            "post": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "menu"
                ],
                "summary": "增加菜单",
                "parameters": [
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.Menu"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/api/v1/menus/": {
            "put": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "menu"
                ],
                "summary": "更新菜单",
                "parameters": [
                    {
                        "type": "string",
                        "description": "id",
                        "name": "id",
                        "in": "path",
                        "required": true
                    },
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.Menu"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            },
            "delete": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "menu"
                ],
                "summary": "删除菜单",
                "parameters": [
                    {
                        "type": "string",
                        "description": "id",
                        "name": "id",
                        "in": "path",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/api/v1/roles": {
            "get": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "role"
                ],
                "summary": "获取所有角色",
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            },
            "post": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "role"
                ],
                "summary": "增加角色",
                "parameters": [
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.Role"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/api/v1/roles/": {
            "put": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "role"
                ],
                "summary": "更新角色",
                "parameters": [
                    {
                        "type": "string",
                        "description": "id",
                        "name": "id",
                        "in": "path",
                        "required": true
                    },
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.Role"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            },
            "delete": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "role"
                ],
                "summary": "删除角色",
                "parameters": [
                    {
                        "type": "string",
                        "description": "id",
                        "name": "id",
                        "in": "path",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/api/v1/userInfo": {
            "get": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "users"
                ],
                "summary": "获取登录用户信息",
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {\"lists\":\"\"}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    },
                    "400": {
                        "description": "Bad Request",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/api/v1/users": {
            "get": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "users"
                ],
                "summary": "获取所有用户",
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    },
                    "400": {
                        "description": "Bad Request",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            },
            "post": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "users"
                ],
                "summary": "增加用户",
                "parameters": [
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.User"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    },
                    "400": {
                        "description": "Bad Request",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/api/v1/users/": {
            "put": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "users"
                ],
                "summary": "更新用户",
                "parameters": [
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.User"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    },
                    "400": {
                        "description": "Bad Request",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            },
            "delete": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "users"
                ],
                "summary": "删除用户",
                "parameters": [
                    {
                        "type": "integer",
                        "description": "id",
                        "name": "id",
                        "in": "path",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        },
        "/auth": {
            "post": {
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "tags": [
                    "auth"
                ],
                "summary": "获取登录token 信息",
                "parameters": [
                    {
                        "description": "body",
                        "name": "body",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "type": "object",
                            "$ref": "#/definitions/models.AuthSwag"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "{ \"code\": 200, \"data\": {}, \"msg\": \"ok\" }",
                        "schema": {
                            "type": "string"
                        }
                    },
                    "400": {
                        "description": "Bad Request",
                        "schema": {
                            "type": "string"
                        }
                    }
                }
            }
        }
    },
    "definitions": {
        "models.AuthSwag": {
            "type": "object",
            "properties": {
                "password": {
                    "type": "string"
                },
                "username": {
                    "type": "string"
                }
            }
        },
        "models.Menu": {
            "type": "object",
            "properties": {
                "created_on": {
                    "type": "integer"
                },
                "deleted_on": {
                    "type": "integer"
                },
                "id": {
                    "type": "integer"
                },
                "method": {
                    "type": "string"
                },
                "modified_on": {
                    "type": "integer"
                },
                "name": {
                    "type": "string"
                },
                "path": {
                    "type": "string"
                },
                "type": {
                    "type": "string"
                }
            }
        },
        "models.Role": {
            "type": "object",
            "properties": {
                "created_on": {
                    "type": "integer"
                },
                "deleted_on": {
                    "type": "integer"
                },
                "id": {
                    "type": "integer"
                },
                "menu": {
                    "type": "array",
                    "items": {
                        "$ref": "#/definitions/models.Menu"
                    }
                },
                "modified_on": {
                    "type": "integer"
                },
                "name": {
                    "type": "string"
                }
            }
        },
        "models.User": {
            "type": "object",
            "properties": {
                "created_on": {
                    "type": "integer"
                },
                "deleted_on": {
                    "type": "integer"
                },
                "id": {
                    "type": "integer"
                },
                "modified_on": {
                    "type": "integer"
                },
                "password": {
                    "type": "string"
                },
                "role": {
                    "type": "array",
                    "items": {
                        "$ref": "#/definitions/models.Role"
                    }
                },
                "username": {
                    "type": "string"
                }
            }
        }
    }
}`

type s struct{}

func (s *s) ReadDoc() string {
	return doc
}
func init() {
	swag.Register(swag.Name, &s{})
}
