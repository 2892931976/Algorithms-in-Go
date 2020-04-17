package utils

//公共方法
import (
    "reflect"
    "crypto/md5" 
    "crypto/sha256"
    "encoding/hex"
    "bytes"
    "math/rand"
    "time"
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

// 返回SHA256加密
func SHA256(s string) string {
    h := sha256.New()
    h.Write([]byte(s))
    rs := hex.EncodeToString(h.Sum(nil))
    return rs
}

//生成随机字符串(大写字母)
func RandomString(len int) string {
    var result bytes.Buffer
    var temp string
    for i := 0; i < len; {
        if string(RandomInt(65, 90)) != temp {
            temp = string(RandomInt(65, 90))
            result.WriteString(temp)
            i++
        }
    }
    return result.String()
}

//生成随机数字
func RandomInt(min int, max int) int {
    rand.Seed(time.Now().UTC().UnixNano())
    return min + rand.Intn(max-min)
}

func Md5(str string) string  {
    h := md5.New()
    h.Write([]byte(str))
    return hex.EncodeToString(h.Sum(nil))
}
