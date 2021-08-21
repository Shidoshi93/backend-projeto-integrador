package Api.controller;

import Api.model.Post;
import Api.model.User;
import Api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String addPost(@RequestBody Post post) {
        postRepository.save(post);
       return "Post adicionado com sucesso.";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Post getPost(@PathVariable Integer id) {
        Optional<Post> post = postRepository.findById(id);

        return post.orElse(null);

    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@PathVariable Integer id, @RequestBody Post post) {
        postRepository.findById(id).map(record -> {
            record.setDescription(post.getDescription());
            record.setQtd(post.getQtd());
            Post updated = postRepository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Integer id) {
        postRepository.deleteById(id);
    }
}
