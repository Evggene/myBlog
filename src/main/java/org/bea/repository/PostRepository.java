package org.bea.repository;

import org.bea.model.Post;

import java.util.List;

public interface PostRepository {

    List<Post> findAll(int offset);

    long getCount();
}
