package br.com.tinuthas.SpringKothin

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.lang.Exception
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

interface TodoRepository: JpaRepository<Todo, Long>

@RestController
@RequestMapping(value = "/todo")
@EnableWebMvc
class TodoResource(val todoReso: TodoRepository){

    @GetMapping(value = "/")
    fun getAll() = todoReso.findAll()

    @GetMapping(value = "/{id}")
    fun getOne(@PathVariable id: Long) = todoReso.findById(id)

    @PostMapping(value = "/")
    fun new(@RequestBody text: String) = todoReso.save(Todo(text = text))

    @DeleteMapping(value = "/{id}")
    fun delete(@PathVariable id: Long) = todoReso.deleteById(id)

    @PutMapping(value = "/{id}")
    fun update(@PathVariable id:Long, @RequestBody todo: Todo): Todo {
        val toUpdate: Todo = todoReso.findById(id).orElseThrow{Exception("server error")}
        toUpdate.text = todo.text
        toUpdate.done = todo.done
        return todoReso.save(toUpdate)
    }

}

@Entity
class Todo(@Id @GeneratedValue(strategy = GenerationType.AUTO)
           val id: Long = 0, var text: String = "", var done: Boolean = false, val createAt: Instant = Instant.now())