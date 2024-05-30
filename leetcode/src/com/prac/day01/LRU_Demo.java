package com.prac.day01;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhenyuan
 * @Date: 2024/5/24
 */
public class LRU_Demo {

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> pre;
        Node<K, V> next;

        public Node(){

        }
        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    // 构建双向链表
    class DoubleLinkedList<K, V>{
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList() {
            this.head = new Node<>();
            this.tail = new Node<>();
            this.head.next = tail;
            this.tail.pre = head;
        }

        // 添加到头
        public void addHead(Node<K, V> node){
            head.next.pre = node;
            node.next = head.next;
            head.next = node;
            node.pre = head;
        }

        // 删除节点
        public void removeNode(Node<K, V> node){
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.next = null;
            node.pre = null;
        }

        // 获得最后一个节点
        public Node getLast(){
            return tail.pre;
        }
    }
    private int cacheSize;
    Map<Integer, Node<Integer, Integer>> map;
    DoubleLinkedList<Integer, Integer> doubleLinkedList;

    public LRU_Demo(int cacheSize) {
        this.cacheSize = cacheSize;
        map = new HashMap<>();
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public int get(int key){
        if(!map.containsKey(key)){
            return -1;
        }
        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);
        return node.value;
    }

    public void put(int key, int value){
        if(map.containsKey(key)){
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        }else{
            if(map.size() == cacheSize){
                Node last = doubleLinkedList.getLast();
                map.remove(last.key);
                doubleLinkedList.removeNode(last);
            }
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key, newNode);
            doubleLinkedList.addHead(newNode);
        }
    }

    public static void main(String[] args) {
        LRU_Demo lru_demo = new LRU_Demo(3);
        lru_demo.put(1,1);
        lru_demo.put(2,2);
        lru_demo.put(3,3);
        System.out.println(lru_demo.map.keySet());

        lru_demo.put(4,4);
        System.out.println(lru_demo.map.keySet());

        lru_demo.put(3,1);
        System.out.println(lru_demo.map.keySet());
        lru_demo.put(3,1);
        System.out.println(lru_demo.map.keySet());
        lru_demo.put(3,1);
        System.out.println(lru_demo.map.keySet());
        lru_demo.put(5,1);
        System.out.println(lru_demo.map.keySet());
    }
}
