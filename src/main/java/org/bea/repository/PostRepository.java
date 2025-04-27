package org.bea.repository;

import org.bea.model.Post;
import org.bea.model.User;

import java.util.List;

public interface PostRepository {

    List<Post> findAll();
}
