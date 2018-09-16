/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.Globals;

import android.os.Bundle;

import java.util.Calendar;

import it.mikymaione.RationesCurare.DB.Tables.movimenti_ricerca;
import it.mikymaione.RationesCurare.UI.Fragments.Calendario;
import it.mikymaione.RationesCurare.UI.Fragments.Calendario_Dettaglio;
import it.mikymaione.RationesCurare.UI.Fragments.Casse;
import it.mikymaione.RationesCurare.UI.Fragments.DataBaseSync;
import it.mikymaione.RationesCurare.UI.Fragments.GestioneCasse;
import it.mikymaione.RationesCurare.UI.Fragments.GestioneCasse_Dettaglio;
import it.mikymaione.RationesCurare.UI.Fragments.Grafico;
import it.mikymaione.RationesCurare.UI.Fragments.Movimenti;
import it.mikymaione.RationesCurare.UI.Fragments.MovimentiPeriodici;
import it.mikymaione.RationesCurare.UI.Fragments.MovimentiPeriodici_Dettaglio;
import it.mikymaione.RationesCurare.UI.Fragments.Movimenti_Dettaglio;
import it.mikymaione.RationesCurare.UI.Fragments.Ricerca;
import it.mikymaione.RationesCurare.UI.Fragments.Utente_Dettaglio;
import it.mikymaione.RationesCurare.UI.Fragments.Utente_Login;
import it.mikymaione.RationesCurare.UI.Fragments.ePosizione;
import it.mikymaione.RationesCurare.UI.Templates.baseDB;
import it.mikymaione.RationesCurare.UI.Templates.baseDati;
import it.mikymaione.RationesCurare.UI.Templates.baseFragment;

/**
 * Created by michele on 29/03/15.
 */
public class PB
{
    private static Bundle bundle = null;

    private static Bundle getBundle()
    {
        if (bundle == null)
        {
            bundle = new Bundle();
        }

        return bundle;
    }

    private static void baseDatiSet(baseDati fragment, baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, int MyID)
    {
        baseDBSet(fragment, Padre_Fragment_, Padre_Posizione_);
        getBundle().putInt(fragment.ARG_MyID, MyID);
    }

    private static void baseDBSet(baseDB fragment, baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        getBundle().putSerializable(fragment.ARG_Padre_Fragment, Padre_Fragment_);
        getBundle().putSerializable(fragment.ARG_Padre_Posizione, Padre_Posizione_);
    }

    public static baseFragment newCasse(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        Casse f = new Casse();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newCasse()
    {
        Casse f = new Casse();

        return f;
    }

    public static baseFragment newCalendario(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        Calendario f = new Calendario();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);
        f.setArguments(getBundle());
        return f;
    }

    public static baseFragment newMovimentiPeriodici(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        MovimentiPeriodici f = new MovimentiPeriodici();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);
        f.setArguments(getBundle());
        return f;
    }

    public static baseFragment newGrafico(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        Grafico f = new Grafico();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newGestioneCasse(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        GestioneCasse f = new GestioneCasse();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newUtente_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, Integer myID)
    {
        Utente_Dettaglio f = new Utente_Dettaglio();
        baseDatiSet(f, Padre_Fragment_, Padre_Posizione_, myID);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newUtente_Dettaglio(Integer myID)
    {
        Utente_Dettaglio f = new Utente_Dettaglio();
        getBundle().putInt(f.ARG_MyID, myID);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newMovimenti(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, String cassa_)
    {
        Movimenti f = new Movimenti();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);

        getBundle().putString(f.ARG_Cassa, cassa_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newMovimenti(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, movimenti_ricerca ricerca_)
    {
        Movimenti f = new Movimenti();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);
        getBundle().putSerializable(f.ARG_movimenti_ricerca, ricerca_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newMovimenti_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, Integer myID, String Cassa_)
    {
        Movimenti_Dettaglio f = new Movimenti_Dettaglio();
        baseDatiSet(f, Padre_Fragment_, Padre_Posizione_, myID);
        getBundle().putString(f.ARG_Cassa, Cassa_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newMovimenti_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, Integer myID, String Cassa_, boolean UsaGiroconto_)
    {
        Movimenti_Dettaglio f = new Movimenti_Dettaglio();
        baseDatiSet(f, Padre_Fragment_, Padre_Posizione_, myID);

        getBundle().putString(f.ARG_Cassa, Cassa_);
        getBundle().putBoolean(f.ARG_UsaGiroconto, UsaGiroconto_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newRicerca(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_)
    {
        Ricerca f = new Ricerca();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newRicerca()
    {
        Ricerca f = new Ricerca();

        return f;
    }

    public static baseFragment newCalendario_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, Integer myID, Calendar Datascelta_)
    {
        Calendario_Dettaglio f = new Calendario_Dettaglio();
        baseDatiSet(f, Padre_Fragment_, Padre_Posizione_, myID);
        getBundle().putSerializable(f.ARG_Datascelta, Datascelta_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newGestioneCasse_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, String myID)
    {
        GestioneCasse_Dettaglio f = new GestioneCasse_Dettaglio();
        baseDBSet(f, Padre_Fragment_, Padre_Posizione_);
        getBundle().putString(f.ARG_MyID, myID);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newMovimentiPeriodici_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, Integer myID, String Cassa_)
    {
        MovimentiPeriodici_Dettaglio f = new MovimentiPeriodici_Dettaglio();
        baseDatiSet(f, Padre_Fragment_, Padre_Posizione_, myID);

        getBundle().putString(f.ARG_Cassa, Cassa_);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newMovimentiPeriodici_Dettaglio(baseFragment Padre_Fragment_, ePosizione Padre_Posizione_, Integer myID)
    {
        MovimentiPeriodici_Dettaglio f = new MovimentiPeriodici_Dettaglio();
        baseDatiSet(f, Padre_Fragment_, Padre_Posizione_, myID);

        f.setArguments(getBundle());

        return f;
    }

    public static baseFragment newCalendario()
    {
        return new Calendario();
    }

    public static baseFragment newMovimentiPeriodici()
    {
        return new MovimentiPeriodici();
    }

    public static baseFragment newGrafico()
    {
        return new Grafico();
    }

    public static baseFragment newGestioneCasse()
    {
        return new GestioneCasse();
    }

    public static baseFragment newUtente_Login()
    {
        return new Utente_Login();
    }

    public static baseFragment newDataBaseSync()
    {
        return new DataBaseSync();
    }


}