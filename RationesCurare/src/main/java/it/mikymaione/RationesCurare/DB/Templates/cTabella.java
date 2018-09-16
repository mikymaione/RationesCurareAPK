/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.DB.Templates;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.Globals.cErrore;

/**
 * Created by michele on 06/03/15.
 */
public abstract class cTabella<T extends baseTabellaDB, V>
{
    protected cDB db = null;

    public cTabella(cDB db)
    {
        this.db = db;
    }

    public cErrore<Long> Salva(V ID, T tabella)
    {
        cErrore<Boolean> e = ControllaPrimaDiSalvare(ID, tabella);

        if (e.risultato)
        {
            if (ID == null || ID.equals(-1) || ID.equals(""))
            {
                return Inserisci(tabella);
            }
            else
            {
                return Modifica(ID, tabella);
            }
        }

        return new cErrore<Long>(e.testo, 0l);
    }

    protected T TabellaFromCursore(Cursor c)
    {
        ArrayList<T> t = TabelleFromCursore(c);

        if (t.size() > 0)
        {
            return t.get(0);
        }
        else
        {
            return null;
        }
    }

    protected ArrayList<T> TabelleFromCursore(Cursor c)
    {
        ArrayList<T> t = new ArrayList<T>();

        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                t.add(CaricaDaCursore(c));
            }
        }

        c.close();

        return t;
    }

    public ArrayList<Object[]> ToArrayListOfObjects(String[] fields_name, ArrayList<?> l)
    {
        ArrayList<Object[]> r = new ArrayList<>();

        for (Object e : l)
        {
            Class gc = e.getClass();
            Object[] z = new Object[fields_name.length];

            for (int i = 0; i < fields_name.length; i++)
            {
                boolean settato = false;
                Field d = null;

                try
                {
                    d = gc.getField(fields_name[i]);
                }
                catch (Exception e1)
                {
                    //non esiste
                }

                if (d == null) //proviamo con un metodo
                {
                    Method m = null;

                    try
                    {
                        //m = gc.getMethod(fields_name[i], null);
                        m = gc.getMethod(fields_name[i], new Class<?>[0]);
                    }
                    catch (Exception e2)
                    {
                        //non esiste
                    }

                    if (m != null)
                    {
                        try
                        {
                            z[i] = m.invoke(e);
                            settato = true;
                        }
                        catch (Exception e3)
                        {
                            //errore
                        }
                    }
                }
                else
                {
                    try
                    {
                        z[i] = d.get(e);
                        settato = true;
                    }
                    catch (Exception e3)
                    {
                        //errore
                    }
                }

                if (!settato)
                {
                    z[i] = "";
                }
            }

            r.add(z);
        }

        return r;
    }

    protected abstract ContentValues ContentValuesFromTabella(T tabella);

    protected abstract T CaricaDaCursore(Cursor c);

    protected abstract cErrore<Boolean> ControllaPrimaDiSalvare(V ID, T tabella);

    protected abstract cErrore<Long> Elimina(V ID);

    protected abstract cErrore<Long> Modifica(V ID, T tabella);

    protected abstract cErrore<Long> Inserisci(T tabella);

    public abstract T Carica(V ID);

    public abstract ArrayList<T> Ricerca(QPar[] parametri);


}