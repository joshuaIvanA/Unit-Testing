import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;
import springboot.service.TodoService;

import java.util.ArrayList;
import java.util.List;

public class TodoServiceTest {
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;

    @Before
    public void setUp(){
        //instantiate TodoService
        MockitoAnnotations.initMocks(this);
        this.todoService= new TodoService(this.todoRepository);
    }

    @After
    public void tearDown(){
        BDDMockito.verifyNoMoreInteractions(this.todoRepository);
    }

    @Test
    public void getAllTest() throws Exception{
        //prep data
        //given
        //todo repo must return non-empty list when getAll is called
        List<Todo> todos= new ArrayList<Todo>();
        todos.add(new Todo("todos", TodoPriority.LOW));
        BDDMockito.given(todoRepository.getAll()).willReturn(todos);

        //call method
        List<Todo> todoList= todoService.getAll();

        //check the response
        //assert that todo list is not empty
        Assert.assertThat(todoList, Matchers.notNullValue());
        Assert.assertThat(todoList.isEmpty(), Matchers.equalTo(false));

//        if(todoList.isEmpty()) {
//            throw new Exception("list kosong");
//        }
//        else {
//            System.out.println("list berisi");
//        }

        //verify
        BDDMockito.then(todoRepository).should().getAll();
    }

    @Test
    public void saveTodoTest() throws Exception{
        //given
        BDDMockito.given(todoRepository.store(new Todo("test", TodoPriority.LOW))).willReturn(true);

        //call method
        boolean status= todoService.saveTodo("test", TodoPriority.LOW);

        //assert
        Assert.assertThat(status, Matchers.equalTo(true));

        //verify
        BDDMockito.then(todoRepository).should().store(new Todo("test", TodoPriority.LOW));
    }

}
