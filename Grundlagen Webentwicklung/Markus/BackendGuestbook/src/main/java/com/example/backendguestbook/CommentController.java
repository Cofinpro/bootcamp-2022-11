package com.example.backendguestbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 4800,allowCredentials = "false")
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /** takes json, saves to database, returns json with Id
     * @param commentDTO
     * @return comment
     */
    @PostMapping("/api/comment/save")
    public Comment saveComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = CommentDirector.constructCommentFromDTO(commentDTO);
        commentRepository.save(comment);
        return comment;
    }

    /**
     * @return list of all comments
     */
    @GetMapping("/api/comment")
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    /** deletes comment from database by Id
     * @param id
     */
    @DeleteMapping("/api/comment/delete/{id}")
    public void deleteComment(@PathVariable String id){
        commentRepository.deleteById(Long.parseLong(id));
    }

    /**Edits text of comment
     * @param id
     * @param textDTO
     */
    @PutMapping("/api/comment/edit/{id}")
    public void editComment(@PathVariable String id, @RequestBody TextDTO textDTO) {
        Comment comment = commentRepository.findById(Long.parseLong(id)).
                orElseThrow(RuntimeException::new);
        comment.setComment(textDTO.getText());
        commentRepository.save(comment);
    }
}
