package Api.controller;

import Api.model.Post;
import Api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String addPost(@RequestBody Post newPost) {
        postRepository.save(newPost);
        return "Post adicionado com sucesso.";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Post getPost(@PathVariable Integer id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()){
            return post.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!");
        }
    }

    @GetMapping(value = "/listAll/page")
    public List<Post> paginacaoComParametro(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return page.getContent();
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Post  updatePost(@PathVariable Integer id, @RequestBody Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
        optionalPost.map(record -> {
            record.setDescription(post.getDescription());
            record.setQtd(post.getQtd());
            Post updated = postRepository.save(record);
            return ResponseEntity.ok().body(updated);
        });
            return optionalPost.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!");
        }
    }
}
