package pl.projekt.tp.service;

import pl.projekt.tp.domain.Rakieta;
import pl.projekt.tp.domain.Model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional
public class ManagerImpl implements Manager {

    @Autowired
    private SessionFactory sf;

    public SessionFactory getSessionFactory() {
        return sf;
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rakieta> DajRakiety() {
        return sf.getCurrentSession().getNamedQuery("all.rakiety").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Model> DajModele() {
        return sf.getCurrentSession().getNamedQuery("all.modele").list();
    }

    @Override
    public List<Rakieta> SzukaniePoNazwie(String wzorzec){
        return sf.getCurrentSession().getNamedQuery("nazwa.rakieta").setString("nazwa", wzorzec).list();
    }

    @Override
    public void usunZaleznosci(Model mod){
        List<Rakieta> rakiety = DajRakiety();
        for (Rakieta rak : rakiety)
        {
            if(rak.getModel().getId() == mod.getId())
                usun(rak);
        }
    }

    @Override
    public Long dodaj(Rakieta rakieta) {
        Long id = (Long) sf.getCurrentSession().save(rakieta);
        rakieta.setId(id);
        Model model = (Model) sf.getCurrentSession().get(Model.class, rakieta.getModel().getId());
        model.getRakiety().add(rakieta);
        return id;
    }

    @Override
    public Long dodaj(Model model) {
        Long id = (Long) sf.getCurrentSession().save(model);
        model.setId(id);
        return id;
    }

    @Override
    public void usun(Rakieta rak) {
        rak = (Rakieta) sf.getCurrentSession().get(Rakieta.class, rak.getId());
        Model mod = (Model) sf.getCurrentSession().get(Model.class, rak.getModel().getId());
        mod.getRakiety().remove(rak);
        sf.getCurrentSession().delete(rak);
    }

    @Override
    public void usun(Model mod) {
        mod = (Model) sf.getCurrentSession().get(Model.class, mod.getId());
        sf.getCurrentSession().delete(mod);
    }

    @Override
    public void edycja(Rakieta rak, Model model, String nazwa, String opis) {
        rak = (Rakieta) sf.getCurrentSession().get(Rakieta.class, rak.getId());
        rak.setModel(model);
        rak.setNazwa(nazwa);
        rak.setOpis(opis);
        sf.getCurrentSession().update(rak);
    }

    @Override
    public void edycja(Model mod, String model, String opis) {
        mod = (Model) sf.getCurrentSession().get(Model.class, mod.getId());
        mod.setNazwa(model);
        mod.setOpis(opis);
        sf.getCurrentSession().update(mod);
    }

    @Override
    public Rakieta pobierzRakietaPoId(Long id) {
        return (Rakieta) sf.getCurrentSession().get(Rakieta.class, id);
    }

    @Override
    public Model pobierzModelPoId(Long id) {
        return (Model) sf.getCurrentSession().get(Model.class, id);
    }

}
