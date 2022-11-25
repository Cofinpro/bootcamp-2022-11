package com.example.backendguestbook;


import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "name")
    private String name;
    @Column(name="text")
    private String comment;
    @Column(name="datetime")
    private String datetime;

    public Comment() {
    }

    public Comment(CommentBuilder builder) {
        this.name = builder.name;
        this.comment = builder.text;
        this.datetime = builder.datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public static class CommentBuilder {
        private String name;
        private String text;
        private String datetime;
        public CommentBuilder setText(String text) {
            this.text=text;
            return this;
        }
        public CommentBuilder setName(String name) {
            this.name=name;
            return this;
        }
        public CommentBuilder setDatetime(String datetime) {
            this.datetime=datetime;
            return this;
        }

        public Comment returnProduct() {
            return new Comment(this);
        }
    }
}
