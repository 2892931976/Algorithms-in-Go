package com.chj.skiplist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Skip01 {

	private class Node{
		//�洢�����ݣ���ȻҲ�����÷���
		int data;
		//leavel������
		Node[] forword;
 
		//int index; //���������ר��Ϊ�˺��������ÿ���ӵġ�
					//�����ȫû�б�ҪΪ�˺ÿ���ȥ������Ϊһ���������ˣ���ô�����������������൱������ݽڵ�Nʱ���ܲ��ң�Ҳ��
					//�����������£�����������һ���µ�Ԫ�أ������Ԫ��ǡ����header����ĵ�һ��λ�ã���ᵼ�º�������е�
					//�Ľڵ㶼Ҫȥ�޸�һ��index�򣬴Ӷ�Ҫȥ���������������ײ㡣�������͸����
		public Node(int data, int leavel){
			this.data = data;
			this.forword = new Node[leavel];
			//this.index = index;
		}
		public String toString(){
			return "[data="+data+", height="+forword.length+"] -->";
		}
	}
	//��Ϊ��֪��������һ���ǳ�������Կռ任ʱ������ݽṹ���,
	//���������ڲ��롢ɾ������Ҫ�Ⱥ�����ߡ�
	//�����һ�������ĵĻӻ��ڴ�
	private static final int DEFAULT_LEAVEL = 3;
	//��ʼ��־�����Ҵ���������������ΪInteger.MIN_VALUE��
	private Node header;
	//������־�����Ҵ���������������ΪInteger.MAX_VALUE��
	private Node nil;
	//��ǰ�ڵ�λ��
	private Node current;// ��һ������Ϊ�����add_tail����������������
 
	private Random rd = new Random();
 
	public Skip01(){
		//�½�header��nil
		header = new Node(Integer.MIN_VALUE, DEFAULT_LEAVEL);
		nil = new Node(Integer.MAX_VALUE, DEFAULT_LEAVEL); //��������ĸ߶���Ϊ1��Ϊ�˺���ı���
		//��headerָ����һ���ڵ㣬Ҳ����nil
		for(int i = DEFAULT_LEAVEL - 1; i >= 0; i --){
			header.forword[i] = nil;
		}
		current = header;
	}
	/**
	 * ��ָ������ת��������
	 * @param data
	 */
	public void addArrayToSkipList(int[] data){
		//�Ƚ�data����������������ַ�����
		//1.��Arrays���sort����
		//2.�Լ�дһ�����������㷨
		quickSort(data);
//		System.out.println( Arrays.toString(data));
		//
		for(int d : data){
//			System.out.println(d);
			//��Ϊ�����Ѿ�����
			//����ѡ��β�巨
			add_tail(d);
		}
	}
	/**
	 * ��ָ��������ӵ�����
	 * @param data
	 */
	public void add(int data){
		Node preN = find(data);
		if(preN.data != data){ //�ҵ���ͬ�����ݵĽڵ㲻��������
			int k = leavel();
			Node node = new Node(data, k);
			//���½ڵ�node�������е�����λ�õĺ�һ��λ�ýڵ㡣ע������ĺ�һ��λ�ýڵ���ָ���£�
			// node1 --> node2  (node1 ����node2�ĺ�һ���ڵ�)
 
			dealForAdd(preN, node, preN.forword[0], k);
		}
	}
	/**
	 * ������� data, ���� data ���ڵĽڵ㣬 
	 * ���򷵻� data ��ǰ���ڵ� 
	 * @param data
	 * @return
	 */
	private Node find(int data){
		Node current = header;
		int n = current.forword.length - 1;
 
//		while(true){  //ΪʲôҪwhile(true)д����ѭ���� ��
			while(n >= 0 && current.data < data){
				if(current.forword[n].data < data){
					current = current.forword[n];
				}else if(current.forword[n].data > data){
					n -= 1;
				}else{
					return current.forword[n];
				}
			}
			return current;
//		}
	}
	/**
	 * ɾ���ڵ�
	 * @param data
	 */
	public void delete(int data){
		Node del = find(data);
		if(del.data == data){ //ȷ���ҵ��Ľڵ㲻������ǰ���ڵ�
			delForDelete(del);
		}
	}
	private void delForDelete(Node node) {
		int h = node.forword.length;
		for(int i = h - 1; i >= 0; i --){
			Node current = header;
			while(current.forword[i] != node){
				current = current.forword[i];
			}
			current.forword[i] = node.forword[i];
		}
		node = null;
	}
	/**
	 * ��β���
	 * @param data
	 */
	public void add_tail(int data) {
		Node preN = find(data);
//		System.out.println("find");
//		System.out.println(preN.toString());
//		System.out.println("find");
		if(preN.data != data){
			int k = leavel();
			Node node = new Node(data, k);
//			System.out.println("leavel");
//			System.out.println(header.toString());
//			System.out.println(current.toString());
//			System.out.println(node.toString());
//			System.out.println("leavel");
			dealForAdd(current, node, nil, k);
			
			current = node;
		}
	}
	/**
	 * ��ӽڵ��Ƕ��������ش���
	 * @param preNode������ڵ�ǰ���ڵ�
	 * @param node������ڵ�
	 * @param succNode������ڵ��̽ڵ�
	 * @param k
	 */
	private void dealForAdd(Node preNode, Node node, Node succNode, int k){ //��ʵ���������Ĳ��� k �е���ࡣ
		int l = header.forword.length;
		int h = preNode.forword.length;
 
		if(k <= h){//�������ӵĽڵ�߶Ȳ��������ڵĺ�һ���ڵ�߶�
			for(int j = k - 1; j >= 0 ; j --){
//				System.out.println("forword");
//				System.out.println(node.forword[j].toString());
//				System.out.println(preNode.forword[j].toString());
//				System.out.println("forword");
				node.forword[j] = preNode.forword[j];
				preNode.forword[j] = node;
//				System.out.println(preNode.forword[j].toString());
//				System.out.println("forword");
			}
		}else{
			//
			if(l < k){ //���header�ĸ߶ȣ�forward�ĳ��ȣ��� k С
				header.forword = Arrays.copyOf(header.forword, k); //��ʱ����ôд�ɣ����õش������û�뵽
				nil.forword = Arrays.copyOf(nil.forword, k);
				for(int i = k - 1; i >= l; i --){
					header.forword[i] = node;
					node.forword[i] = nil;
//					System.out.println("l < k");
//					System.out.println(l);
//					System.out.println(k);
//					System.out.println(i);
//					System.out.println(header.forword[i]);
//					System.out.println("l < k");
				}
			}
			Node tmp;
			for(int m = l < k ? l - 1 : k - 1; m >= h; m --){
				tmp = header;
				//�ҵ����һ��node��ǰһ��node��Ȼ�����
				while(tmp.forword[m] != null && tmp.forword[m] != succNode){
					tmp = tmp.forword[m];
				}
				node.forword[m] = tmp.forword[m];
				tmp.forword[m] = node;
			}
 
			for(int n = h - 1; n >= 0; n --){
				node.forword[n] = preNode.forword[n];
				preNode.forword[n] = node;
			}
		}
	}
	/**
	 * �����ȡ�߶�,���൱����Ӳ��������������Ĵ�����
	 * @return
	 */
	private int leavel(){
		int k = 1;
		while(rd.nextInt(2) == 1){
			k ++;
		}
		return k;
	}
 
	/**
	 * ��������
	 * @param data
	 */
	private void quickSort(int[] data){
		quickSortUtil(data, 0, data.length - 1);
	}
	private void quickSortUtil(int[] data, int start, int end){
		if(start < end){
			//�Ե�һ��Ԫ��Ϊ�ֽ���
			int base = data[start];
			int i = start;
			int j = end + 1;
			//���ִ�
			while(true){
				//����߿�ʼ����ֱ���ҵ�����base������i
				while( i < end && data[++ i] < base);
				//���ұ߿�ʼ����ֱ���ҵ�С��base������j
				while( j > start && data[-- j] > base);
				if(i < j){
					swap(data, i, j);
				}else{
					break;
				}
			}
			//���ֽ�ֵ�� j ����λ�á�
			swap(data, start, j);
			//��ݹ�
			quickSortUtil(data, start, j - 1);
			//�ҵݹ�
			quickSortUtil(data, j + 1, end);
		}
	}
	private void swap(int[] data, int i, int j){
		int t = data[i];
		data[i] = data[j];
		data[j] = t;
	}
 
	//��������  �޵�һ��
	public Map<Integer, List<Node>> lookUp(){
		Map<Integer, List<Node>> map = new HashMap<Integer, List<Node>>();
		List<Node> nodes;
		for(int i = 0; i < header.forword.length; i ++){
			nodes = new ArrayList<Node>();
			for(Node current = header; current != null; current = current.forword[i]){
//				System.out.println("lookUp");
//				System.out.println(i);
//				System.out.println(current);
//				System.out.println("lookUp");
				nodes.add(current);
			}
			map.put(i,nodes);
			System.out.println("lookUp");
			show(map);
			System.out.println("lookUp");
		}
		return map;
	}
 
	public void show(Map<Integer, List<Node>> map){
		for(int i = map.size() - 1; i >= 0; i --){
			List<Node> list = map.get(i);
			StringBuffer sb = new StringBuffer("��"+i+"��:");
			for(Iterator<Node> it = list.iterator(); it.hasNext();){
				sb.append(it.next().toString());
			}
			System.out.println(sb.substring(0,sb.toString().lastIndexOf("-->")));
		}
	}
	public static void main(String[] args) {
		Skip01 list = new Skip01();
		list.show(list.lookUp());
		int[] data = {4, 8, 16, 10, 14};
		System.out.println( Arrays.toString(data));
		list.addArrayToSkipList(data);
		list.show(list.lookUp());
		System.out.println("�ڱ��������в���15�Ľڵ��ǰ���ڵ�Ϊ��" + list.find(15));
		System.out.println("�ڱ��������в���12�Ľڵ��ǰ���ڵ�Ϊ��" + list.find(12) + "\n");
		list.add(12);
		list.add(12);
		list.add(18);
		list.show(list.lookUp());
		System.out.println("�ڱ��������в���15�Ľڵ��ǰ���ڵ�Ϊ��" + list.find(15));
		System.out.println("�ڱ��������в���12�Ľڵ��ǰ���ڵ�Ϊ��" + list.find(12) + "\n");
		list.delete(12);
		System.out.println("ɾ���ڵ�ֵΪ12�������Ϊ��");
		list.show(list.lookUp());
	}


}
