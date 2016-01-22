package pl.projekt.tp.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.projekt.tp.domain.Model;
import pl.projekt.tp.domain.Rakieta;
import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional


public class ManagerTest {

    //"wstrzykniecie" danych

    @Autowired
    Manager manager;



    private List<Long>  dodaneRakiety = new ArrayList<Long>();
    private List<Long>  dodaneModele = new ArrayList<Long>();

    private final String rakieta1 = "Babolat";
    private final String rakieta2 = "Head";

    private final String opisRakiety1 = "Wybrany m.in przez: Nadala, Tsonge i Janowicza";
    private final String opisRakiety2 = "Wybrany m.in przez: Djokovica, Murraya i Przysieznego";

    private final String model1 = "Aero Pure";
    private final String model2 = "Speed Pro";

    private final String opisModele1 = "Rakieta Nadala";
    private final String opisModele2 = "Rakieta Djokovica";


    @Before
    public void SprawdzDodawanie() {

        //sprawdza dodane rakiety i modele

        List<Rakieta> rakiety = manager.DajRakiety();
        List<Model> modele = manager.DajModele();

        for(Rakieta rakieta : rakiety)
            dodaneRakiety.add(rakieta.getId());

        for(Model model : modele)
            dodaneModele.add(model.getId());
    }

    @After
    public void Czyszczenie() {

        //czyszczenie bazy po testach

        List<Rakieta> rakiety = manager.DajRakiety();
        List<Model> modele = manager.DajModele();

        boolean usun;

        for(Rakieta rakieta : rakiety) {
            usun = true;
            for (Long rakieta2 : dodaneRakiety)
                if (rakieta.getId() == rakieta2) {
                    usun = false;
                    break;
                }
            if(usun)
                manager.usun(rakieta);
        }

        for(Model model : modele) {
            usun = true;
            for (Long model2 : dodaneModele)
                if (model.getId() == model2)
                {
                    usun = false;
                    break;
                }
            if(usun)
                manager.usun(model);
        }
    }

    @Test
    public void Dodawanie() {

        Model mod = new Model();
        mod.setNazwa(model1);
        mod.setOpis(opisModele1);

        Rakieta rak = new Rakieta();

        rak.setModel(mod);
        rak.setNazwa(rakieta1);
        rak.setOpis(opisRakiety1);
        Long modId = manager.dodaj(mod);
        Long rakId = manager.dodaj(rak);

        Rakieta rakPoId = manager.pobierzRakietaPoId(rakId);
        Model modPoId = manager.pobierzModelPoId(modId);

        assertEquals(model1, modPoId.getNazwa());
        assertEquals(opisModele1, modPoId.getOpis());
        assertEquals(model1, rakPoId.getModel().getNazwa());
        assertEquals(opisModele1, rakPoId.getModel().getOpis());
        assertEquals(rakieta1, rakPoId.getNazwa());
        assertEquals(opisRakiety1, rakPoId.getOpis());

    }

    @Test
    public void WyszukiwanieRakiety(){

        Model mod1 = new Model();
        Model mod2 = new Model();
        mod1.setNazwa(model1);
        mod1.setOpis(opisModele1);
        mod2.setNazwa(model2);
        mod2.setOpis(opisModele2);

        manager.dodaj(mod1);
        manager.dodaj(mod2);

        Rakieta rak1 = new Rakieta();
        Rakieta rak2 = new Rakieta();
        rak1.setModel(mod1);
        rak1.setNazwa(rakieta1);
        rak1.setOpis(opisRakiety1);
        rak2.setModel(mod2);
        rak2.setNazwa(rakieta1);
        rak2.setOpis(opisRakiety2);

        manager.dodaj(rak1);
        manager.dodaj(rak2);

        assertEquals(manager.SzukaniePoNazwie(rakieta1).size(), 2); // 2 przypisania
        assertEquals(manager.SzukaniePoNazwie(rakieta2).size(), 0);

    }

    @Test
    public void PobraniePoId() {

        Model model = new Model();
        model.setNazwa(model1);
        model.setOpis(opisModele1);

        Rakieta rakieta = new Rakieta();
        rakieta.setModel(model);
        rakieta.setNazwa(rakieta1);
        rakieta.setOpis(opisRakiety1);

        Long modelId = manager.dodaj(model);
        Long rakietaID = manager.dodaj(rakieta);

        Rakieta rakieta2 = manager.pobierzRakietaPoId(rakietaID);
        Model model2 = manager.pobierzModelPoId(modelId);

        assertEquals(modelId, model2.getId());
        assertEquals(model1, model2.getNazwa());
        assertEquals(opisModele1, model2.getOpis());
        assertEquals(rakietaID, rakieta2.getId());
        assertEquals(model.getNazwa(), rakieta2.getModel().getNazwa());
        assertEquals(model.getOpis(), rakieta2.getModel().getOpis());
        assertEquals(rakieta1, rakieta2.getNazwa());
        assertEquals(opisRakiety1, rakieta2.getOpis());

    }

