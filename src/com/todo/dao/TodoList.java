package com.todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "INSERT INTO list (title, category, memo, day, due_date, current_date, is_completed, importance)"
						+ " VALUES (?,?,?,?,?,?,?,?);";
			int records=0;
			while((line=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String category = st.nextToken();
				String desc = st.nextToken();
				String day = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				String is_completed = st.nextToken();
				String importance = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, category);
				pstmt.setString(3, desc);
				pstmt.setString(4, day);
				pstmt.setString(5, due_date);
				pstmt.setString(6, current_date);
				pstmt.setString(7, is_completed);
				pstmt.setString(8, importance);
				int count = pstmt.executeUpdate();
				if(count>0) records++;
				pstmt.close();
			}
			System.out.println(records + "개의 데이터를 읽었습니다!");
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int addItem(TodoItem t) {
		String sql = "INSERT INTO list (title, category, memo, day, due_date, current_date, importance)"
					+ " VALUES (?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getCategory());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getDay());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getCurrent_date());
			pstmt.setInt(7, t.getImportance());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String desc = rs.getString("memo");
				String day = rs.getString("day");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(id, title, category, desc, day, due_date, current_date, is_completed, importance);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title LIKE ? OR memo LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String desc = rs.getString("memo");
				String day = rs.getString("day");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(id, title, category, desc, day, due_date, current_date, is_completed, importance);
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void listAll(TodoList l) {
		System.out.println("\n[전체 목록, 총 " + l.getCount() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
		System.out.println();
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, category=?, memo=?, day=?, due_date=?, current_date=?, is_completed=?, importance=?"
					+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getCategory());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getDay());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getCurrent_date());
			pstmt.setInt(7, t.getIs_completed());
			pstmt.setInt(8, t.getImportance());
			pstmt.setInt(9, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteItem(int index) {
		String sql = "DELETE FROM list WHERE id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getCompleted(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed=1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String desc = rs.getString("memo");
				String day = rs.getString("day");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(id, title, category, desc, day, due_date, current_date, is_completed, importance);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String desc = rs.getString("memo");
				String day = rs.getString("day");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(id, title, category, desc, day, due_date, current_date, is_completed, importance);
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering==0) {
				sql+=" desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String desc = rs.getString("memo");
				String day = rs.getString("day");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(id, title, category, desc, day, due_date, current_date, is_completed, importance);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Boolean isDuplicate(String title) {
		PreparedStatement pstmt;
		int count=0;
		try {
			String sql = "SELECT COUNT(title) FROM list WHERE title = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("COUNT(title)");
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(count>0) return true;
		else return false;
	}
}
