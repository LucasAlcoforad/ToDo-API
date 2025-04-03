package github.com.LucasAlcoforad.TO_DO.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.w3c.dom.stylesheets.LinkStyle;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todos;

    @Column
    private boolean active = true;

    @CreationTimestamp
    private Timestamp createTimeStamp;

    @UpdateTimestamp
    private Timestamp updateTimeStamp;

    public User() {
    }

    public User(UUID id, String email, String password, String username, boolean active, Timestamp createTimeStamp, Timestamp updateTimeStamp) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.active = active;
        this.createTimeStamp = createTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(Timestamp createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public Timestamp getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(Timestamp updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
}
