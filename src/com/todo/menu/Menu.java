package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList 관리 명령어>");
        System.out.println("add - 추가하기");
        System.out.println("del - 삭제하기");
        System.out.println("edit - 수정하기");
        System.out.println("ls - 확인하기");
        System.out.println("ls_cate - 카테고리 확인하기");
        System.out.println("find 키워드 - 검색하기");
        System.out.println("find_cate 키워드 - 카테고리 검색하기");
        System.out.println("ls_name_asc - 정렬하기(제목순)");
        System.out.println("ls_name_desc - 역정렬하기(제목순)");
        System.out.println("ls_date - 정렬하기(시간순)");
        System.out.println("ls_date_desc - 역정렬하기(시간순)");
        System.out.println("import - txt파일에서 데이터 가져오기");
        System.out.println("comp 번호 - 완료 체크하기");
        System.out.println("comp_cancel 번호 - 완료 체크 취소하기");
        System.out.println("exit - 나가기");
        System.out.println();
    }
    
    public static void prompt() {
    	System.out.print("명령어 > ");
    }
}
