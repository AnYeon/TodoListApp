package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;


public class TodoUtil {

	/*public static void saveList(TodoList l, String filename) {
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
		
		
	}*/
	public static void createItem(TodoList l) {
		
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
		if (l.isDuplicate(title)) {
			System.out.printf("������ �ߺ��� �� �����ϴ�.");
			return;
		}
		desc = sc.nextLine();
		
		System.out.println("������ �Է��ϼ���");
		desc = sc.nextLine();
		
		System.out.println("�������ڸ� �Է��ϼ���");
		due_date= sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(l.addItem(t)>0) {
			System.out.println("�߰��Ǿ����ϴ�.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String ans;
		System.out.println("\n"
				+ "========== ��� ���� �Ϻ� ����\n"
				+ "������ �������� ��ȣ�� �Է��ϼ���");
		int number= sc.nextInt();
		if(l.deleteItem(number)>0) {
			System.out.println("�����Ǿ����ϴ�.");
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
		/*
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��� �� �����ϴ�.");
			return;
		}*/
		
		String  new_description = sc.nextLine();
		System.out.println("���ο� ������ �Է��ϼ���");
		new_description = sc.nextLine().trim();
		
		System.out.println("�������ڸ� �Է��ϼ���");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
		t.setId(number);
		
		if(l.updateItem(t)>0) {
			System.out.println("�ֽ�ȭ �Ǿ����ϴ�.");}
	}

	public static void listAll(TodoList l) {
		System.out.println("��ü ���, �� "+ l.getCount()+"��");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("��ü ���, �� "+ l.getCount()+"��");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listCate(TodoList l) {
		int count=0;
		for(String item: l.getCate()) {
			System.out.println(item+" ");
			count++;
		}
		System.out.printf("\n �� %d���� ī�װ��� ��ϵǾ����ϴ�.\n", count);
	}
	
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
			
		}
		System.out.println("�� "+count +" ���� �׸��� ã�ҽ��ϴ�.");
		
	}
	
	public static void find_cate(TodoList l, String keyword) {
		int count=0;
		
		for (TodoItem item : l.getListCate(keyword)) {
			System.out.println(item.toString());
			count++;
			
		}
		System.out.println("�� "+count +" ���� �׸��� ã�ҽ��ϴ�.");
		
	}
}
