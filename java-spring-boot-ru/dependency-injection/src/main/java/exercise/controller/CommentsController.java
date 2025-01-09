package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id " + id + " not found")
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment body) {
        return commentRepository.save(body);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment body) {
        var comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment with id " + id + " not found")
        );

        comment.setBody(body.getBody());

        return commentRepository.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
// END
