package jiejue_nianbao

import (
	_ "bufio"
	"bytes"
	"encoding/binary"
	"fmt"
	"io"
	"net"
)

// Encode 将消息编码
func Encode(message string) ([]byte, error) {
	//读取消息长度，转成int32类型（占4个字节）
	var length = int32(len(message))
	var pkg = new(bytes.Buffer)
	//写入消息头
	err := binary.Write(pkg, binary.LittleEndian, length)
	if err != nil {
		return nil, err
	}
	//写入消息实体
	errs := binary.Write(pkg, binary.LittleEndian, []byte(message))
	if errs != nil {
		return nil, errs
	}
	return pkg.Bytes(), nil
}

//Decode 解码消息
func Decode(conn net.Conn) (string, error) {
	// 读取消息长度
	//lengthByte, _:= reader.Peek(4) //读取前4个字节的数据
	var buff = make([]byte, 4)
	n, err := io.ReadFull(conn, buff)
	if err != nil {
		fmt.Println("读取packetHeader error ", n, err.Error())
		return "", nil
	}

	lengthBuff := bytes.NewBuffer(buff)
	var length int32
	errw := binary.Read(lengthBuff, binary.LittleEndian, &length)
	if errw != nil {
		fmt.Println("binary.Read ", errw.Error())
		return "", errw
	}

	fmt.Println("length=", length)
	// Buffer 返回缓存中可读取的字节数

	//读取真正的消息数据
	pack := make([]byte, int(length))
	_, errf := io.ReadFull(conn, pack)
	if errf != nil {
		return "", errf
	}
	return string(pack), nil
}
