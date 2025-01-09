package exercise;

import exercise.component.UserProperties;
import exercise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private final List<User> users = Data.getUsers();

    // BEGIN
    @Autowired
    private UserProperties userProperties;

    @GetMapping("/admins")
    public List<String> getAdmins() {
        List<String> adminEmails = userProperties.getAdmins();

        return users.stream()
                .filter(user -> adminEmails.contains(user.getEmail()))
                .map(User::getName)
                .sorted()
                .toList();
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
