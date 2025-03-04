package springsecurity1.demo.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    // для связей многие ко многим.
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<MyUser> myUsers;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MyUser> getUsers() {
        return myUsers;
    }

    public void setUsers(Set<MyUser> myUsers) {
        this.myUsers = myUsers;
    }

    @Override
    public String getAuthority() {
        return getName();
    }


}
