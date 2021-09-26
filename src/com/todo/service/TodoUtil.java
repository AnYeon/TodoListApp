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
				+ "========== 목록에 추가\n"
				+ "카테고리을 입력하세요");
		
		category= sc.next();
		
		System.out.println("제목을 입력하세요");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목은 중복될 수 없습니다.");
			return;
		}
		desc = sc.nextLine();
		
		System.out.println("내용을 입력하세요");
		desc = sc.nextLine();
		
		System.out.println("마감일자를 입력하세요");
		due_date= sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String ans;
		System.out.println("\n"
				+ "========== 목록 내용 일부 제거\n"
				+ "제거할 아이템의 번호를 입력하세요");
		int number= sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if ((l.getList().indexOf(item)+1)==number) {
				System.out.println((l.getList().indexOf(item)+1)+"."+"[" + item.getCategory() +"] " 
						+item.getTitle()+" - "+ item.getDesc()+" - " + item.getDue_date()+" - "+item.getCurrent_date());
				System.out.print("위 항목을 삭제하시겠습니까? (y/n)");
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
				+ "========== 부분을 편집\n"
				+ "최신화할 아이템의 번호를 입력하세요");
		int number= sc.nextInt();
		if (number>l.getList().size()||number<=0) {
			System.out.println("해당 번호에 해당하는 아이템이 목록에 존재하지 않습니다.");
			return;
		}
		System.out.println("바꿀 새로운 카테고리를 입력하세요.");
		String new_category = sc.next().trim();
		
		System.out.println("바꿀 새로운 제목을 입력하세요.");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		
		String  new_description = sc.nextLine();
		System.out.println("새로운 내용을 입력하세요");
		new_description = sc.nextLine().trim();
		
		System.out.println("마감일자를 입력하세요");
		String new_due_date = sc.next().trim();
		
		for (TodoItem item : l.getList()) {
			if((l.getList().indexOf(item)+1)==number) {
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
				l.editItem(item ,t);
				System.out.println("최신화 되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("전체 목록, 총 "+ l.getList().size()+"개");
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
		System.out.println("총 "+count +" 개의 항목을 찾았습니다.");
		
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
		System.out.println("총 "+count +" 개의 항목을 찾았습니다.");
		
	}
}
