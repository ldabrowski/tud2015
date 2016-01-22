package pl.projekt.tp.service;
import  pl.projekt.tp.domain.Rakieta;
import pl.projekt.tp.domain.Model;

import java.util.List;

public interface Manager {

    List<Rakieta> DajRakiety();
    List<Model> DajModele();

    List<Rakieta> SzukaniePoNazwie(String wzorzec);

    void usunZaleznosci(Model mod);

    Long dodaj(Rakieta rakieta);
    Long dodaj(Model model);

    void usun(Rakieta rak);
    void usun(Model mod);

    void edycja(Rakieta rak, Model model, String nazwa, String opis);
    void edycja(Model mod, String nazwa, String opis);

    Rakieta pobierzRakietaPoId(Long id);
    Model pobierzModelPoId(Long id);


}
