package main

import (
	"fmt"
	"math/rand"
)

func main() {
	var length = 20
	var tree []int

	for i := 0; i < length; i++ {
		tree = append(tree, int(rand.Intn(1000)))
	}
	fmt.Println(tree)

	// 此时的切片o可以理解为初始状态二叉树的数(qie)组(pian)表示，然后需要将这个乱序的树调整为大根堆的状态
	// 由于是从树的右下角第一个非叶子节点开始从右向左从下往上进行比较，所以可以知道是从n/2-1这个位置的节点开始算
	for i := length/2 - 1; i >= 0; i-- {
		nodeSort(tree, i, length-1)
	}

	// 次数tree已经是个大根堆了。只需每次交换根节点和最后一个节点，并减少一个比较范围。再进行一轮比较
	for i := length - 1; i > 0; i-- {
		// 如果只剩根节点和左孩子节点，就可以提前结束了
		if i == 1 && tree[0] <= tree[i] {
			break
		}
		// 交换根节点和比较范围内最后一个节点的数值
		tree[0], tree[i] = tree[i], tree[0]
		// 这里递归的把较大值一层层提上来
		nodeSort(tree, 0, i-1)
		fmt.Println(tree)
	}
}

func nodeSort(tree []int, startNode, latestNode int) {
	var largerChild int
	leftChild := startNode*2 + 1
	rightChild := leftChild + 1

	// 子节点超过比较范围就跳出递归
	if leftChild >= latestNode {
		return
	}

	// 左右孩子节点中找到较大的，右孩子不能超出比较的范围
	if rightChild <= latestNode && tree[rightChild] > tree[leftChild] {
		largerChild = rightChild
	} else {
		largerChild = leftChild
	}

	// 此时startNode节点数值已经最大了，就不用再比下去了
	if tree[largerChild] <= tree[startNode] {
		return
	}

	// 到这里发现孩子节点数值比父节点大，所以交换位置，并继续比较子孙节点，直到把大鱼捞上来
	tree[startNode], tree[largerChild] = tree[largerChild], tree[startNode]
	nodeSort(tree, largerChild, latestNode)
}
