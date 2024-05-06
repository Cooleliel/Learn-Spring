package com.example.discussion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Subscriber {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column @NotEmpty
    private String username;
    @OneToMany(mappedBy = "subscriber", fetch = FetchType.LAZY)
    @Column
    private Collection<Post> postsList;
}
