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
		 
		String title, category, desc, day, due_date;
		int importance=0;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[추가하기]\n"
				+ "제목 > ");
		
		title = sc.next().trim() ;
		sc.nextLine();
		if (l.isDuplicate(title)) {
			System.out.println("중복된 제목은 사용할 수 없습니다.\n");
			return;
		}

		System.out.print("카테고리 > ");
		category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine();

		System.out.print("마감요일 > ");
		day = sc.nextLine();
		
		System.out.print("마감일자 > ");
		due_date = sc.next().trim();

		System.out.print("중요도 > ");
		importance = sc.nextInt();
		
		TodoItem t = new TodoItem(title, category, desc, day, due_date, importance);
		if(l.addItem(t)>0)
			System.out.println("추가되었습니다.\n");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[삭제하기]\n"
				+ "번호 > ");
		int index = sc.nextInt();
		sc.nextLine();
		
		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다.\n");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[수정하기]\n"
				+ "번호 > ");
		int index = sc.nextInt();
		
		System.out.print("새로운 제목 > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새로운 카테고리 > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새로운 설명 > ");
		String new_description = sc.nextLine();
		
		System.out.print("새로운 마감요일 > ");
		String new_due_day = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새로운 마감일자 > ");
		String new_due_date = sc.next().trim();
		sc.nextLine();
		
		System.out.print("새로운 중요도 > ");
		int new_importance = sc.nextInt();
		
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_day, new_due_date, new_importance);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.\n");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
		System.out.println();
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
		System.out.println();
	}

	public static void listAll(TodoList l) {
		System.out.println("\n[전체 목록, 총 " + l.getCount() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
		System.out.println();
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("\n[전체 목록, 총 " + l.getCount() + "개]");
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
		System.out.println("총 " + count + "개의 카테고리가 등록되어 있습니다.\n");
	}
	
	public static void listCompAll(TodoList l) {
		int count=0;
		for(TodoItem item : l.getCompleted()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println();
		System.out.println("총 " + count + "개의 항목이 완료되어 있습니다.\n");
	}
	
	public static void compList(TodoList l, int index) {
		int importance=0, is_completed=0;
		String title="";
		String category="";
		String desc="";
		String day="";
		String due_date="";
		String current_date="";
		
		for(TodoItem item : l.getList()) {
			if(item.getId()==index) {
				title = item.getTitle();
				category = item.getCategory();
				desc = item.getDesc();
				day = item.getDay();
				due_date = item.getDue_date();
				current_date = item.getCurrent_date();
				is_completed=item.getIs_completed();
				importance=item.getImportance();
			}
		}
		if(title.equals("")) {System.out.println("해당하는 항목이 없습니다.\n");return;}
		else if(is_completed==1) {System.out.println("이미 완료체크된 항목입니다.\n");return;}
		TodoItem t = new TodoItem(index, title, category, desc, day, due_date, current_date, 1, importance);
		if(l.updateItem(t)>0)
			System.out.println("완료 체크하였습니다.\n");
	}
	
	public static void compListCancel(TodoList l, int index) {
		int importance=0, is_completed=1;
		String title="";
		String category="";
		String desc="";
		String day="";
		String due_date="";
		String current_date="";
		
		for(TodoItem item : l.getList()) {
			if(item.getId()==index) {
				title = item.getTitle();
				category = item.getCategory();
				desc = item.getDesc();
				day = item.getDay();
				due_date = item.getDue_date();
				current_date = item.getCurrent_date();
				is_completed=item.getIs_completed();
				importance=item.getImportance();
			}
		}
		
		if(title.equals("")) {System.out.println("해당하는 항목이 없습니다.\n");return;}
		else if(is_completed==0) {System.out.println("완료체크된 항목이 아닙니다.\n");return;}
		TodoItem t = new TodoItem(index, title, category, desc, day, due_date, current_date, 0, importance);
		if(l.updateItem(t)>0)
			System.out.println("완료 체크를 취소하였습니다.\n");
	}
}
