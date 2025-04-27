package org.bea.repository;

import org.bea.model.Post;
import org.bea.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository {

    List<Post> findAll(int offset);

    long getCount();
}
