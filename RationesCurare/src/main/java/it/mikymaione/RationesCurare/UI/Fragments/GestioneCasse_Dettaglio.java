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

import it.mikymaione.RationesCurare.DB.Tables.Cassa;
import it.mikymaione.RationesCurare.DB.Wrappers.cCasse;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseDati;


public class GestioneCasse_Dettaglio extends baseDati<String>
{
    private EditText eNome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        eNome = (EditText) MyView.findViewById(R.id.eNome);

        Carica();

        return MyView;
    }

    @Override
    protected cErrore<Long> Salva()
    {
        cCasse d = new cCasse(DB);
        Cassa m = new Cassa();

        m.nome = eNome.getText().toString();

        return d.Salva(MyID, m);
    }

    @Override
    protected void SettaDatiNeiControlli()
    {
        cCasse c = new cCasse(DB);
        Cassa tabella = c.Carica(MyID);

        eNome.setText(tabella.nome);
    }

    @Override
    protected void Svuota()
    {
        eNome.setText("");
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return eNome;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_gestione_casse__dettaglio;
    }

    @Override
    public CharSequence GetTitolo()
    {
        if (MyID != null && !MyID.equals(""))
        {
            return "Dettaglio cassa " + MyID;
        }
        else
        {
            return "Nuova cassa";
        }
    }


}