/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import java.util.Calendar;

import it.mikymaione.RationesCurare.DB.Tables.Calendario;
import it.mikymaione.RationesCurare.DB.Wrappers.cCalendario;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseDatiEX;


public class Calendario_Dettaglio extends baseDatiEX<Integer>
{

    public final String ARG_Datascelta = "Datascelta";

    private EditText eData;
    private MultiAutoCompleteTextView eDescrizione;
    private Calendar Datascelta;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Datascelta = (Calendar) this.BundleGetArg(ARG_Datascelta);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        eDescrizione = (MultiAutoCompleteTextView) MyView.findViewById(R.id.eDescrizione);
        eData = (EditText) MyView.findViewById(R.id.eData);

        Carica();

        return MyView;
    }

    @Override
    protected cErrore<Long> Elimina()
    {
        if (MyID != null && MyID > -1)
        {
            cCalendario c = new cCalendario(DB);

            return c.Elimina(MyID);
        }

        return null;
    }

    @Override
    protected cErrore<Long> Salva()
    {
        cCalendario d = new cCalendario(DB);
        it.mikymaione.RationesCurare.DB.Tables.Calendario m = new Calendario();

        m.ID = MyID;
        m.Giorno = GB.StringToDate(eData.getText().toString(), false);
        m.Descrizione = eDescrizione.getText().toString();

        return d.Salva(MyID, m);
    }

    @Override
    protected void SettaDatiNeiControlli()
    {
        cCalendario c = new cCalendario(DB);
        Calendario tabella = c.Carica(MyID);

        eDescrizione.setText(tabella.Descrizione);
        eData.setText(GB.DateToString(tabella.Giorno, false));
    }

    @Override
    protected void Svuota()
    {
        eDescrizione.setText("");

        if (MyID.equals(-1))
        {
            eData.setText(GB.DateToString(Datascelta));
        }
        else
        {
            eData.setText("");
        }
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return eDescrizione;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_calendario__dettaglio;
    }

    @Override
    public CharSequence GetTitolo()
    {
        if (MyID > -1)
        {
            return "Dettaglio evento " + MyID;
        }
        else
        {
            return "Nuovo evento";
        }
    }


}