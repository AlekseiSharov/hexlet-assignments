package exercise.controller;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping
    List<BookDTO> show() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    BookDTO showAuthor(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookDTO create(@RequestBody @Valid BookCreateDTO dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    BookDTO update(@RequestBody @Valid BookUpdateDTO dto, @PathVariable long id) {
        return bookService.updateBook(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable long id) {
        bookService.deleteBookById(id);
    }
}
