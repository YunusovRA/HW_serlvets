package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(0);

  public List<Post> all() {
    return List.copyOf(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.incrementAndGet());
    }
    posts.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    if (posts.remove(id) == null) {
      throw new NotFoundException("Post with id " + id + " not found");
    }
  }
}
