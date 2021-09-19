package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;


public class TodoUtil {
	
	public static void loadList(TodoList l, String filename) {

		try {
			BufferedReader fr = new BufferedReader(new FileReader(filename));
			try {
				String str;
				String title, desc, date;
				while ((str = fr.readLine()) != null) {
					StringTokenizer st= new StringTokenizer(str,"##");
					title=st.nextToken();
					desc=st.nextToken();
					date=st.nextToken();
					
					TodoItem t = new TodoItem(title, desc, date);
					l.addItem(t);
					
					
				}
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter fw = new FileWriter(filename, false);
			for (TodoItem item : l.getList()) {
				
				fw.write(item.toSaveString());
					
			}
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 목록에 추가\n"
				+ "제목을 입력하세요");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다.");
			return;
		}
		desc = sc.nextLine();
		
		System.out.println("내용을 입력하세요");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 목록 내용 일부 제거\n"
				+ "제거할 목록의 제목을 입력하세요");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 부분을 편집\n"
				+ "최신화할 목록의 제목을 입력하세요");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("제목이 목록에 존재하지 않습니다.");
			return;
		}

		System.out.println("바꿀 새로운 제목을 입력하세요.");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		
		String  new_description = sc.nextLine();
		System.out.println("새로운 내용을 입력하세요");
		new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("최신화 되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("{" + item.getTitle() +"} " + item.getDesc()+" - " +item.getCurrent_date());
		}
	}
}
