package app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import app.model.Todo;
import app.service.TodoService;


@Controller
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	// Todo Index画面
	@RequestMapping(value = "/todo", method = {RequestMethod.GET, RequestMethod.POST})
	public String index() {
		return "todo/index";
	}
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
	// Todoを削除
	@RequestMapping(value = "/todo/delete/{todo_id}", method= RequestMethod.GET)
	public String deleteTodo(@PathVariable("todo_id") Long todo_id) {
		return Optional.ofNullable(todoService.updateDelFlg(todo_id))
				.map(t -> "todo/success")
				.orElse("todo/failed");
	}
	
	// Todoを編集
	@RequestMapping(value = "/todo/edit")
	public String editTodo(@RequestParam(value="todo_id") Long todo_id, Model model) {
		model.addAttribute("todo", todoService.findById(todo_id));
		return "todo/edit";
	}
	@RequestMapping(value = "/todo/put", method= RequestMethod.POST)
	public String putTodo(@ModelAttribute Todo todo) {
		return Optional.ofNullable(todoService.put(todo))
				.map(t -> "todo/success")
				.orElse("todo/failed");
	}
	// Todoを完了する
	@RequestMapping(value = "/todo/done", method= RequestMethod.POST)
	@ResponseBody
	public String  doneTodo(@RequestParam(value="todo_id") Long todo_id, Model model) {
		String result = todoService.updateStatus(todo_id);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
