package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Dto.PostDto;
import com.instagram.clone.Entity.Comment;
import com.instagram.clone.Entity.Like;
import com.instagram.clone.Entity.Post;
import com.instagram.clone.Entity.User;
import com.instagram.clone.Repository.CommentRepository;
import com.instagram.clone.Repository.LikeRepository;
import com.instagram.clone.Repository.PostRepository;
import com.instagram.clone.Repository.UserRepository;
import com.instagram.clone.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Object doPost(PostDto postDto) {
        // Validate the postDto fields
        if (postDto.getImageUrl() == null || postDto.getCaption() == null || postDto.getUsername() == null) {
            return "Failed to create post: Image URL, caption, and username are required.";
        }

        // Retrieve the user by username
        Optional<User> optionalUser = userRepository.findByuserName(postDto.getUsername());
        if (optionalUser.isEmpty()) {
            return "Failed to create post: User with the provided username does not exist.";
        }

        try {
            // Create a new post entity
            Post post = Post.builder()
                    .imageUrl(postDto.getImageUrl())
                    .caption(postDto.getCaption())
                    .user(optionalUser.get())
                    .build();

            // Save the post to the database
            postRepository.save(post);

            return "Post Uploaded Successfully!!";
        } catch (Exception ex) {
            return "Failed to create post: " + ex.getMessage();
        }
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public String likePost(Long postId, Long userId) {
        // Check if the post exists
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return "Failed to like post: Post not found.";
        }

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "Failed to like post: User not found.";
        }

        try {
            // Check if the user has already liked the post
            Post post = optionalPost.get();
            User user = optionalUser.get();
            if (post.getLikes().stream().anyMatch(like -> like.getUser().getId().equals(userId))) {
                return "Failed to like post: User already liked this post.";
            }

            // Create a new like entity and associate it with the post and user
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);

            // Save the like to the database
            post.getLikes().add(like);
            likeRepository.save(like);
            postRepository.save(post);

            return "Post liked successfully!";
        } catch (Exception ex) {
            return "Failed to like post: " + ex.getMessage();
        }
    }


    @Override
    public String commentOnPost(Long postId, Long userId, String content) {
        // Check if the post exists
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return "Failed to comment on post: Post not found.";
        }

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "Failed to comment on post: User not found.";
        }

        try {
            // Create a new comment entity and associate it with the post and user
            Comment comment = new Comment();
            comment.setPost(optionalPost.get());
            comment.setUser(optionalUser.get());
            comment.setContent(content);
            // Save the comment to the database
            commentRepository.save(comment);

            // Add the comment to the post's list of comments
            Post post = optionalPost.get();
            post.getComments().add(comment);

            // Save the updated post to update the association with the comment
            postRepository.save(post);

            return "Comment added successfully!";
        } catch (Exception ex) {
            return "Failed to comment on post: " + ex.getMessage();
        }
    }

}
