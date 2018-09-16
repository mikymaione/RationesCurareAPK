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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Tables.movimenti_ricerca;
import it.mikymaione.RationesCurare.DB.Wrappers.cCasse;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Editors.DateEditText;
import it.mikymaione.RationesCurare.UI.Templates.baseDB;


public class Ricerca extends baseDB
{

    private DateEditText eDataDa, eDataA;
    private EditText eSoldiDa, eSoldiA;
    private Spinner eCassa;
    private AutoCompleteTextView eMacroArea, eDescrizione;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        cMovimenti m = new cMovimenti(DB);
        cCasse c = new cCasse(DB);

        ArrayList<String> cas = c.GetCasse();
        cas.add(0, "");

        eDescrizione = (AutoCompleteTextView) MyView.findViewById(R.id.eDescrizione);
        eMacroArea = (AutoCompleteTextView) MyView.findViewById(R.id.eMacroArea);
        eCassa = (Spinner) MyView.findViewById(R.id.eCassa);
        eSoldiDa = (EditText) MyView.findViewById(R.id.eSoldiDa);
        eSoldiA = (EditText) MyView.findViewById(R.id.eSoldiA);
        eDataDa = (DateEditText) MyView.findViewById(R.id.eDataDa);
        eDataA = (DateEditText) MyView.findViewById(R.id.eDataA);

        eDescrizione.setText("");
        eMacroArea.setText("");
        eSoldiDa.setText("");
        eSoldiA.setText("");
        eDataDa.setText("");
        eDataA.setText("");

        eMacroArea.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, m.getMacroAree()));
        eDescrizione.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, m.getDescrizioni()));
        eCassa.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, cas));

        return MyView;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_ricerca;
    }

    @Override
    public CharSequence GetTitolo()
    {
        return "Ricerca";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Cerca)
        {
            Cerca();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return null; //sceglie l'utente quale cliccare
    }

    private void Cerca()
    {
        movimenti_ricerca r = new movimenti_ricerca();

        String soldiDa = eSoldiDa.getText().toString();
        String soldiA = eSoldiA.getText().toString();

        if (eDataDa.isSetted() || eDataA.isSetted())
        {
            r.dataDa = eDataDa.getDate();
            r.dataA = eDataA.getDate();
            r.bData = true;
        }

        if (soldiDa.equals("") && soldiA.equals(""))
        {
            r.bSoldi = false;
        }
        else
        {
            r.bSoldi = true;
            r.soldiDa = GB.StringToDouble(soldiDa);
            r.soldiA = GB.StringToDouble(soldiA);
        }

        r.descrizione = eDescrizione.getText().toString();
        r.MacroArea = eMacroArea.getText().toString();

        r.cassa = (String) eCassa.getSelectedItem();

        GB.NavigateTo(this, PB.newMovimenti(this, ePosizione.Casse, r), ePosizione.RisultatoRicerca);
    }


}