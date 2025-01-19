package exercise.controller;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    List<AuthorDTO> show() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    AuthorDTO showAuthor(@PathVariable long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AuthorDTO create(@RequestBody @Valid AuthorCreateDTO dto) {
        return authorService.create(dto);
    }

    @PutMapping("/{id}")
    AuthorDTO update(@RequestBody @Valid AuthorUpdateDTO dto, @PathVariable long id) {
        return authorService.updateAuthor(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable long id) {
        authorService.deleteAuthorById(id);
    }
}
