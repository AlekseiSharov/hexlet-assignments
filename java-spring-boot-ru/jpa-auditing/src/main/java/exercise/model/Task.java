package exercise.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

// BEGIN
@Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    @CreatedDate
    LocalDate createdAt;
    @LastModifiedDate
    LocalDate updateAt;
}
// END
