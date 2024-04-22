package com.instagram.clone.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.AnnotatedArrayType;

@Data
@Builder
@Entity
@Table(name = "Notification")
@AllArgsConstructor
@NoArgsConstructor
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "Id")
    public Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "Type")
    public String type;

    @ManyToOne
    @JoinColumn(name = "related_user_id")
    public User relatedUser;

    @Column(name = "Read")
    public boolean read;
}
