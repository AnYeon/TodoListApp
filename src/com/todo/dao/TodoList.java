package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;
import com.todo.service.DbConnect;

public class TodoList {
	private List<TodoItem> list;
	static Connection conn;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}

	public int getCount() {
		int count = 0;

		try {
			Statement stmt;
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)"
	+ "values (?,?,?,?,?);";
		int count = 0;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			count = pstmt.executeUpdate();
			
			pstmt.close();
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?" + "where id= ?;";
		int count = 0;

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			//pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;

	}

	public int deleteItem(int number) {
		String sql = "delete from list where id=?;";
		int count = 0;
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	public void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();

		try {
			Statement stmt;
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);

			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list Where title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);

			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("정한 순서대로 출력합니다.");
		for (TodoItem myitem : list) {
			System.out.println("[" + myitem.getCategory() + "]" + myitem.getTitle() + " - " + myitem.getDesc() + " - "
					+ myitem.getDue_date() + " - " + myitem.getCurrent_date());
		}
	}

	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public void sortByDateDesc() {
		Collections.sort(list, new TodoSortByDateDesc());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getTitle()))
				return true;
		}
		return false;
	}

	public ArrayList<String> getCate() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;

		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCate(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;

		try {

			String sql = "SELECT * FROM list WHERE category= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;

		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;

			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(category, title, description, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static void importData(String filename) {
		try {
			String sql1 = "SELECT * FROM list";
			Statement stmt;
			
			stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			
			if (rs.next()==false) {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String sql2 = "insert into list (title, memo, category, current_date, due_date)"
						+ " values (?,?,?,?,?);";
				int records = 0;

				String line;
				String category, title, desc, due_date, current_date;
				while ((line = br.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line, "##");
					category = st.nextToken();
					title = st.nextToken();
					desc = st.nextToken();
					due_date = st.nextToken();
					current_date = st.nextToken();

					PreparedStatement pstmt = conn.prepareStatement(sql2);

					pstmt.setString(1, title);
					pstmt.setString(2, desc);
					pstmt.setString(3, category);
					pstmt.setString(4, current_date);
					pstmt.setString(5, due_date);
					int count = pstmt.executeUpdate();
					if (count > 0)
						records++;
					pstmt.close();
				}
				System.out.println(records + " records read!!");
				br.close();
				stmt.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
