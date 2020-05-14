package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.controller.CommonController;
import app.model.Todo;
import app.repository.TodoRepository;



@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;
	
	
	public List<Todo> findAll() {
		return todoRepository.findAll();
	}
	public List<Todo> findAll(Integer limit) {
		return todoRepository.findAll();
//        return Optional.ofNullable(limit)
//                       .map(value -> todoRepository.findAll(PageRequest.of(0, value)).getContent())
//                       .orElseGet(() -> todoRepository.findAll());
    }
	// Todoを追加
	public Todo add(Todo todo) {
		String now = CommonController.getNow();
		todo.setUser_id(1);
		todo.setCreate_date(now);
		todo.setCreate_user_id(1);
		int status = 0;
		if (!CommonController.compareDate(now, todo.getStart_date())) {
			status = 0;
		} else if (CommonController.compareDate(now, todo.getStart_date()) && !CommonController.compareDate(now, todo.getEnd_date())) {
			status = 1;
		} else if (CommonController.compareDate(now, todo.getEnd_date())) {
			status = 2;
		}
		todo.setStatus(status);
		return todoRepository.save(todo);
	}
	// 削除
	public Boolean updateDelFlg(Long todo_id) {
		String now = CommonController.getNow();
		int user_id = 1;
		if (todoRepository.updateDelFlg(todo_id, user_id, now) > 0) {
			return true;
		} else {
			return false;
		}
	}
	// Todoを編集
	public Optional<Todo> findById(Long todo_id) {
		return todoRepository.findById(todo_id);
	}
	// Todoを編集
	public Boolean put(Todo todo) {
		String now = CommonController.getNow();
		todo.setUpdate_date(now);
		todo.setUpdate_user_id(1);
		int status = 0;
		if (!CommonController.compareDate(now, todo.getStart_date())) {
			status = 0;
		} else if (CommonController.compareDate(now, todo.getStart_date()) && !CommonController.compareDate(now, todo.getEnd_date())) {
			status = 1;
		} else if (CommonController.compareDate(now, todo.getEnd_date())) {
			status = 2;
		}
		todo.setStatus(status);
		if (todoRepository.updateAll(todo) > 0) {
			return true;
		} else {
			return false;
		}
	}
	// Todoのステータスを更新
	public String updateStatus(Long todo_id) {
		String now = CommonController.getNow();
		int user_id = 1;
		if (todoRepository.updateStatus(todo_id, user_id, now) > 0) {
			return "true";
		} else {
			return "false";
		}
	}
}
