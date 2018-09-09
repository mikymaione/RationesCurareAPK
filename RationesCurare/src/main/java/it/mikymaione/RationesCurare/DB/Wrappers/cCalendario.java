package it.mikymaione.RationesCurare.DB.Wrappers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import it.mikymaione.RationesCurare.DB.Tables.Calendario;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Templates.cTabella;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.DB.eTabelle;
import it.mikymaione.RationesCurare.DB.eTipi;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.Globals.cOggiDomani;

public class cCalendario extends cTabella<Calendario, Integer>
{

    public cCalendario(cDB db)
    {
        super(db);
    }

    @Override
    protected ContentValues ContentValuesFromTabella(Calendario tabella)
    {
        ContentValues v = new ContentValues();
        v.put("Giorno", GB.DateToSQLite(tabella.Giorno));
        v.put("Descrizione", tabella.Descrizione);
        v.put("IDGruppo", tabella.IDGruppo);
        //v.put("Inserimento", GB.DateToSQLite(tabella.Inserimento)); default su DB

        return v;
    }

    @Override
    protected Calendario CaricaDaCursore(Cursor c)
    {
        int j = -1;
        Calendario e = new Calendario();

        e.ID = c.getInt(j += 1);
        e.Giorno = GB.SQLiteDateToDate(c.getString(j += 1));
        e.Descrizione = c.getString(j += 1);
        e.IDGruppo = c.getString(j += 1);
        e.Inserimento = GB.SQLiteDateToDate(c.getString(j += 1));

        return e;
    }

    @Override
    protected cErrore<Boolean> ControllaPrimaDiSalvare(Integer ID, Calendario tabella)
    {
        String e = "";

        if (tabella.Descrizione == null || tabella.Descrizione.length() < 3)
        {
            e = "La descrizione è troppo breve!";
        }

        return new cErrore<Boolean>(e, e.equals(""));
    }

    @Override
    public cErrore Elimina(Integer ID)
    {
        return db.delete(eTabelle.Calendario, "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore Modifica(Integer ID, Calendario tabella)
    {
        return db.update(eTabelle.Calendario, ContentValuesFromTabella(tabella), "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore Inserisci(Calendario tabella)
    {
        return db.insert(eTabelle.Calendario, ContentValuesFromTabella(tabella));
    }

    @Override
    public Calendario Carica(Integer ID)
    {
        Cursor c = db.rawQuery(eTabelle.Calendario, eTipi.Carica, new QPar[]{new QPar("ID", ID)});

        return TabellaFromCursore(c);
    }

    /**
     * Ricerca degli eventi del calendario
     *
     * @param parametri: bData, DA, A
     * @return eventi del calendario
     */
    @Override
    public ArrayList<Calendario> Ricerca(QPar[] parametri)
    {
        if (parametri == null)
        {
            parametri = new QPar[]{new QPar("bData", 0), new QPar("DA", ""), new QPar("A", "")};
        }

        Cursor c = db.rawQuery(eTabelle.Calendario, eTipi.Ricerca, parametri, -1);

        return TabelleFromCursore(c);
    }

    public ArrayList<Calendario> Ricerca()
    {
        return Ricerca(null);
    }

    public cOggiDomani<Calendario> getPromemoria()
    {
        cOggiDomani<Calendario> p = new cOggiDomani<Calendario>();

        Date n = new Date();
        Calendar cDa = Calendar.getInstance();
        Calendar cA = Calendar.getInstance();

        cDa.set(n.getYear() + 1900, n.getMonth(), n.getDate(), 0, 0, 0);
        cA.set(n.getYear() + 1900, n.getMonth(), n.getDate(), 23, 59, 59);

        ArrayList<Calendario> oggi = Ricerca(new QPar[]{new QPar("bData", 1), new QPar("DA", GB.DateToSQLite(cDa)), new QPar("A", GB.DateToSQLite(cA))});

        cDa.add(Calendar.DATE, 1);
        cA.add(Calendar.DATE, 1);

        ArrayList<Calendario> doma = Ricerca(new QPar[]{new QPar("bData", 1), new QPar("DA", GB.DateToSQLite(cDa)), new QPar("A", GB.DateToSQLite(cA))});

        if (oggi != null)
        {
            if (oggi.size() > 0)
            {
                p.Oggi = oggi;
            }
        }

        if (doma != null)
        {
            if (doma.size() > 0)
            {
                p.Domani = doma;
            }
        }

        return p;
    }


}