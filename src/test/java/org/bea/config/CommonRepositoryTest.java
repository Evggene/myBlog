package org.bea.config;


import org.bea.db.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonRepositoryTest extends CommonDaoTest{

    @Autowired
    protected PostRepository postRepository;

}