    @Test
    public void SzukaniePoNazwie() {

        int licznik=0;
        String wzorzec = rakieta1;

        Model mod = new Model();

        mod.setNazwa(model1);
        mod.setOpis(opisModele1);

        Rakieta rak = new Rakieta();
        rak.setModel(mod);
        rak.setNazwa(rakieta1);
        rak.setOpis(opisRakiety1);
        manager.dodaj(mod);
        manager.dodaj(rak);

        for(Long l : dodaneRakiety)
        {
            if(wzorzec == manager.pobierzRakietaPoId(l).getNazwa())
                licznik++;
        }
        List<Rakieta> rakiety = manager.SzukaniePoNazwie(wzorzec); // wyszkuje wedlug wzorca model1
        assertEquals(rakiety.size(), licznik+1); //ile bylo juz wczesniej + ten dodany w testach
    }

    @Test
    public void Edycja() {

        int x = 0;
        int y = 0;

        Model mod = new Model();

        mod.setNazwa(model1);
        mod.setOpis(opisModele1);

        Rakieta rak = new Rakieta();
        rak.setModel(mod);
        rak.setNazwa(rakieta1);
        rak.setOpis(opisRakiety1);

        Long modID = manager.dodaj(mod);
        Long rakID = manager.dodaj(rak);

        List<Rakieta> rakiety = manager.DajRakiety();
        List<Model> modele = manager.DajModele();

        manager.edycja(mod, model2, opisModele2);
        manager.edycja(rak, mod, rakieta2, opisRakiety2);

        List<Rakieta> rakiety2 = manager.DajRakiety();
        List<Model> modele2 = manager.DajModele();

        for(Rakieta rakieta : rakiety) {
            for (Rakieta rak2 : rakiety2){
                if(rakieta.getId() == rak2.getId()) {
                    if (rakieta.getId() != rakID) {
                        assertEquals(rak2.getModel().getNazwa(), rakieta.getModel().getNazwa());
                        assertEquals(rak2.getModel().getOpis(), rakieta.getModel().getOpis());
                        assertEquals(rak2.getNazwa(), rakieta.getNazwa());
                        assertEquals(rak2.getOpis(), rakieta.getOpis());
                        x++;
                    } else if (rakieta.getId() == rakID) {
                        assertEquals(model2, rakieta.getModel().getNazwa());
                        assertEquals(opisModele2, rakieta.getModel().getOpis());
                        assertEquals(rakieta2, rakieta.getNazwa());
                        assertEquals(opisRakiety2, rakieta.getOpis());
                        y++;
                    }
                }
            }
        }
        assertEquals(y, 1);
        assertEquals(x+y, rakiety2.size());
        assertEquals(rakiety.size(), rakiety2.size());
        x = 0;
        y = 0;
        for(Model model : modele) {
            for (Model mod2 : modele2){
                if(model.getId() == mod2.getId()) {
                    if (model.getId() != modID) {
                        assertEquals(modele2.get(x).getNazwa(), model.getNazwa());
                        assertEquals(modele2.get(x).getOpis(), model.getOpis());
                        x++;
                    } else if (model.getId() == modID) {
                        assertEquals(model2, model.getNazwa());
                        assertEquals(opisModele2, model.getOpis());
                        y++;
                    }
                }
            }
        }

        assertEquals(y, 1);
        assertEquals(x+y, modele2.size());
        assertEquals(modele.size(), modele2.size());
    }

