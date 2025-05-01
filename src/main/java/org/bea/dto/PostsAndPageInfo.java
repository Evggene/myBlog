package org.bea.dto;

import org.bea.db.entity.PostAggregate;
import org.bea.model.Post;

import java.util.List;

public record PostsAndPageInfo(List<PostAggregate> posts, PageOfPostsResponse pageOfPosts) {
}
