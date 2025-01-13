package exercise.controller;

import exercise.dto.CommentDTO;
import exercise.dto.PostDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getPosts() {
        var allPosts = postRepository.findAll();

        return allPosts.stream().map(this::toPostDTO).toList();
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable long id) {
        var getPost = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post with id " + id + " not found")
        );

        return toPostDTO(getPost);
    }

    private PostDTO toPostDTO(Post post) {
        var postDto = new PostDTO();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        postDto.setComments(
                commentRepository.findByPostId(post.getId()).stream()
                        .map(this::toCommentDto)
                        .toList()
        );

        return postDto;
    }

    private CommentDTO toCommentDto(Comment comment) {
        var commentDto = new CommentDTO();

        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }
}
// END