    @Test
    public void PobieranieWszystkich() {

        List<Rakieta> rakiety = manager.DajRakiety();
        List<Model> modele = manager.DajModele();

        int ileRakiet = rakiety.size();
        int ileModeli = modele.size();

        Model mod = new Model();

        mod.setNazwa(model1);
        mod.setOpis(opisModele1);

        Rakieta rak = new Rakieta();

        rak.setModel(mod);
        rak.setNazwa(rakieta1);
        rak.setOpis(opisRakiety1);

        manager.dodaj(mod);
        manager.dodaj(rak);

        rakiety = manager.DajRakiety();
        modele = manager.DajModele();

        assertEquals(ileRakiet+1, rakiety.size());
        assertEquals(ileModeli+1, modele.size());

        for(Rakieta rakieta : rakiety) {
            rak = manager.pobierzRakietaPoId(rakieta.getId());
            assertNotNull(rak);
        }
        for(Model model : modele) {
            mod = manager.pobierzModelPoId(model.getId());
            assertNotNull(mod);
        }
    }

    @Test
    public void UsuwanieZaleznosci() {
        Model mod1 = new Model();
        mod1.setNazwa(model1);
        mod1.setOpis(opisModele1);

        manager.dodaj(mod1);

        Rakieta rak1 = new Rakieta();
        Rakieta rak2 = new Rakieta();
        rak1.setModel(mod1);
        rak1.setNazwa(rakieta1);
        rak1.setOpis(opisRakiety1);
        rak2.setModel(mod1);
        rak2.setNazwa(rakieta2);
        rak2.setOpis(opisRakiety2);
        Long idRakieta1 = manager.dodaj(rak1);
        Long idRakieta2 = manager.dodaj(rak2);

        List<Rakieta> rakiety = manager.DajRakiety();
        manager.usunZaleznosci(mod1);
        Rakieta rakietaPoId1 = manager.pobierzRakietaPoId(idRakieta1);
        Rakieta rakietaPoId2 = manager.pobierzRakietaPoId(idRakieta2);

        assertEquals(rakietaPoId1, null);
        assertEquals(rakietaPoId2, null);
        List<Rakieta> rakiety2 = manager.DajRakiety();
        assertEquals(rakiety2.size(), rakiety.size()-2);

        int i = 0;

        for(Rakieta rakieta : rakiety) {
            for(Rakieta rakieta2 : rakiety2)
                if(rakieta.getId() == rakieta2.getId()) {
                    assertEquals(rakieta2.getModel().getNazwa(), rakieta.getModel().getNazwa());
                    assertEquals(rakieta2.getModel().getOpis(), rakieta.getModel().getOpis());
                    assertEquals(rakieta2.getNazwa(), rakieta.getNazwa());
                    assertEquals(rakieta2.getOpis(), rakieta.getOpis());
                    i++;
                }
        }

        assertEquals(rakiety2.size(), i);
    }

    @Test
    public void Usuwanie() {

        Model mod = new Model();
        mod.setNazwa(model1);
        mod.setOpis(opisModele1);

        Rakieta rak = new Rakieta();
        rak.setModel(mod);
        rak.setNazwa(rakieta1);
        rak.setOpis(opisRakiety1);

        Long modId = manager.dodaj(mod);
        Long rakId = manager.dodaj(rak);

        List<Rakieta> rakiety = manager.DajRakiety();
        List<Model> modele = manager.DajModele();

        manager.usun(rak);
        manager.usun(mod);

        int ileRakiet = rakiety.size();
        int ileModeli = modele.size();

        Rakieta rakPoId = manager.pobierzRakietaPoId(rakId);
        Model modPoId = manager.pobierzModelPoId(modId);

        assertEquals(rakPoId, null);
        assertEquals(modPoId, null);

        List<Rakieta> rakiety2 = manager.DajRakiety();
        List<Model> modele2 = manager.DajModele();

        assertEquals(rakiety2.size(), ileRakiet-1);
        assertEquals(modele2.size(), ileModeli-1);

        int i = 0;

        for(Rakieta rakieta : rakiety) {
            for(Rakieta rakieta2 : rakiety2)
                if(rakieta.getId() == rakieta2.getId()) {
                    assertEquals(rakieta2.getModel().getNazwa(), rakieta.getModel().getNazwa());
                    assertEquals(rakieta2.getModel().getOpis(), rakieta.getModel().getOpis());
                    assertEquals(rakieta2.getNazwa(), rakieta.getNazwa());
                    assertEquals(rakieta2.getOpis(), rakieta.getOpis());
                    i++;
                }
        }

        assertEquals(rakiety2.size(), i);

        i = 0;

        for(Model model : modele) {
            for(Model model2 : modele2)
            {
                if(model.getId() == model2.getId()) {
                    assertEquals(model2.getNazwa(), model.getNazwa());
                    assertEquals(model2.getOpis(), model.getOpis());
                    i++;
                }
            }
        }

        assertEquals(modele2.size(), i);
    }

}
