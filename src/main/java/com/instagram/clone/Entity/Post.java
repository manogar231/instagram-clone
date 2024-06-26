package com.instagram.clone.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "Post")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "Post_id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "User_id")
    public User user;

    @Column(name = "Image_Url")
    public String imageUrl;

    @Column(name = "Caption")
    public String caption;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Comment> comments;

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
