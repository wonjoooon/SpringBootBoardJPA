package com.wonjun.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Component
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Reply {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @ManyToOne
    private BoardUser writer;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bulletin bulletin;

    @CreatedDate
    private LocalDateTime writeDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;
}
