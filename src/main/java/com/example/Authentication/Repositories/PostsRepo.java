package com.example.Authentication.Repositories;

import com.example.Authentication.Models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepo extends JpaRepository<Posts, Long> {

}
