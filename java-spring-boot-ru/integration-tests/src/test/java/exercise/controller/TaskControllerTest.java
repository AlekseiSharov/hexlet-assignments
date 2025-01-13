package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private final List<Task> taskList = new ArrayList<>();

    public void generateData() {
        for (int i = 0; i < 5; i++) {
            taskList.add(
                    Instancio.of(Task.class)
                            .ignore(Select.field(Task::getId))
                            .supply(Select.field(Task::getTitle), () -> faker.lorem().toString())
                            .create()
            );
        }

        taskRepository.saveAll(taskList);
    }

    public void clearDb() {
        taskRepository.deleteAll();
    }

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testGetAllTasks() throws Exception {
        generateData();

        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var resp = result.getResponse().getContentAsString();

        assertThatJson(resp).isEqualTo(om.writeValueAsString(taskList));

        clearDb();
    }

    @Test
    public void testGetTaskById() throws Exception {
        Task task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().toString())
                .create();

        taskRepository.save(task);

        var result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        task = taskRepository.findById(task.getId()).get();

        assertThat(task).isEqualTo(om.readValue(result.getResponse().getContentAsString(), Task.class));
    }

    @Test
    public void testCreateTask() throws Exception {
        var testBody = new HashMap<>();

        testBody.put("tittle", "test");
        testBody.put("description", "test");

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testBody));

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().toString())
                .create();

        taskRepository.save(task);

        var testBody = new HashMap<>();

        testBody.put("tittle", "test");
        testBody.put("description", "testDescription");

        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testBody));

        mockMvc.perform(request).andExpect(status().isOk());

        var savedTask = taskRepository.findById(task.getId()).get();

        assertThat(savedTask.getTitle()).isEqualTo(testBody.get("title"));
        assertThat(savedTask.getDescription()).isEqualTo(testBody.get("description"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().toString())
                .create();

        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assert taskRepository.findById(task.getId()).isEmpty();
    }
    // END
}
