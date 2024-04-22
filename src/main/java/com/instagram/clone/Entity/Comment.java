package com.instagram.clone.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "Comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "Comment_id")
    public Long id;


    @ManyToOne
    @JoinColumn(name = "post_id")
    public Post post;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "User_id")
    public User user;

    @Column(name = "Content")
    public String content;

    @Column(name = "Created_at")
    public Date createdAt;

    @Column(name = "Updated_at")
    public Date updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
