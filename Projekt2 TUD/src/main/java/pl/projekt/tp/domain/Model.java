package pl.projekt.tp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "all.modele", query = "SELECT m FROM Model m"),
})
public class Model {
    private Long id;
    private String nazwa;
    private String opis;

    @Column(nullable = false)
    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String model) {
        this.nazwa = model;
    }

    private List<Rakieta> Rakiety = new ArrayList<Rakieta>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) {
        this.opis = opis;
    }

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Rakieta> getRakiety() {
        return Rakiety;
    }
    public void setRakiety(List<Rakieta> Rakiety) {
        this.Rakiety =  Rakiety;
    }


}

