package com.dzone.albanoj2.zeal.persistence.article;

import java.util.List;
import java.util.Optional;

import com.dzone.albanoj2.zeal.domain.Article;

/**
 * Interacts with {@link Article} objects stored in the persistence layer.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public interface ArticleRepository {

	/**
	 * Finds an article by its ID if it exists.
	 * 
	 * @param id
	 *        The ID of the desired article.
	 * 
	 * @return
	 *         An {@link Optional} populated with the article matching the
	 *         supplied ID if it exists; an empty {@link Optional} otherwise.
	 */
	public Optional<Article> findById(long id);

	/**
	 * Finds all existing articles.
	 * 
	 * @return
	 *         A list of all existing articles.
	 */
	public List<Article> findAll();

	/**
	 * Saves an article. If the supplied article has an ID, this operation is
	 * treated like an update. If the supplied article has no ID, this operation
	 * is treated like a creation.
	 * 
	 * @param article
	 *        The article to save.
	 * 
	 * @return
	 *         The saved article.
	 */
	public Article save(Article article);

	/**
	 * Deletes the article associated with the supplied ID. If the article does
	 * not exist, nothing is done. Regardless of whether the article exists,
	 * upon successful completion of this method, it can be assumed an article
	 * with the supplied ID does not exist.
	 * 
	 * @param id
	 *        The ID of the article to delete.
	 */
	public void deleteById(long id);
}
