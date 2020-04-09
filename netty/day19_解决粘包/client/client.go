package main

import (
	"fmt"
	"net"
	"strconv"
	_ "time"

	"../jiejue_nianbao"
)

func main() {
	conn, err := net.Dial("tcp", "127.0.0.1:30000")
	if err != nil {
		fmt.Println("连接服务器失败: ", err)
		return
	}
	defer conn.Close()

	for i := 0; i < 1000; i++ {
		msg := strconv.Itoa(i) + "广州市图吉科技有限公司"

		//调用协议编码数据
		encode, err := jiejue_nianbao.Encode(msg)
		if err != nil {
			fmt.Println("编码协议失败：", encode)
			continue
		}
		_, erre := conn.Write(encode)
		if erre != nil {
			fmt.Println("写入失败: ", erre)
			continue
		}
		//time.Sleep(time.Second)
	}

}
