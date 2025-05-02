package org.bea.dto;

import org.bea.model.PostAggregate;

import java.util.List;

public record PostsAndPageInfo(List<PostAggregate> posts, PageOfPostsResponse pageOfPosts) {
}
