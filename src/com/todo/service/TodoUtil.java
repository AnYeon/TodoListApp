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
				+ "========== ��Ͽ� �߰�\n"
				+ "������ �Է��ϼ���");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		desc = sc.nextLine();
		
		System.out.println("������ �Է��ϼ���");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== ��� ���� �Ϻ� ����\n"
				+ "������ ����� ������ �Է��ϼ���");
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
				+ "========== �κ��� ����\n"
				+ "�ֽ�ȭ�� ����� ������ �Է��ϼ���");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("������ ��Ͽ� �������� �ʽ��ϴ�.");
			return;
		}

		System.out.println("�ٲ� ���ο� ������ �Է��ϼ���.");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		
		String  new_description = sc.nextLine();
		System.out.println("���ο� ������ �Է��ϼ���");
		new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�ֽ�ȭ �Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("{" + item.getTitle() +"} " + item.getDesc()+" - " +item.getCurrent_date());
		}
	}
}
