package utils

//公共方法
import (
    "reflect"
)

// 结构体转映射
func Struct2Map(obj interface{}) map[string]interface{} {
    t := reflect.TypeOf(obj)
    v := reflect.ValueOf(obj)

    var data = make(map[string]interface{})
    for i := 0; i < t.NumField(); i++ {
        data[t.Field(i).Name] = v.Field(i).Interface()
    }
    return data
}
