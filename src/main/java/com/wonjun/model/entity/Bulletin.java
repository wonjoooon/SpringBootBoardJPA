package com.wonjun.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Component
@EntityListeners(AuditingEntityListener.class)
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_no")
    private int id;

    @Column(length = 100)
    private String title;

    @Column(length = 2000)
    private String content;

    @CreatedDate
    private LocalDateTime writeDate;

    @ManyToOne
    private BoardUser writer;
}
