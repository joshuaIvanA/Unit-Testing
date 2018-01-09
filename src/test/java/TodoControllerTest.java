import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.Introduction;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.service.TodoService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Introduction.class)
public class TodoControllerTest {
    @MockBean
    private TodoService todoService;

    @LocalServerPort
    private int serverPort;

    @After
    public void tearDown(){
        BDDMockito.verifyNoMoreInteractions(this.todoService);
    }

    @Test
    public void getAllTest() throws Exception{
        //given
        List<Todo> result=new ArrayList<Todo>();
        result.add(new Todo("todo", TodoPriority.LOW));
        BDDMockito.given(todoService.getAll()).willReturn(result);

        RestAssured.get("/todos")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(Matchers.equalTo("{\"code\":200,\"message\":null,"
                        + "\"value\":[{\"name\":\"todo\",\"priority\":\"HIGH\"}]}"));

        BDDMockito.then(todoService).should().getAll();
    }

}
