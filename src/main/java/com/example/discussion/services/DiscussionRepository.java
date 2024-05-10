package com.example.discussion.services;

import com.example.discussion.dto.ContentsDTO;
import com.example.discussion.dto.PostsDTO;
import com.example.discussion.dto.UsersDTO;
import com.example.discussion.entities.Comments;
import com.example.discussion.entities.Posts;
import com.example.discussion.entities.Users;

/** Interface pour la conversion entre les entités et les DTOs ***/
public interface DiscussionRepository {

    UsersDTO convertUserEntityToDTO(Users user);

    Users convertDTOToUserEntity(UsersDTO userDto);

    PostsDTO convertPostEntityToDTO(Posts post);

    Posts convertDTOToPostEntity(Users user, ContentsDTO contentsDTO);

    ContentsDTO convertCommentEntityToDTO(Comments comments);

    Comments convertDTOToCommentEntity(Users user, Posts posts, ContentsDTO contentsDTO);
}
