package com.kh.happve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suggest")
public class Suggest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggest_id")
    private Long suggestId;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @NotBlank(message="내용을 입력해주세요")
    private String content;

    @CreationTimestamp
    private LocalDateTime regdate;
}