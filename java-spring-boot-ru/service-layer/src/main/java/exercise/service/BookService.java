package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(a -> bookMapper.map(a)).toList();
    }

    public BookDTO getBookById(Long id) {
        return bookMapper.map(
                bookRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Book with id " + id + "not found")
                )
        );
    }

    public BookDTO createBook(BookCreateDTO dto) {
        if(authorRepository.findById(dto.getAuthorId()).isPresent()) {
            var savedBook = bookRepository.save(
                    bookMapper.map(dto)
            );
            return bookMapper.map(savedBook);
        } else {
            throw new ConstraintViolationException("", Set.of());
        }
    }

    public BookDTO updateBook(BookUpdateDTO dto, long id) {
        var book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book with id " + id + "not found")
        );

        bookMapper.update(dto, book);
        bookRepository.save(book);

        return bookMapper.map(book);
    }

    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }
}
