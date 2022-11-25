package com.example.backendguestbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/api/comment/save")
    public Comment saveComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = CommentDirector.constructCommentFromDTO(commentDTO);
        commentRepository.save(comment);
        return comment;
    }
    @GetMapping("/api/comment")
    public List<CommentDTO> getComments() {
        List<CommentDTO> DTOList = new ArrayList<>();
        commentRepository.findAll().forEach(comment ->
                DTOList.add(CommentDirector.constructDTOFromComment(comment)));
        return DTOList;
    }
}
