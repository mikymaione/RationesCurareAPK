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
import android.widget.EditText;

import it.mikymaione.RationesCurare.DB.Tables.utenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseDati;

public class Utente_Login extends baseDati<Integer>
{

    private EditText eEmail, ePsw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        eEmail = (EditText) MyView.findViewById(R.id.eEmail);
        ePsw = (EditText) MyView.findViewById(R.id.ePsw);

        Carica();

        return MyView;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_utente__login;
    }

    @Override
    public CharSequence GetTitolo()
    {
        return "Login";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Sincronizza)
        {
            cErrore<Long> zzz = Salva();

            if (zzz.risultato > 0)
                GB.NavigateTo(this, PB.newDataBaseSync(), ePosizione.DataBaseSync);
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
        return eEmail;
    }

    @Override
    protected cErrore<Long> Salva()
    {
        cUtenti c = new cUtenti(DB);
        utenti u = new utenti();

        u.psw = ePsw.getText().toString();
        u.Email = eEmail.getText().toString();
        u.nome = u.Email;

        cErrore<Long> zzz = c.Salva(-1, u);
        GB.Esiste1Utente = (zzz.risultato > 0);

        return zzz;
    }

    @Override
    protected void SettaDatiNeiControlli()
    {
        cUtenti c = new cUtenti(DB);
        utenti u = c.Carica(MyID);

        eEmail.setText(u.Email);
        ePsw.setText(u.psw);
    }

    @Override
    protected void Svuota()
    {
        eEmail.setText("");
        ePsw.setText("");
    }


}
