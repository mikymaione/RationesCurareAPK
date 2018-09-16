/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.DB.Wrappers;

import android.content.ContentValues;
import android.database.Cursor;

import org.joda.time.DateTime;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Tables.MovimentiTempo;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Templates.cTabella;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.DB.eTabelle;
import it.mikymaione.RationesCurare.DB.eTipi;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.Pair;
import it.mikymaione.RationesCurare.Globals.cErrore;

public class cMovimentiTempo extends cTabella<MovimentiTempo, Integer>
{

    public cMovimentiTempo(cDB db)
    {
        super(db);
    }

    @Override
    protected ContentValues ContentValuesFromTabella(MovimentiTempo tabella)
    {
        ContentValues v = new ContentValues();
        v.put("nome", tabella.nome);
        v.put("tipo", tabella.tipo);
        v.put("descrizione", tabella.descrizione);
        v.put("MacroArea", tabella.MacroArea);
        v.put("TipoGiorniMese", tabella.TipoGiorniMese);

        v.put("Scadenza", GB.DateToSQLite(tabella.Scadenza));
        v.put("GiornoDelMese", GB.DateToSQLite(tabella.GiornoDelMese));
        //v.put("PartendoDalGiorno", GB.DateToSQLite(tabella.PartendoDalGiorno)); //non usare

        v.put("soldi", tabella.soldi);
        v.put("NumeroGiorni", tabella.NumeroGiorni);

        return v;
    }

    @Override
    protected MovimentiTempo CaricaDaCursore(Cursor c)
    {
        int j = -1;
        MovimentiTempo e = new MovimentiTempo();

        e.ID = c.getInt(j += 1);
        e.nome = c.getString(j += 1);
        e.tipo = c.getString(j += 1);
        e.descrizione = c.getString(j += 1);
        e.NumeroGiorni = c.getInt(j += 1);
        e.GiornoDelMese = GB.SQLiteDateToDateTime(c.getString(j += 1));
        e.TipoGiorniMese = c.getString(j += 1);
        e.PartendoDalGiorno = GB.SQLiteDateToDateTime(c.getString(j += 1));
        e.MacroArea = c.getString(j += 1);
        e.Scadenza = GB.SQLiteDateToDateTime(c.getString(j += 1));
        e.soldi = c.getDouble(j += 1);

        return e;
    }

    @Override
    protected cErrore<Boolean> ControllaPrimaDiSalvare(Integer ID, MovimentiTempo tabella)
    {
        String e = "";

        if (tabella.descrizione == null || tabella.descrizione.length() < 3)
        {
            e = "La descrizione è troppo breve!";
        }
        if (tabella.nome == null || tabella.nome.length() < 2)
        {
            e = "Il nome è troppo breve!";
        }
        if (tabella.soldi == null || tabella.soldi == 0)
        {
            e = "Non puoi inserire un importo 0";
        }
        //continuare

        return new cErrore<Boolean>(e, e.equals(""));
    }

    public String getKeyOfGiorniMeseByVal(String v)
    {
        ArrayList<Pair<String, String>> h = Get_TipoGiorniMese();

        for (Pair<String, String> p : h)
        {
            if (p.getVal().equals(v))
            {
                return p.getKey();
            }
        }

        return "";
    }

    public String getValueOfGiorniMeseByVal(String k)
    {
        ArrayList<Pair<String, String>> h = Get_TipoGiorniMese();

        for (Pair<String, String> p : h)
        {
            if (p.getKey().equals(k))
            {
                return p.getVal();
            }
        }

        return "";
    }

    public ArrayList<String> getTipoGiorniMese()
    {
        ArrayList<String> r = new ArrayList<String>();
        ArrayList<Pair<String, String>> h = Get_TipoGiorniMese();

        for (Pair<String, String> p : h)
        {
            r.add(p.getVal());
        }

        return r;
    }


    private ArrayList<Pair<String, String>> Get_TipoGiorniMese()
    {
        ArrayList<Pair<String, String>> r = new ArrayList<Pair<String, String>>();

        r.add(new Pair("M", "Mese"));
        r.add(new Pair("G", "Giorni"));

        return r;
    }

    @Override
    public cErrore<Long> Elimina(Integer ID)
    {
        return db.delete(eTabelle.MovimentiTempo, "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore<Long> Modifica(Integer ID, MovimentiTempo tabella)
    {
        return db.update(eTabelle.MovimentiTempo, ContentValuesFromTabella(tabella), "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore<Long> Inserisci(MovimentiTempo tabella)
    {
        return db.insert(eTabelle.MovimentiTempo, ContentValuesFromTabella(tabella));
    }

    @Override
    public MovimentiTempo Carica(Integer ID)
    {
        Cursor c = db.rawQuery(eTabelle.MovimentiTempo, eTipi.Carica, new QPar[]{new QPar("ID", ID)});

        return TabellaFromCursore(c);
    }

    @Override
    public ArrayList<MovimentiTempo> Ricerca(QPar[] parametri)
    {
        Cursor c = db.rawQuery(eTabelle.MovimentiTempo, eTipi.Ricerca, parametri);

        return TabelleFromCursore(c);
    }

    public ArrayList<MovimentiTempo> RicercaScadenzeEntroOggi()
    {
        DateTime n = DateTime.now();
        DateTime da = new DateTime(1900, 1, 1, 0, 0, 0);
        DateTime a = new DateTime(n.getYear(), n.getMonthOfYear(), n.getDayOfMonth(), 23, 59, 59);

        return RicercaScadenze(da, a);
    }

    public ArrayList<MovimentiTempo> RicercaScadenzeCalcolate(org.joda.time.DateTime da, org.joda.time.DateTime a)
    {
        ArrayList<MovimentiTempo> n = new ArrayList<MovimentiTempo>();
        ArrayList<MovimentiTempo> c = RicercaScadenze(da, a);

        if (c != null)
        {
            for (MovimentiTempo i : c)
            {
                if (i.TipoGiorniMese.equals("M"))
                {
                    DateTime data = i.GiornoDelMese;

                    while (data.compareTo(a) < 0 && (data.compareTo(i.Scadenza) < 0 || i.Scadenza == null))
                    {
                        MovimentiTempo z = i.clone();
                        z.GiornoDelMese = data;

                        n.add(z);
                        data = data.plusMonths(1);
                    }
                }
            }

            if (n.size() > 0)
            {
                //n.Sort();
            }
        }

        return n;
    }

    public ArrayList<MovimentiTempo> RicercaScadenze(org.joda.time.DateTime da, org.joda.time.DateTime a)
    {
        Cursor c = db.rawQuery(eTabelle.MovimentiTempo, eTipi.Scadenze, new QPar[]{new QPar("GiornoDa", GB.DateToSQLite(da)), new QPar("GiornoA", GB.DateToSQLite(a))});

        return TabelleFromCursore(c);
    }


}