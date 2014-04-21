import java.util.ArrayList;
import java.util.Collections;
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


public class PostTest  extends UnitTest
{
  private User bob;
  
  @Before
  public void setup()
  {
    bob = new User("bob", "jones", "bob@jones.com", "secret");
    Post aPost = new Post ("Post A", "This is the post A content");
    Post bPost = new Post ("Post B", "This is the post B content");
    Post cPost = new Post ("Post C", "This is the post C content");
    bob.addPost(aPost);
    bob.addPost(bPost);
    bob.addPost(cPost);
    bob.save();
  }
  
  @After
  public void teardown()
  {
    bob.delete();
  }
  
  @Test
  public void testReversePosts()
  {
    User user = User.findByEmail("bob@jones.com");
    List<Post> posts = user.posts;
    assertEquals("Post A", posts.get(0).title);
    assertEquals("Post B", posts.get(1).title);
    assertEquals("Post C", posts.get(2).title);
    
    Collections.reverse(posts);
    assertEquals("Post C", posts.get(0).title);
    assertEquals("Post B", posts.get(1).title);
    assertEquals("Post A", posts.get(2).title);
  }

  @Test
  public void copyAndReversePosts()
  {
    User user = User.findByEmail("bob@jones.com");
    List<Post> posts = user.posts;
    ArrayList<Post> postsCopy = new ArrayList<Post>(posts);
    
    Collections.reverse(postsCopy);
    
    assertEquals("Post C", postsCopy.get(0).title);
    assertEquals("Post B", postsCopy.get(1).title);
    assertEquals("Post A", postsCopy.get(2).title);
    
    assertEquals("Post A", posts.get(0).title);
    assertEquals("Post B", posts.get(1).title);
    assertEquals("Post C", posts.get(2).title);
  }
  

}