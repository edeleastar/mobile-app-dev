import java.util.ArrayList;
import java.util.List;

import models.Comment;
import models.Post;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class BlogTest  extends UnitTest
{
  private User bob;
  
  @Before
  public void setup()
  {
    bob = new User("bob", "jones", "bob@jones.com", "secret");
    bob.save();
  }
  
  @After
  public void teardown()
  {
    bob.delete();
  }
  
  @Test
  public void testCreatePost()
  {
    Post aPost = new Post ("Post Title", "This is the post content");
    bob.addPost(aPost);
    bob.save();
    
    User user = User.findByEmail("bob@jones.com");
    List<Post> posts = user.posts;
    assertEquals(1, posts.size());
    Post post = posts.get(0);
    assertEquals(post.title, "Post Title");
    assertEquals(post.content, "This is the post content");
  }
  
  
  @Test
  public void testCreateMultiplePosts()
  {
    Post post1 = new Post ("Post Title 1", "This is the first post content");
    Post post2 = new Post ("Post Title 2", "This is the second post content");
    bob.addPost(post1);
    bob.addPost(post2);
    bob.save();
    
    User user = User.findByEmail("bob@jones.com");
    List<Post> posts = user.posts;
    assertEquals(2, posts.size());
    Post posta = posts.get(0);
    assertEquals(posta.title, "Post Title 1");
    assertEquals(posta.content, "This is the first post content");
    
    Post postb = posts.get(1);
    assertEquals(postb.title, "Post Title 2");
    assertEquals(postb.content, "This is the second post content");   
   }
  
  
  @Test
  public void testDeletePost()
  {
    Post post1 = new Post ("Post Title 1", "This is the first post content");
    Post post2 = new Post ("Post Title 2", "This is the second post content");
    bob.addPost(post1);
    bob.addPost(post2);
    bob.save();
    
    User user = User.findByEmail("bob@jones.com");
    
    Post post = user.posts.get(0);
    
    user.posts.remove(post);
    user.save();
    post.delete();
    
    User anotherUser = User.findByEmail("bob@jones.com");
    assertEquals(1, anotherUser.posts.size());   
   }
  
  
  @Test
  public void testCreatePostWithComment()
  {
    Post aPost = new Post ("Post Title", "This is the post content");
    bob.addPost(aPost);
    
    Comment comment = new Comment ("This is a comment");
    aPost.addComment(comment);
    bob.save();

    
    User user = User.findByEmail("bob@jones.com");
    List<Post> posts = user.posts;
    Post thePost = posts.get(0);
    assertEquals(thePost.comments.size(), 1);
    Comment theComment = thePost.comments.get(0);
    assertEquals(theComment.content, "This is a comment");
  }
  
}
