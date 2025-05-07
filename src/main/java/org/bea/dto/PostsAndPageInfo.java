package org.bea.dto;

import org.bea.model.Post;

import java.util.List;

public record PostsAndPageInfo(List<Post> posts, PageOfPostsResponse pageOfPosts) {
}
