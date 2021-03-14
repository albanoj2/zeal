package com.dzone.albanoj2.zeal.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An article published on Zeal. The comments added to this article must have
 * unique IDs. If a comment is added and another comment already exists with the
 * same ID, the comment is rejected.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class Article {

	private long id;
	private long creationTime;
	private String title;
	private String content;
	private List<Comment> comments;

	public Article() {
		this.comments = new ArrayList<>();
	}

	/**
	 * Creates a new article.
	 * 
	 * @param id
	 *        The ID of the article.
	 * @param creationTime
	 *        The creation time of the article.
	 * @param title
	 *        The title of the article.
	 * @param content
	 *        The content of the article.
	 * @param comments
	 *        The comments associated with the article. If the supplied comments
	 *        are {@code null}, the comments are set to an empty list.
	 */
	public Article(long id, long creationTime, String title, String content,
		List<Comment> comments) {
		this.id = id;
		this.creationTime = creationTime;
		this.title = title;
		this.content = content;
		setComments(comments);
	}

	/**
	 * Adds a new comment. The supplied comment must have a unique ID (i.e.,
	 * there must not exist a comment with the same ID in this article).
	 * 
	 * @param comment
	 *        The comment to add.
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
	}

	/**
	 * Finds a comment that matches the supplied comment ID.
	 * 
	 * @param id
	 *        The ID of the desired comment.
	 * 
	 * @return The comment associated with the supplied comment ID if a comment
	 *         with the supplied ID exists; an empty {@link Optional} otherwise.
	 */
	public Optional<Comment> findCommentById(long id) {
		return comments.stream()
			.filter(comment -> comment.getId() == id)
			.findFirst();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments for this article.
	 * 
	 * @param comments
	 *        The comments for this article. If the supplied comments are
	 *        {@code null}, the comments are set to an empty list.
	 */
	public void setComments(List<Comment> comments) {
		this.comments = ensureNonNull(comments);
	}

	private static List<Comment> ensureNonNull(List<Comment> comments) {

		if (comments == null) {
			return new ArrayList<>();
		} 
		else {
			return comments;
		}
	}
}
