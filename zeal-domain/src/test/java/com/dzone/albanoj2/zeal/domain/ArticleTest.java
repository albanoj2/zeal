package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class ArticleTest {
	
	@Test
	public void givenNoComments_whenFindCommentById_thenNoCommentFound() {
		
		Article article = new Article();
		
		Optional<Comment> found = article.findCommentById(1L);
		
		assertFalse(found.isPresent());
	}
	
	@Test
	public void givenMatchingComment_whenFindCommentById_thenCommentFound() {
		
		Article article = new Article();
		Comment comment = new Comment(7L, 600, "foo");
		
		article.addComment(comment);
		
		Optional<Comment> found = article.findCommentById(comment.getId());
		
		assertTrue(found.isPresent());
		assertEquals(comment, found.get());
	}
	
	@Test
	public void givenNullComments_whenFindCommentById_thenNoCommentFound() {
		
		Article article = new Article(10L, 100L, "foo", "bar", null);
		
		Optional<Comment> found = article.findCommentById(1L);
		
		assertFalse(found.isPresent());
	}
	
	@Test
	public void givenNonMatchingComment_whenFindCommentById_thenNoCommentFound() {
		
		Article article = new Article();
		Comment comment = new Comment(7L, 600, "foo");
		
		article.addComment(comment);
		
		Optional<Comment> found = article.findCommentById(comment.getId() + 1);
		
		assertFalse(found.isPresent());
	}
	
	@Test
	public void givenNullComments_whenSetComments_thenCommentsAreEmpty() {
		
		Article article = new Article();
		article.setComments(null);
		
		List<Comment> comments = article.getComments();
		
		assertNotNull(comments);
		assertTrue(comments.isEmpty());
	}
	
	@Test
	public void whenConstruct_thenCommentsAreEmpty() {
		
		Article article = new Article();
		
		List<Comment> comments = article.getComments();
		
		assertNotNull(comments);
		assertTrue(comments.isEmpty());
	}
	
	@Test
	public void whenConstructWithNullComments_thenCommentsAreEmpty() {
		
		Article article = new Article(1L, 100L, "foo", "bar", null);
		
		List<Comment> comments = article.getComments();
		
		assertNotNull(comments);
		assertTrue(comments.isEmpty());
	}

	@Test
	public void givenValidData_whenSet_thenGetValueIsCorrect() {
		
		Article article = new Article();
		Comment comment = new Comment(7L, 600, "foo");
		
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
		assertTrue(commentsInArticle.contains(comment));
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
		assertTrue(commentsInArticle.contains(comment));
	}
}
