package org.bea.dto;

import java.util.List;

public record PostsAndPageInfo(List<PostRequest> posts, PageOfPostsResponse pageOfPosts) {
}
