package Api.controller;

import Api.model.Post;
import Api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post newPost) {
        Post postUserId = postRepository.findByUserId(newPost.getUser());
        Post postUserType = postRepository.findByUserType(newPost.getUser_type());
        Post postDonation = postRepository.findByDonation(newPost.getDonation_type());
        Post postQty = postRepository.findByQty(newPost.getQtd());
        Post postStatus = postRepository.findByStatus(newPost.getStatus());

        if (postUserId != null && postUserType.getUser_type().equals(newPost.getUser_type()) &&
                postDonation.getDonation_type().equals(newPost.getDonation_type()) &&
                postQty.getQtd().equals(newPost.getQtd()) &&
                postStatus.getStatus().equals(newPost.getStatus())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Um post desse tipo já foi criado e está ativo!");
        } else{
            return postRepository.save(newPost);
        }
    }


    @RequestMapping(value = "/getPost/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Post getPost(@PathVariable Integer id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()){
            return post.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!");
        }
    }

    @RequestMapping(value = "/getPosts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @RequestMapping(value = "/updatePost/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@PathVariable Integer id, @RequestBody Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            optionalPost.map(record -> {
            record.setDescription(post.getDescription());
            record.setQtd(post.getQtd());
            record.setStatus(post.getStatus());
            Post updated = postRepository.save(record);
            return ResponseEntity.ok().body(updated);
        });
            return optionalPost.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
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
