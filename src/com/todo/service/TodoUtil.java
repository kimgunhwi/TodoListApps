package com.todo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		 
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�߰��ϱ�]\n"
				+ "���� > ");
		
		title = sc.next().trim() ;
		sc.nextLine();
		if (l.isDuplicate(title)) {
			System.out.println("�ߺ��� ������ ����� �� �����ϴ�.\n");
			return;
		}

		System.out.print("ī�װ� > ");
		category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("���� > ");
		desc = sc.nextLine();
		
		System.out.print("�������� > ");
		due_date = sc.next().trim();

		
		TodoItem t = new TodoItem(title, category, desc, due_date);
		if(l.addItem(t)>0)
			System.out.println("�߰��Ǿ����ϴ�.\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�����ϱ�]\n"
				+ "��ȣ > ");
		int index = sc.nextInt();
		sc.nextLine();
		
		if(l.deleteItem(index)>0)
			System.out.println("�����Ǿ����ϴ�.\n");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�����ϱ�]\n"
				+ "��ȣ > ");
		int index = sc.nextInt();
		
		System.out.print("���ο� ���� > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		
		System.out.print("���ο� ī�װ� > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("���ο� ���� > ");
		String new_description = sc.nextLine();
		
		System.out.print("���ο� �������� > ");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("�����Ǿ����ϴ�.\n");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
		System.out.println();
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
		System.out.println();
	}

	public static void listAll(TodoList l) {
		System.out.println("\n[��ü ���, �� " + l.getCount() + "��]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
		System.out.println();
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("\n[��ü ���, �� " + l.getCount() + "��]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
		System.out.println();
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.println();
		System.out.println("�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n");
	}
	
	public static void listCompAll(TodoList l) {
		int count=0;
		for(TodoItem item : l.getCompleted()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println();
		System.out.println("�� " + count + "���� �׸��� �Ϸ�Ǿ� �ֽ��ϴ�.\n");
	}
	
	public static void compList(TodoList l, int index) {
		String new_title="";
		String new_category="";
		String new_description="";
		String new_due_date="";
		
		for(TodoItem item : l.getList()) {
			if(item.getId()==index) {
				new_title = item.getTitle();
				new_category = item.getCategory();
				new_description = item.getDesc();
				new_due_date = item.getDue_date();
			}
		}
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date);
		t.setId(index);
		t.setIs_completed(1);
		if(l.updateItem(t)>0)
			System.out.println("�Ϸ� üũ�Ͽ����ϴ�.\n");
	}
}
