package messenger.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public class Post {

    private long id;
    private String content;
    private Date created;
    private String author;
    private Map<Long, Comment> comments = new HashMap<>();

    public Post() {

    }

    public Post(long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.created = new Date();
    }
    public Post(long id, String content, Date created, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlTransient
    public Map<Long, Comment> getComments() {
        return comments;
    }

    public void setComments(Map<Long, Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", author='" + author + '\'' +
                ", comments=" + comments +
                '}';
    }
}
