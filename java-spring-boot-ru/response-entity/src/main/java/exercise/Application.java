package exercise;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        return ResponseEntity.of(
                posts.stream()
                        .filter(post ->post.getId().equals(id))
                        .findFirst()
        );
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> post(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.created(URI.create("/posts/" + post.getId())).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> putPost(@PathVariable String id, @RequestBody Post post) {
        var getPosts = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if(getPosts.isPresent()) {
            getPosts.get().setTitle(post.getTitle());
            getPosts.get().setBody(post.getBody());
            return ResponseEntity.ok(post);
        }
        return ResponseEntity.noContent().build();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}