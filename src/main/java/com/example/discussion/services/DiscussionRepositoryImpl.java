package com.example.discussion.services;

import com.example.discussion.dto.ContentsDTO;
import com.example.discussion.dto.PostsDTO;
import com.example.discussion.dto.UsersDTO;
import com.example.discussion.entities.Comments;
import com.example.discussion.entities.Posts;
import com.example.discussion.entities.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DiscussionRepositoryImpl implements DiscussionRepository {

    @Override
    public UsersDTO convertUserEntityToDTO(Users user) {
        return new UsersDTO(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

    @Override
    public Users convertDTOToUserEntity(UsersDTO userDto) {
        return new Users(userDto.getUsername(), userDto.getFirstname(), userDto.getLastname(), userDto.getEmail(),
                new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public PostsDTO convertPostEntityToDTO(Posts post) {
        String author = post.getUsers().getUsername() + "@" + post.getUsers().getFirstname() + "-" + post.getUsers().getLastname();
        return new PostsDTO(post.getContent(), author);
    }

    @Override
    public Posts convertDTOToPostEntity(Users user, ContentsDTO contentsDTO) {
        return new Posts(contentsDTO.getContent(), user, new ArrayList<>());
    }

    @Override
    public ContentsDTO convertCommentEntityToDTO(Comments comments) {
        return new ContentsDTO(comments.getContent());
    }

    @Override
    public Comments convertDTOToCommentEntity(Users user, Posts post, ContentsDTO contentsDTO) {
        return new Comments(contentsDTO.getContent(), user, post);
    }
}
