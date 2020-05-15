package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{
	
	@Query(value = "SELECT todo FROM Todo todo WHERE todo.del_flg = 0 ORDER BY todo.start_date ASC")
	List<Todo> findAll();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Todo todo SET todo.del_flg = 1, todo.update_user_id = :update_user_id, todo.update_date = :update_date WHERE todo.todo_id = :todo_id")
	Integer updateDelFlg(@Param("todo_id") int todo_id, @Param("update_user_id") int user_id, @Param("update_date") String update_date);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Todo t SET t.title = :#{#todo.title}, t.content = :#{#todo.content},"
			+ "t.status = :#{#todo.status}, t.start_date = :#{#todo.start_date}, t.end_date = :#{#todo.end_date}, t.update_user_id = :#{#todo.update_user_id}, t.update_date = :#{#todo.update_date} WHERE t.todo_id = :#{#todo.todo_id}")
	Integer updateAll(@Param("todo") Todo todo);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Todo todo SET todo.status = 3, todo.update_user_id = :update_user_id, todo.update_date = :update_date WHERE todo.todo_id = :todo_id")
	Integer updateStatus(@Param("todo_id") int todo_id, @Param("update_user_id") int user_id, @Param("update_date") String update_date);
}
