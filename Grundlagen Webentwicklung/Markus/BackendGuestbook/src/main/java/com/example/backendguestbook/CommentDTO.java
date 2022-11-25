package com.example.backendguestbook;

public class CommentDTO {
    private String name;
    private String comment;
    private String datetime;

    public CommentDTO(CommentDTOBuilder builder) {
        name = builder.name;
        comment = builder.comment;
        datetime = builder.datetime;
    }

    public CommentDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public static class CommentDTOBuilder {
        private String name;
        private String comment;
        private String datetime;
        public CommentDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public CommentDTOBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }
        public CommentDTOBuilder setDateTime(String datetime) {
            this.datetime = datetime;
            return this;
        }
        public CommentDTO returnProduct() {
            return new CommentDTO(this);
        }
    }
}
