package app.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import app.model.Todo;
import app.service.TodoService;

@Controller
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	// Todo Index画面
//	@RequestMapping(value = "/todo", method = RequestMethod.POST)
//	public String  index() {
//		return "todo/index";
//	}
	// Todoリスト
	@RequestMapping(value = "/todo/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		model.addAttribute("todoList", todoService.findAll());
		return "todo/list";
	}
	// Todoを追加
	@RequestMapping(value = "/todo/add", method= RequestMethod.GET)
	public String addTodo(Model model) {
		model.addAttribute("todo", new Todo());
		return "todo/add";
	}
	@RequestMapping(value = "/todo/add", method= RequestMethod.POST)
	public String addTodo(@ModelAttribute Todo todo) {
		return Optional.ofNullable(todoService.add(todo))
				.map(t -> "todo/success")
				.orElse("todo/failed");
	}
	// Todo編集フォーム
	@RequestMapping(value = "/todo/{todo_id}", method = RequestMethod.GET)
	public String viewTodo(@PathVariable("todo_id") String id, Model model) {
		int todo_id = Integer.parseInt(id);
		model.addAttribute("todo", todoService.findById(todo_id));
		return "todo/edit";
	}

	// Todoを完了する
	@RequestMapping(value = "/todo/done/{todo_id}", method= RequestMethod.PUT)
	public ResponseEntity<Boolean>  doneTodo(@PathVariable(value="todo_id") String id) {
		int todo_id = Integer.parseInt(id);
		Boolean result = todoService.updateStatus(todo_id);
		return ResponseEntity.ok().body(result);
	}
	// Todoを削除
	@RequestMapping(value = "/todo/{todo_id}", method= RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteTodo(@PathVariable("todo_id") String id) {
		int todo_id = Integer.parseInt(id);
		Boolean result = todoService.updateDelFlg(todo_id);
		return ResponseEntity.ok().body(result);
	}
	// Todo編集を登録
	@RequestMapping(value = "/todo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> editTodo(HttpServletRequest request) {
		Todo todo = new Todo();
		todo.setTodo_id(Integer.parseInt(request.getParameter("todo_id")));
		todo.setTitle(request.getParameter("title"));
		todo.setContent(request.getParameter("content"));
		todo.setStart_date(request.getParameter("start_date"));
		todo.setEnd_date(request.getParameter("end_date"));
		Boolean result = todoService.updateAll(todo);
		return ResponseEntity.ok().body(result);
	}
}
