package ca.sheridancollege.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Topic;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.Post;
import ca.sheridancollege.beans.PostBlock;

public class DAO {

	SessionFactory sessionFactory;
	
	public DAO()
	{
		sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
				.buildSessionFactory();
	}
	
	public DAO(boolean test)
	{
		if (test)
			sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernatetest.cfg.xml")
				.buildSessionFactory();
		else
			sessionFactory = new Configuration().configure("ca/sheridancollege/config/hibernate.cfg.xml")
			.buildSessionFactory();
	}

	// This method saves the user to the database.  Checks that the user object
	// is valid, then check if there's an existing user with that name.
	// If there is no existing user, this user is saved to the database.
	public boolean saveUser(User user) {
		if (user != null) // If the user is null, we return false
		{
			// If the username or password is null, we return false.
			if (user.getUsername() != null && user.getPassword() != null)
			{
				// If the username or password is empty, we return false.
				if (user.getUsername().isEmpty() || user.getPassword().isEmpty())
				{
					return false;
				}
				User verifiedUser = null; // Holds the potentially existing user
				
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
				Root<User> root = criteria.from(User.class);
				try {
					criteria.where(
							criteriaBuilder.equal(
										criteriaBuilder.lower(root.get("username")), user.getUsername().toLowerCase()
									             )
								  );
				} catch (Exception ex) {
					System.out.println(ex.toString());
					return false;
				}
				try {	
					// If the user exists, we return false, otherwise we create the user.
					// Getting on an empty list will throw an IndexOutOfBoundsException
					// which will be caught and used to save the user.
					verifiedUser = (User) session.createQuery(criteria).getResultList().get(0);
				} catch (IndexOutOfBoundsException ex) {
					session.save(user);
				} catch (Exception ex) {
					// Saving has failed for another reason.
					return false;
				}
				
				session.getTransaction().commit();
				session.close();
				
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public User loginUser(User user) {
		if (user != null)
		{
			User verifiedUser;
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			try {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
			Root<User> root = criteria.from(User.class);
			criteria.where(
					criteriaBuilder.and(
				criteriaBuilder.equal(criteriaBuilder.lower(root.get("username")), user.getUsername().toLowerCase()),
				criteriaBuilder.equal(root.get("password"), user.getPassword())
				)
			);
			
			// If the user exists, we return that user, otherwise we return null.
				verifiedUser = (User) session.createQuery(criteria).getResultList().get(0);
				session.getTransaction().commit();
			} catch (Exception ex) {
				verifiedUser = null;
			}
	
			session.close();
			
			return verifiedUser;
		}
		else
		{
			return null;
		}
	}

	public List<Post> getPostList() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Post> criteria = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteria.from(Post.class);
		
		List<Post> postList = (List<Post>) session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();
		
		return postList;
	}

	public Post getPostById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Post post;
		try {
			post = session.get(Post.class, id);
			session.getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
			post = null;
		}
		session.close();
		
		return post;
	}

	public boolean savePost(Post post) {
		if (post != null)
		{
			if (post.getName() != null && post.getTopic() != null)
			{
				if (!post.getName().isEmpty() && !post.getTopic().isEmpty())
				{
					Session session = sessionFactory.openSession();
					session.beginTransaction();
					Post existingPost = session.get(Post.class, post.getId());
					post.setDateEdited(new Date());
					post.setDatePosted(post.getDateEdited());
					if (existingPost != null)
					{
						existingPost.setDateEdited(post.getDateEdited());
						existingPost.setTopic(post.getTopic());
						existingPost.setName(post.getName());
					}
					else
						session.saveOrUpdate(post);
					
					session.getTransaction().commit();
					session.close();
					
					return true;
				}
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean deletePost(int id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Post existingPost = session.get(Post.class, id);
		if (existingPost != null)
		{
			if (!existingPost.getPostBlocks().isEmpty())
			{
				for (PostBlock postBlock : existingPost.getPostBlocks()) {
					session.remove(postBlock);					
				}
				existingPost.getPostBlocks().clear();
			}
			session.remove(existingPost);
		}
		else
			return false;
		
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public Topic getTopicById(int id) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Topic topic;
		try {
			topic = session.get(Topic.class, id);
			session.getTransaction().commit();
		} catch (Exception ex)
		{
			topic = null;
		}
	
		session.close();
		
		return topic;
	}

	public List<Topic> getTopicList() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Topic> criteria = criteriaBuilder.createQuery(Topic.class);
		Root<Topic> root = criteria.from(Topic.class);
		List<Topic> topicList = (List<Topic>) session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();
		
		return topicList;
	}
	
	public boolean saveTopic(Topic topic)
	{
		if (topic != null)
		{
			if (topic.getTag() != null)
			{
				if (!topic.getTag().isEmpty())
				{
					Session session = sessionFactory.openSession();
					session.beginTransaction();
					
					session.saveOrUpdate(topic);
					
					session.getTransaction().commit();
					session.close();
					
					return true;
				}
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean deleteTopic(int id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Topic existingTopic = session.get(Topic.class, id);
		if (existingTopic != null)
			session.remove(existingTopic);
		else
			return false;
		
		session.getTransaction().commit();
		session.close();
		return true;
	}
	
	public String[] getTopics() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<String> criteria = criteriaBuilder.createQuery(String.class);
		Root<Topic> root = criteria.from(Topic.class);
		criteria.select(root.get("tag"));
		String[] topics = new String[0];
		try {
			topics = ( (List<String>) session.createQuery(criteria).getResultList()).toArray(new String[0]);
			session.getTransaction().commit();
		} catch (Exception excep)
		{
			System.out.println(excep.toString());
		}
		session.close();
		
		return topics;
	}

	public PostBlock getPostBlockById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		PostBlock postBlock;
		
		try {
			postBlock = session.get(PostBlock.class, id);
			session.getTransaction().commit();
		} catch (Exception ex)
		{
			postBlock = null;
		}
		
		session.close();
		
		return postBlock;
	}
	
	public PostBlock getPostBlockById(int postId, int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		PostBlock postBlock;
		Post post = session.get(Post.class, postId);
		if (post != null)
		{
			try {
				postBlock = post.getPostBlocks().stream().filter(wb -> wb.getId() == id).findFirst().orElse(session.get(PostBlock.class, id));
				session.getTransaction().commit();
			} catch (Exception ex)
			{
				postBlock = null;
			}
		}
		else
			postBlock = null;
	
		session.close();
		
		return postBlock;
	}

	public boolean savePostBlock(int postId, PostBlock postBlock, String username) {
		if (postBlock != null)
		{
			if (postBlock.getHeader() != null && postBlock.getText() != null)
			{
				if (!postBlock.getHeader().isEmpty() && !postBlock.getText().isEmpty())
				{
					Session session = sessionFactory.openSession();
					session.beginTransaction();
					try {
						Post post = session.get(Post.class, postId);
						post.setDateEdited(new Date());
						postBlock.setDatePosted(new Date());
						PostBlock existingPostBlock = post.getPostBlocks().stream().filter(wb -> wb.getId() == postBlock.getId()).findFirst().orElse(null);
						if (existingPostBlock != null)
						{
							existingPostBlock.setHeader(postBlock.getHeader());
							existingPostBlock.setText(postBlock.getText());
							existingPostBlock.setDatePosted(postBlock.getDatePosted());
						}
						else
						{
							post.getPostBlocks().add(postBlock);
							session.save(postBlock);
						}
						
						session.saveOrUpdate(post);
					
					} catch (Exception ex)
					{
						System.out.println(ex.toString() +"\n" + ex.getMessage());
					}
					
					session.getTransaction().commit();
					session.close();
					
					return true;
				}
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean deletePostBlock(int postId, int id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Post post = session.get(Post.class, postId);
		PostBlock existingPostBlock = session.get(PostBlock.class, id);
		if (existingPostBlock != null)
		{
			post.getPostBlocks().remove(existingPostBlock);
			session.remove(existingPostBlock);
		}
		else
			return false;
		
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public List<Post> getPostListByTopic(String topic) {
		List<Post> postList = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Post> criteria = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteria.from(Post.class);
		criteria.where(criteriaBuilder.like(root.get("topic"), topic.toLowerCase()));
		try {
			postList = session.createQuery(criteria).getResultList();
			session.getTransaction().commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		session.close();
		
		return postList;
	}
}
