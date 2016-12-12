package ca.sheridancollege.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sheridancollege.beans.Post;
import ca.sheridancollege.beans.PostBlock;
import ca.sheridancollege.beans.Topic;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.dao.DAO;

@Controller
public class HomeController {
	
	private DAO dao = new DAO();
	@RequestMapping("/")
	public String home(Model model, HttpSession session)
	{
		if (session.getAttribute("authenticated") == null)
			session.setAttribute("authenticated", false);
		User user = new User();
		model.addAttribute("user", user);
		return "home";
	}
	
	@RequestMapping(value="/register")
	public String register(Model model)
	{
		model.addAttribute("user", new User());
		return "register";
	}
	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(Model model, @ModelAttribute User user, HttpSession session) {
		if (dao.saveUser(user))
		{
			session.setAttribute("authenticated", true);
			session.setAttribute("username", user.getUsername());
			return "redirect:/displayTopics";
		}
		else
		{
			model.addAttribute("error", "That username is already in use!");
			return "register";
		}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(Model model, @ModelAttribute User user, HttpSession session) {
		User verifiedUser = dao.loginUser(user);
		if (verifiedUser != null)
		{
			session.setAttribute("authenticated", true);
			session.setAttribute("username", verifiedUser.getUsername());
			return "redirect:/displayTopics";
		}
		else
		{
			model.addAttribute("error", "Incorrect Username/Password!");
			model.addAttribute("user", new User());
			return "home";
		}
	}
	
	@RequestMapping(value="/logout")
	public String logout(Model model, HttpSession session)
	{
		User user = new User();
		session.setAttribute("authenticated", false);
		session.removeAttribute("username");
		model.addAttribute("user", user);
		return "redirect:/";
	}
	
	@RequestMapping(value="/displayPosts/{topic}")
	public String displayPosts(Model model, HttpSession session, @PathVariable String topic)
	{
		model.addAttribute("postList", dao.getPostListByTopic(topic));
		model.addAttribute("topic", topic);
		System.out.println("k");
		return "displayPosts";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/addPost")
	public String addPost(Model model, @PathVariable String topic) {
		Post post = new Post();
		model.addAttribute("post", post);
		model.addAttribute("topic", topic);
		return "addPost";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/editPost/{id}")
	public String editPost(Model model, @PathVariable int id, @PathVariable String topic) {
		Post post = dao.getPostById(id);
		model.addAttribute("post", post);
		model.addAttribute("topic", topic);
		return "addPost";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/deletePost/{id}")
	public String deletePost(Model model, @PathVariable int id, @PathVariable String topic) {
		dao.deletePost(id);
		model.addAttribute("topic", topic);
		return "redirect:/displayPosts/"+topic;
	}
	
	@RequestMapping(value="/displayPosts/{topic}/savePost", method=RequestMethod.POST)
	public String savePost(Model model, @ModelAttribute Post post, HttpSession session, @PathVariable String topic)
	{
		post.setEditedBy((String) session.getAttribute("username"));
		dao.savePost(post);
		model.addAttribute("postList", dao.getPostList());
		model.addAttribute("topic", topic);
		return "redirect:/displayPosts/"+topic;
	}
	
	@RequestMapping(value="/displayPosts/{topic}/post/{postId}")
	public String viewPost(Model model, @PathVariable int postId, HttpSession session, @PathVariable String topic)
	{
		model.addAttribute("post", dao.getPostById(postId));
		model.addAttribute("topic", topic);
		return "displayPostBlocks";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/post/{postId}/addPostBlock")
	public String addPostBlock(Model model, @PathVariable int postId, @ModelAttribute Post post, @PathVariable String topic) {
		PostBlock postBlock = new PostBlock();
		model.addAttribute("postBlock", postBlock);
		model.addAttribute("postId", postId);
		return "addPostBlock";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/post/{postId}/editPostBlock/{id}")
	public String editPostBlock(Model model, @PathVariable int postId, @ModelAttribute Post post, @PathVariable String topic, @PathVariable int id) {
		PostBlock postBlock = dao.getPostBlockById(postId, id);
		model.addAttribute("postBlock", postBlock);
		model.addAttribute("postId", postId);
		return "addPostBlock";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/post/{postId}/deletePostBlock/{id}")
	public String deletePostBlock(Model model, @PathVariable String topic, @PathVariable int postId, @PathVariable int id) {
		dao.deletePostBlock(postId, id);
		model.addAttribute("topic", topic);
		return "redirect:/displayPosts/" + topic + "/post/{postId}";
	}
	
	@RequestMapping(value="/displayPosts/{topic}/post/{postId}/savePostBlock", method=RequestMethod.POST)
	public String savePostBlock(Model model, @PathVariable String topic, @PathVariable int postId, @ModelAttribute PostBlock postBlock, HttpSession session)
	{
		postBlock.setPostedBy((String) session.getAttribute("username"));
		dao.savePostBlock(postId, postBlock, (String)session.getAttribute("username"));
		model.addAttribute("topic", topic);
		return "redirect:/displayPosts/" + topic + "/post/{postId}";
	}
	
	@RequestMapping(value="/displayTopics")
	public String displayTopics(Model model, HttpSession session)
	{
		model.addAttribute("topicList", dao.getTopicList());
		return "displayTopics";
	}
	
	@RequestMapping(value="/addTopic")
	public String addTopic(Model model) {
		Topic topic = new Topic();
		model.addAttribute("topic", topic);
		return "addTopic";
	}
	
	@RequestMapping(value="/editTopic/{id}")
	public String editTopic(Model model, @PathVariable int id) {
		Topic topic = dao.getTopicById(id);
		model.addAttribute("topic", topic);
		return "addTopic";
	}
	
	@RequestMapping(value="/deleteTopic/{id}")
	public String deleteTopic(Model model, @PathVariable int id) {
		dao.deleteTopic(id);
		return "redirect:/displayTopics";
	}
	
	@RequestMapping(value="/saveTopic", method=RequestMethod.POST)
	public String saveTopic(Model model, @ModelAttribute Topic topic, HttpSession session)
	{
		dao.saveTopic(topic);
		model.addAttribute("topicList", dao.getTopicList());
		return "redirect:/displayTopics";
	}
	
	@RequestMapping(value="/getReply/{id}", produces="application/json")
	@ResponseBody
	public Map<String, Object> getReply(@PathVariable int id)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		PostBlock postBlock = dao.getPostBlockById(id);
		data.put("postBlock", postBlock);
		return data;
		
	}
}
