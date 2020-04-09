package main

//处理粘包
import (
	_ "bufio"
	"fmt"
	"io"
	"net"

	"../jiejue_nianbao"
)

func main() {
	listen, err := net.Listen("tcp", "127.0.0.1:30000")
	if err != nil {
		fmt.Println("建立服务器监听失败:", err)
		return
	}
	defer listen.Close()

	for {
		conn, errs := listen.Accept()
		if errs != nil {
			fmt.Println("建立连接失败: ", errs)
			continue
		}
		go process(conn)
	}
}

func process(conn net.Conn) {
	defer conn.Close()
	//reader := bufio.NewReader(conn)
	for {
		decode, errr := jiejue_nianbao.Decode(conn)
		if errr == io.EOF {
			break
		}
		if errr != nil || decode == "" {
			fmt.Println("读取客户端数据失败: ", errr)
			break
		}
		fmt.Printf("收到client发来的数据: %s\n", decode)
	}
}
