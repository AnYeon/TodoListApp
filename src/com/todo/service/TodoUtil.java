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
				String category ,title, desc, due_date, current_date;
				while ((str = fr.readLine()) != null) {
					StringTokenizer st= new StringTokenizer(str,"##");
					category=st.nextToken();
					title=st.nextToken();
					desc=st.nextToken();
					due_date=st.nextToken();
					current_date=st.nextToken();
					
					TodoItem t = new TodoItem(category, title, desc, due_date, current_date);
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
		String category;
		String due_date;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== ��Ͽ� �߰�\n"
				+ "ī�װ��� �Է��ϼ���");
		
		category= sc.next();
		
		System.out.println("������ �Է��ϼ���");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		desc = sc.nextLine();
		
		System.out.println("������ �Է��ϼ���");
		desc = sc.nextLine();
		
		System.out.println("�������ڸ� �Է��ϼ���");
		due_date= sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String ans;
		System.out.println("\n"
				+ "========== ��� ���� �Ϻ� ����\n"
				+ "������ �������� ��ȣ�� �Է��ϼ���");
		int number= sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if ((l.getList().indexOf(item)+1)==number) {
				System.out.println((l.getList().indexOf(item)+1)+"."+"[" + item.getCategory() +"] " 
						+item.getTitle()+" - "+ item.getDesc()+" - " + item.getDue_date()+" - "+item.getCurrent_date());
				System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n)");
				ans= sc.next();
				if(ans.equals("y"))
					l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== �κ��� ����\n"
				+ "�ֽ�ȭ�� �������� ��ȣ�� �Է��ϼ���");
		int number= sc.nextInt();
		if (number>l.getList().size()||number<=0) {
			System.out.println("�ش� ��ȣ�� �ش��ϴ� �������� ��Ͽ� �������� �ʽ��ϴ�.");
			return;
		}
		System.out.println("�ٲ� ���ο� ī�װ��� �Է��ϼ���.");
		String new_category = sc.next().trim();
		
		System.out.println("�ٲ� ���ο� ������ �Է��ϼ���.");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		
		String  new_description = sc.nextLine();
		System.out.println("���ο� ������ �Է��ϼ���");
		new_description = sc.nextLine().trim();
		
		System.out.println("�������ڸ� �Է��ϼ���");
		String new_due_date = sc.next().trim();
		
		for (TodoItem item : l.getList()) {
			if((l.getList().indexOf(item)+1)==number) {
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
				l.editItem(item ,t);
				System.out.println("�ֽ�ȭ �Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("��ü ���, �� "+ l.getList().size()+"��");
		for (TodoItem item : l.getList()) {
			System.out.println((l.getList().indexOf(item)+1)+"."+"[" + item.getCategory() +"] " 
		+item.getTitle()+" - "+ item.getDesc()+" - " + item.getDue_date()+" - "+item.getCurrent_date());
		}
	}
	
	public static void listCate(TodoList l) {
		l.makeSet();
	}
	
	public static void find(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword)||item.getDesc().contains(keyword)) {
				System.out.println((l.getList().indexOf(item)+1)+"."+"[" + item.getCategory() +"] " 
						+item.getTitle()+" - "+ item.getDesc()+" - " + item.getDue_date()+" - "+item.getCurrent_date());
				count++;
			}
		}
		System.out.println("�� "+count +" ���� �׸��� ã�ҽ��ϴ�.");
		
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int count=0;
		
		for (TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {
				System.out.println((l.getList().indexOf(item)+1)+"."+"[" + item.getCategory() +"] " 
						+item.getTitle()+" - "+ item.getDesc()+" - " + item.getDue_date()+" - "+item.getCurrent_date());
				count++;
			}
		}
		System.out.println("�� "+count +" ���� �׸��� ã�ҽ��ϴ�.");
		
	}
}
