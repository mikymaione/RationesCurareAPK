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

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Tables.Saldo;
import it.mikymaione.RationesCurare.DB.Tables.grafico_anno;
import it.mikymaione.RationesCurare.DB.Tables.grafico_mese;
import it.mikymaione.RationesCurare.DB.Tables.movimenti;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Templates.cTabella;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.DB.eTabelle;
import it.mikymaione.RationesCurare.DB.eTipi;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;

public class cMovimenti extends cTabella<movimenti, Integer>
{

    public cMovimenti(cDB db)
    {
        super(db);
    }

    @Override
    protected ContentValues ContentValuesFromTabella(movimenti tabella)
    {
        ContentValues v = new ContentValues();
        v.put("nome", tabella.nome);
        v.put("tipo", tabella.tipo);
        v.put("descrizione", tabella.descrizione);
        v.put("MacroArea", tabella.MacroArea);
        v.put("data", GB.DateToSQLite(tabella.data));
        v.put("soldi", tabella.soldi);

        return v;
    }

    @Override
    protected movimenti CaricaDaCursore(Cursor c)
    {
        int j = -1;
        movimenti e = new movimenti();

        e.ID = c.getInt(j += 1);
        e.nome = c.getString(j += 1);
        e.data = GB.SQLiteDateToDate(c.getString(j += 1));
        e.tipo = c.getString(j += 1);
        e.descrizione = c.getString(j += 1);
        e.MacroArea = c.getString(j += 1);
        e.soldi = c.getDouble(j += 1);

        return e;
    }

    @Override
    protected cErrore<Boolean> ControllaPrimaDiSalvare(Integer ID, movimenti tabella)
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
        if (tabella.data == null)
        {
            e = "Selezionare una data";
        }

        return new cErrore<Boolean>(e, e.equals(""));
    }

    @Override
    public cErrore<Long> Elimina(Integer ID)
    {
        return db.delete(eTabelle.movimenti, "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore<Long> Modifica(Integer ID, movimenti tabella)
    {
        return db.update(eTabelle.movimenti, ContentValuesFromTabella(tabella), "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore<Long> Inserisci(movimenti tabella)
    {
        return db.insert(eTabelle.movimenti, ContentValuesFromTabella(tabella));
    }

    @Override
    public movimenti Carica(Integer ID)
    {
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.Carica, new QPar[]{new QPar("ID", ID)});

        return TabellaFromCursore(c);
    }

    /**
     * Ricerca dei movimenti
     *
     * @param parametri: tipo, dataDA, dataA, soldiDA, soldiA, descrizione, MacroArea, bData, bSoldi
     * @return lista di movimenti
     */
    @Override
    public ArrayList<movimenti> Ricerca(QPar[] parametri)
    {
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.Ricerca, parametri);

        return TabelleFromCursore(c);
    }

    /**
     * Ricerca della macro area per questa descrizione
     *
     * @param parametri: descrizione
     * @return macro area
     */
    public String getMacroAreaForDescrizione(QPar[] parametri)
    {
        String r = "";
        ArrayList<String> t = new ArrayList<String>();
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.getMacroAreaForDescrizione, parametri, 1);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                t.add(c.getString(0));
            }
        }

        c.close();

        if (t.size() > 0)
        {
            r = t.get(0);
        }

        return r;
    }

    public ArrayList<String> getDescrizioni()
    {
        ArrayList<String> t = new ArrayList<String>();
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.Descrizioni, null, -1);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                t.add(c.getString(0));
            }
        }

        c.close();

        return t;
    }

    public ArrayList<String> getMacroAree()
    {
        ArrayList<String> t = new ArrayList<String>();
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.MacroAree, null, -1);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                t.add(c.getString(0));
            }
        }

        c.close();

        return t;
    }

    public ArrayList<grafico_anno> Grafico_Annuale(QPar[] parametri)
    {
        ArrayList<grafico_anno> t = new ArrayList<grafico_anno>();
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.GraficoAnnuale, parametri, -1);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                grafico_anno g = new grafico_anno();

                String a = c.getString(0);
                g.importo = c.getDouble(1);
                g.anno = Integer.valueOf(a);

                t.add(g);
            }
        }

        c.close();

        return t;
    }

    public ArrayList<grafico_mese> Grafico_Mensile(QPar[] parametri)
    {
        ArrayList<grafico_mese> t = new ArrayList<grafico_mese>();
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.GraficoMensile, parametri, -1);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                grafico_mese g = new grafico_mese();

                String a = c.getString(0);
                String m = c.getString(1);
                g.importo = c.getDouble(2);
                g.anno = Integer.valueOf(a);
                g.mese = Integer.valueOf(m);

                t.add(g);
            }
        }

        c.close();

        return t;
    }


    /**
     * Saldo dei movimenti per parametri
     *
     * @param parametri: tipo, dataDA, dataA, soldiDA, soldiA, descrizione, MacroArea, bData, bSoldi
     * @return saldo
     */
    public Double SaldoByParams(QPar[] parametri)
    {
        Double s = 0d;
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.SaldoByParams, parametri);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                s = c.getDouble(0);
            }
        }

        return s;
    }

    public Double Saldo()
    {
        Double s = 0d;
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.Saldo, null);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                s = c.getDouble(0);
            }
        }

        return s;
    }

    public Double SaldoByTipo(String tipo_)
    {
        Double s = 0d;
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.SaldoByTipo, new QPar[]{new QPar("tipo", tipo_)});

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                s = c.getDouble(0);
            }
        }

        return s;
    }

    public ArrayList<Saldo> Saldi()
    {
        ArrayList<Saldo> t = new ArrayList<Saldo>();
        Cursor c = db.rawQuery(eTabelle.movimenti, eTipi.Saldi, null);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                Saldo s = new Saldo();

                s.tipo = c.getString(0);
                s.soldi = c.getDouble(1);

                t.add(s);
            }
        }

        c.close();

        return t;
    }


}