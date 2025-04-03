package github.com.LucasAlcoforad.TO_DO.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table
public class Todo {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private boolean complete = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo() {
    }

    public Todo(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public Todo(UUID id, String title, String description, boolean complete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.complete = complete;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
