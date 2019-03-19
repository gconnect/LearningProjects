package com.glory.consoleapp;

import org.w3c.dom.Node;

public class LinkedListExample {

    NodeList head;

    public void insert(int data){
        NodeList node = new NodeList();
        node.data = data;
        node.next = null;

        if(head == null){
            head = node;
        }else{
            NodeList n = head;
            while(n.next != null){
                n = n.next;
            }
            n.next = node;
        }
    }

    public void show(){
        NodeList node = head;
        while(node.next != null){
            System.out.println(node.data);
            node = node.next;
        }
        System.out.println(node.data);
    }
}
