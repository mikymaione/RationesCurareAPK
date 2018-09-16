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
import java.util.Date;

import it.mikymaione.RationesCurare.DB.Tables.utenti;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Templates.cTabella;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.DB.eTabelle;
import it.mikymaione.RationesCurare.DB.eTipi;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;

/**
 * Created by michele on 21/03/15.
 */
public class cUtenti extends cTabella<utenti, Integer>
{

    public cUtenti(cDB db)
    {
        super(db);
    }

    @Override
    protected ContentValues ContentValuesFromTabella(utenti tabella)
    {
        ContentValues v = new ContentValues();
        v.put("nome", tabella.nome);
        v.put("psw", tabella.psw);
        v.put("path", tabella.path);
        v.put("Email", tabella.Email);
        v.put("UltimaModifica", GB.DateToSQLite(tabella.UltimaModifica, new Date(90, 1, 1, 0, 0, 0)));

        return v;
    }

    @Override
    protected utenti CaricaDaCursore(Cursor c)
    {
        int j = -1;
        utenti e = new utenti();

        e.ID = c.getInt(j += 1);
        e.nome = c.getString(j += 1);
        e.psw = c.getString(j += 1);
        e.path = c.getString(j += 1);
        e.Email = c.getString(j += 1);
        e.UltimaModifica = GB.SQLiteDateToDate(c.getString(j += 1));

        return e;
    }

    @Override
    protected cErrore<Boolean> ControllaPrimaDiSalvare(Integer ID, utenti tabella)
    {
        String e = "";

        if (tabella.nome == null || tabella.nome.length() < 3)
        {
            e = "Nome troppo breve!";
        }
        if (tabella.psw == null || tabella.psw.length() < 2)
        {
            e = "Password troppo breve!";
        }
        if (tabella.Email == null || tabella.Email.length() < 8)
        {
            e = "Email troppo breve!";
        }

        return new cErrore<Boolean>(e, e.equals(""));
    }

    @Override
    protected cErrore<Long> Elimina(Integer ID)
    {
        return db.delete(eTabelle.utenti, "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore<Long> Modifica(Integer ID, utenti tabella)
    {
        return db.update(eTabelle.utenti, ContentValuesFromTabella(tabella), "ID = ?", new String[]{ID.toString()});
    }

    @Override
    protected cErrore<Long> Inserisci(utenti tabella)
    {
        return db.insert(eTabelle.utenti, ContentValuesFromTabella(tabella));
    }

    @Override
    public utenti Carica(Integer ID)
    {
        Cursor c = db.rawQuery(eTabelle.utenti, eTipi.Carica, new QPar[]{new QPar("ID", ID)});

        return TabellaFromCursore(c);
    }

    public utenti Carica()
    {
        ArrayList<utenti> u = Ricerca(null);

        if (u != null && u.size() > 0)
        {
            return u.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public ArrayList<utenti> Ricerca(QPar[] parametri)
    {
        Cursor c = db.rawQuery(eTabelle.utenti, eTipi.Ricerca, parametri);

        return TabelleFromCursore(c);
    }

    public boolean Esiste1Utente()
    {
        ArrayList<utenti> u = Ricerca(null);

        return (u != null && u.size() > 0);
    }

    public Integer getMioID()
    {
        Integer r = -1;
        ArrayList<utenti> u = Ricerca(null);

        if (u != null && u.size() > 0)
        {
            r = u.get(0).ID;
        }

        return r;
    }

    public String getMioNome()
    {
        String r = "";
        ArrayList<utenti> u = Ricerca(null);

        if (u != null && u.size() > 0)
        {
            r = u.get(0).nome;
        }

        return r;
    }

}