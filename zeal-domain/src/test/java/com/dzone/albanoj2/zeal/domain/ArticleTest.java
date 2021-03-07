package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ArticleTest {

	@Test
	public void givenValidData_whenSet_thenGetValueIsCorrect() {
		
		Article article = new Article();
		Comment comment = new Comment((long) 7, 600, "foo");
		
		long id = 70;
		long creationTime = 500;
		String title = "An Example Title";
		String content = "Some article content";
		List<Comment> comments = Arrays.asList(comment);
		
		article.setId(id);
		article.setCreationTime(creationTime);
		article.setTitle(title);
		article.setContent(content);
		article.setComments(comments);
		
		List<Comment> commentsInArticle = article.getComments();
		
		assertEquals(id, article.getId());
		assertEquals(creationTime, article.getCreationTime());
		assertEquals(title, article.getTitle());
		assertEquals(content, article.getContent());
		assertNotNull(commentsInArticle);
		assertEquals(1, commentsInArticle.size());
		assertCommentFound(comment, commentsInArticle);
	}

	private static void assertCommentFound(Comment comment, List<Comment> comments) {
		
		boolean foundComment = comments.stream()
			.filter(comment::equals)
			.findAny()
			.isPresent();
		
		assertTrue(foundComment);
	}

	@Test
	public void givenValidData_whenConstruct_thenGetValueIsCorrect() {
		
		Comment comment = new Comment((long) 7, 600, "foo");
		
		long id = 70;
		long creationTime = 500;
		String title = "An Example Title";
		String content = "Some article content";
		List<Comment> comments = Arrays.asList(comment);
		
		Article article = new Article(id, creationTime, title, content, comments);
		
		List<Comment> commentsInArticle = article.getComments();
		
		assertEquals(id, article.getId());
		assertEquals(creationTime, article.getCreationTime());
		assertEquals(title, article.getTitle());
		assertEquals(content, article.getContent());
		assertNotNull(commentsInArticle);
		assertEquals(1, commentsInArticle.size());
		assertCommentFound(comment, commentsInArticle);
	}
}
