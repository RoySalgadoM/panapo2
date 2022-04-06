package mx.edu.utez.panapo.personTeam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.panapo.person.model.Person;
import mx.edu.utez.panapo.project.model.Project;
import mx.edu.utez.panapo.rolProject.RolProject;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class PersonTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @NonNull
    Person person;
    @ManyToOne
    @JoinColumn(name = "rolProject_id", nullable = false)
    @NonNull
    RolProject rolProject;
    @OneToMany(mappedBy = "statusProject")
    @JsonIgnore
    private List<Project> project;

    public PersonTeam() {
    }

    public PersonTeam(Person person, RolProject rolProject, List<Project> project) {
        this.person = person;
        this.rolProject = rolProject;
        this.project = project;
    }

    public PersonTeam(long id,  Person person, RolProject rolProject, List<Project> project) {
        this.id = id;
        this.person = person;
        this.rolProject = rolProject;
        this.project = project;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson( Person person) {
        this.person = person;
    }

    public RolProject getRolProject() {
        return rolProject;
    }

    public void setRolProject( RolProject rolProject) {
        this.rolProject = rolProject;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> team) {
        this.project = team;
    }
}
