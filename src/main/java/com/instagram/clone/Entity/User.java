package com.instagram.clone.Entity;

import com.instagram.clone.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "User_id")
    public Long id;

    @Column(name = "User_name",unique = true)
    public String userName;

    @Column(name = "Email_Address")
    public String email;

    @Column(name = "Password")
    public String password;

    @Column(name = "Profile_Picture_Url")
    public String profilePictureUrl;

    @Column(name = "Bio")
    public String bio;

    @OneToMany(mappedBy = "followedUser", cascade = CascadeType.ALL)
    public List<Follow> followers;

    @OneToMany(mappedBy = "followerUser", cascade = CascadeType.ALL)
    public List<Follow> following;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Notification> notifications;

    @Enumerated(EnumType.STRING)
    public Status status;

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
