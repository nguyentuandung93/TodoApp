package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="t_todos")
@Data
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int todo_id;
	private int user_id;
	private String title;
	private String content;
	private int status;
	private String start_date;
	private String end_date;
	private int del_flg;
	private int create_user_id;
	private String create_date;
	private int update_user_id;
	private String update_date;
	
}
