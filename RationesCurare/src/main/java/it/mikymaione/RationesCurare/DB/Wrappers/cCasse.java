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

import it.mikymaione.RationesCurare.DB.Tables.Cassa;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Templates.cTabella;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.DB.eTabelle;
import it.mikymaione.RationesCurare.DB.eTipi;
import it.mikymaione.RationesCurare.Globals.cErrore;

public class cCasse extends cTabella<Cassa, String>
{

    public cCasse(cDB db)
    {
        super(db);
    }

    @Override
    protected ContentValues ContentValuesFromTabella(Cassa tabella)
    {
        ContentValues v = new ContentValues();
        v.put("nome", tabella.nome);
        v.put("imgName", tabella.imgName);

        return v;
    }

    @Override
    protected Cassa CaricaDaCursore(Cursor c)
    {
        Cassa e = new Cassa();

        e.nome = c.getString(0);
        e.imgName = c.getBlob(1);

        return e;
    }

    @Override
    protected cErrore<Boolean> ControllaPrimaDiSalvare(String ID, Cassa tabella)
    {
        String e = "";

        if (tabella.nome == null || tabella.nome.length() < 3)
        {
            e = "Il nome è troppo breve!";
        }
        //continuare se mette l'immagine

        return new cErrore<Boolean>(e, e.equals(""));
    }

    @Override
    protected cErrore<Long> Elimina(String ID)
    {
        return db.delete(eTabelle.Casse, "nome = ?", new String[]{ID});
    }

    @Override
    protected cErrore<Long> Modifica(String ID, Cassa tabella)
    {
        return db.update(eTabelle.Casse, ContentValuesFromTabella(tabella), "nome = ?", new String[]{ID});
    }

    @Override
    protected cErrore<Long> Inserisci(Cassa tabella)
    {
        return db.insert(eTabelle.Casse, ContentValuesFromTabella(tabella));
    }

    @Override
    public Cassa Carica(String ID)
    {
        Cursor c = db.rawQuery(eTabelle.Casse, eTipi.Carica, new QPar[]{new QPar("nome", ID)});

        return TabellaFromCursore(c);
    }

    @Override
    public ArrayList<Cassa> Ricerca(QPar[] parametri)
    {
        Cursor c = db.rawQuery(eTabelle.Casse, eTipi.Ricerca, parametri);

        return TabelleFromCursore(c);
    }

    public ArrayList<String> GetCasse()
    {
        return GetCasse("");
    }

    public ArrayList<String> GetCasse(String escludi)
    {
        ArrayList<String> r = new ArrayList<String>();
        Cursor c = db.rawQuery(eTabelle.Casse, eTipi.Ricerca, null);

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                String z = c.getString(0);

                if (!z.equalsIgnoreCase(escludi))
                {
                    r.add(z);
                }
            }
        }

        c.close();

        return r;
    }


}