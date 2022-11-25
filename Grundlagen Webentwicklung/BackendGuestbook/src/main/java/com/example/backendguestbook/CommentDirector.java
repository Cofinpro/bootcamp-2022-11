package com.example.backendguestbook;

public class CommentDirector {
    public static Comment constructCommentFromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment.CommentBuilder()
                .setName(commentDTO.getName()).setText(commentDTO.getComment())
                .setDatetime(commentDTO.getDatetime())
                .returnProduct();
        return comment;
    }
    public static CommentDTO constructDTOFromComment(Comment comment) {
        CommentDTO commentDTO = new CommentDTO.CommentDTOBuilder()
                .setName(comment.getName())
                .setComment(comment.getComment())
                .setDateTime(comment.getDatetime())
                .returnProduct();
        return commentDTO;
    }
}
