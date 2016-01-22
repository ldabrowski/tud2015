package pl.projekt.tp.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "all.rakiety", query = "SELECT t FROM Rakieta t"),
        @NamedQuery(name = "nazwa.rakieta", query =  "SELECT t FROM Rakieta t WHERE t.nazwa = :nazwa"),
})
public class Rakieta implements java.io.Serializable{
    private Long id;
    private Model model;
    private String nazwa;
    private String opis;

    @Column(nullable = false)
    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) {
        this.opis = opis;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model", nullable = false)
    public Model getModel() {
        return model;
    }
    public void setModel(Model Model) { this.model = Model; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
