package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(a -> authorMapper.map(a)).toList();
    }

    public AuthorDTO getAuthorById(Long id) {
        return authorMapper.map(
                authorRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Author with id " + id + "not found")
                )
        );
    }

    public AuthorDTO create(AuthorCreateDTO dto) {
        var savedAuthor = authorRepository.save(
                authorMapper.map(dto)
        );

        return authorMapper.map(savedAuthor);
    }

    public AuthorDTO updateAuthor(AuthorUpdateDTO dto, long id) {
        var author = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author with id " + id + "not found")
        );

        authorMapper.update(dto, author);
        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public void deleteAuthorById(long id) {
        authorRepository.deleteById(id);
    }
}
