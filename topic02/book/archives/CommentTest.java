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


public class CommentTest  extends UnitTest
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
  public void testAddComment()
  {
    User user = User.findByEmail("bob@jones.com");
    Post post = user.posts.get(1);
    Comment comment1 = new Comment("Comment 1");
    Comment comment2 = new Comment("Comment 2");
    Comment comment3 = new Comment("Comment 3");
    post.addComment(comment1);
    post.addComment(comment2);
    post.addComment(comment3);
    user.save();
    
    User anotherUser = User.findByEmail("bob@jones.com");
    assertEquals("Comment 1", anotherUser.posts.get(1).comments.get(0).content);
    assertEquals("Comment 2", anotherUser.posts.get(1).comments.get(1).content);    
    assertEquals("Comment 3", anotherUser.posts.get(1).comments.get(2).content);
  }

  @Test
  public void testDeleteComment()
  {
    User user = User.findByEmail("bob@jones.com");
    Post post = user.posts.get(1);
    Comment comment1 = new Comment("Comment 1");
    Comment comment2 = new Comment("Comment 2");
    Comment comment3 = new Comment("Comment 3");
    post.addComment(comment1);
    post.addComment(comment2);
    post.addComment(comment3);
    user.save();
    
    User anotherUser = User.findByEmail("bob@jones.com");
    Post anotherPost = anotherUser.posts.get(1);
    Comment comment = anotherPost.comments.remove(1);
    user.save();
    comment.delete();
    
    User yetAnotherUser = User.findByEmail("bob@jones.com");
    assertEquals("Comment 1", yetAnotherUser.posts.get(1).comments.get(0).content);
    assertEquals("Comment 3", yetAnotherUser.posts.get(1).comments.get(1).content);    
  }
  
}