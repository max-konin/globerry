/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.utils;

/**
 *
 * @author signal
 */
public class ListNode<E> {
	
	private ListNode<E> next;
	private E data;
	
	private ListNode() {
		
	}
	
	public ListNode(E data, ListNode<E> next) {
		this.data = data;
		this.next = next;
	}
	
	public ListNode<E> next() {
		return this.next;
	}
	
	public void setNext(ListNode<E> next) {
		this.next = next;
	}
	
	public E getData() {
		return this.data;
	}
	
}
