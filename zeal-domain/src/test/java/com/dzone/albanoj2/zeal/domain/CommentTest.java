package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentTest {

	@Test
	public void givenValidData_whenSet_thenGetValueIsCorrect() {
		
		Comment comment = new Comment();
		
		long id = 100;
		long creationTime = 700;
		String content = "Some content";
		
		comment.setId(id);
		comment.setCreationTime(creationTime);
		comment.setContent(content);
		
		assertEquals(id, comment.getId());
		assertEquals(creationTime, comment.getCreationTime());
		assertEquals(content, comment.getContent());
	}
	
	@Test
	public void givenValidData_whenConstruct_thenGetValueIsCorrect() {
		
		long id = 90;
		long creationTime = 800;
		String content = "Some content";
		
		Comment comment = new Comment(id, creationTime, content);
		
		assertEquals(id, comment.getId());
		assertEquals(creationTime, comment.getCreationTime());
		assertEquals(content, comment.getContent());
	}
}
