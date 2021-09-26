package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	public void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("정한 순서대로 출력합니다.");
		for (TodoItem myitem : list) {
			System.out.println("["+myitem.getCategory() +"]"+myitem.getTitle() +" - "+ myitem.getDesc()+" - "
		+myitem.getDue_date() +" - "+myitem.getCurrent_date());
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
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public void makeSet() {
		int count=0;
		HashSet<String> set = new HashSet<String>();
		for (TodoItem item : list) {
			set.add(item.getCategory());
		}
		for(String category : set) {
			count++;
			if(count<set.size())
				System.out.print(category+" / ");
			else
				System.out.println(category);
		}
		System.out.print("총 "+count+"개의 카테고리가 등록되어 있습니다.");
	}
}
