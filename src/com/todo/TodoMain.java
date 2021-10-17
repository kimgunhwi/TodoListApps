package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean quit = false;
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "ls_comp":
				TodoUtil.listCompAll(l);
				break;
				
			case "find":
				String keyword = sc.next();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.next();
				TodoUtil.findCateList(l, cate);
				break;	
				
			case "comp":
				int index = sc.nextInt();
				TodoUtil.compList(l, index);
				break;
			
			case "comp_cancel":
				int indexCancel = sc.nextInt();
				TodoUtil.compListCancel(l, indexCancel);
				break;

			case "ls_name_asc":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;

			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "import":
				l.importData("todolist.txt");
				break;
				
			case "exit":
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			default:
				System.out.println("\n정해진 명령어 중에서 선택하세요. (help - 도움말)");
				System.out.println();
				break;
			}
		} while (!quit);
		sc.close();
	}
}
