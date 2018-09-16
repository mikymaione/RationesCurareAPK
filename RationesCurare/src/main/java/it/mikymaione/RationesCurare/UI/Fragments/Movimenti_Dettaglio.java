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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import it.mikymaione.RationesCurare.DB.Tables.movimenti;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Wrappers.cCasse;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Editors.DateEditText;
import it.mikymaione.RationesCurare.UI.Editors.TimeEditText;
import it.mikymaione.RationesCurare.UI.Templates.baseDati;


public class Movimenti_Dettaglio extends baseDati<Integer>
{

    public final String ARG_Cassa = "Cassa";
    public final String ARG_UsaGiroconto = "UsaGiroconto";

    private String Cassa = "";
    private Boolean UsaGiroconto = false;

    private DateEditText eData;
    private TimeEditText eData2;
    private EditText eSoldi, eNome;
    private AutoCompleteTextView eMacroArea, eDescrizione;
    private TextView tCassaGiroconto;
    private Spinner eCassaGiroconto;

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        Cassa = (String) this.BundleGetArg(ARG_Cassa);
        UsaGiroconto = (Boolean) this.BundleGetArg(ARG_UsaGiroconto);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        cMovimenti c = new cMovimenti(DB);
        cCasse z = new cCasse(DB);

        eDescrizione = (AutoCompleteTextView) MyView.findViewById(R.id.eDescrizione);
        eMacroArea = (AutoCompleteTextView) MyView.findViewById(R.id.eMacroArea);
        eNome = (EditText) MyView.findViewById(R.id.eNome);
        eSoldi = (EditText) MyView.findViewById(R.id.eSoldi);
        eData = (DateEditText) MyView.findViewById(R.id.eData);
        eData2 = (TimeEditText) MyView.findViewById(R.id.eData2);
        eCassaGiroconto = (Spinner) MyView.findViewById(R.id.eCassaGiroconto);
        tCassaGiroconto = (TextView) MyView.findViewById(R.id.tCassaGiroconto);

        if (UsaGiroconto)
        {
            MyID = -1; //deve essere per forza nuovo
            tCassaGiroconto.setVisibility(View.VISIBLE);
            eCassaGiroconto.setVisibility(View.VISIBLE);
            eCassaGiroconto.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, z.GetCasse(Cassa)));
        }

        eMacroArea.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, c.getMacroAree()));
        eDescrizione.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, c.getDescrizioni()));
        eDescrizione.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (eMacroArea.getText().length() < 1 && eDescrizione.getText().length() > 0)
                {
                    cMovimenti d = new cMovimenti(DB);
                    String k = d.getMacroAreaForDescrizione(new QPar[]{new QPar("descrizione", eDescrizione.getText().toString())});
                    eMacroArea.setText(k);
                }
            }
        });

        Carica();

        return MyView;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_movimenti__dettaglio;
    }

    @Override
    public CharSequence GetTitolo()
    {
        if (UsaGiroconto)
        {
            return "Nuovo giroconto";
        }
        else
        {
            if (MyID > -1)
            {
                return "Dettaglio movimento " + MyID;
            }
            else
            {
                return "Nuovo movimento";
            }
        }
    }

    @Override
    protected cErrore<Long> Salva()
    {
        cMovimenti d = new cMovimenti(DB);
        movimenti m = new movimenti();

        m.ID = MyID;
        m.tipo = Cassa;
        m.data = GB.DateAndTime(eData.getDate(), eData2.getTime());
        m.descrizione = eDescrizione.getText().toString();
        m.MacroArea = eMacroArea.getText().toString();
        m.nome = eNome.getText().toString();
        m.soldi = GB.StringToDouble(eSoldi.getText().toString());

        cErrore<Long> e = d.Salva(MyID, m);

        if (UsaGiroconto && e.risultato > 0)
        {
            m.tipo = (String) eCassaGiroconto.getSelectedItem();
            m.soldi *= -1;

            return d.Salva(-1, m);
        }

        return e;
    }

    @Override
    protected void SettaDatiNeiControlli()
    {
        cMovimenti c = new cMovimenti(DB);
        movimenti tabella = c.Carica(MyID);

        Cassa = tabella.tipo;
        eDescrizione.setText(tabella.descrizione);
        eMacroArea.setText(tabella.MacroArea);
        eNome.setText(tabella.nome);
        eSoldi.setText(GB.DoubleToString(tabella.soldi));
        eData.setText(GB.DateToString(tabella.data, false));
        eData2.setText(GB.TimeToString(tabella.data));
    }

    @Override
    protected void Svuota()
    {
        cUtenti u = new cUtenti(DB);

        eNome.setText(u.getMioNome());
        eDescrizione.setText("");
        eMacroArea.setText("");
        eSoldi.setText("");
        eData.SetNow();
        eData2.SetNow();
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return eSoldi;
    }


}