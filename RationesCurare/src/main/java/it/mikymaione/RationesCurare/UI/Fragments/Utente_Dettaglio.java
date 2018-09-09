package it.mikymaione.RationesCurare.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import it.mikymaione.RationesCurare.DB.Tables.utenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseDati;


public class Utente_Dettaglio extends baseDati<Integer>
{
    private EditText eNome, eEmail, ePsw;
    private TextView welcome1, welcome2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        eNome = (EditText) MyView.findViewById(R.id.eNome);
        eEmail = (EditText) MyView.findViewById(R.id.eEmail);
        ePsw = (EditText) MyView.findViewById(R.id.ePsw);
        welcome1 = (TextView) MyView.findViewById(R.id.welcome1);
        welcome2 = (TextView) MyView.findViewById(R.id.welcome2);

        Carica();

        return MyView;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_utente__dettaglio;
    }

    @Override
    public CharSequence GetTitolo()
    {
        if (MyID > -1)
        {
            return "Dettaglio utente " + MyID;
        }
        else
        {
            return "Nuovo utente";
        }
    }

    @Override
    protected cErrore<Long> Salva()
    {
        cUtenti c = new cUtenti(DB);
        utenti u = new utenti();

        u.nome = eNome.getText().toString();
        u.psw = ePsw.getText().toString();
        u.Email = eEmail.getText().toString();

        if (MyID > -1)
            u.UltimaModifica = new Date();

        cErrore<Long> zzz = c.Salva(MyID, u);
        GB.Esiste1Utente = (zzz.risultato > 0);

        return zzz;
    }

    @Override
    protected void SettaDatiNeiControlli()
    {
        cUtenti c = new cUtenti(DB);
        utenti u = c.Carica(MyID);

        welcome1.setVisibility(View.GONE);
        welcome2.setVisibility(View.GONE);

        eNome.setEnabled(false);
        eNome.setText(u.nome);
        eEmail.setText(u.Email);
        ePsw.setText(u.psw);
    }

    @Override
    protected void Svuota()
    {
        welcome1.setVisibility(View.VISIBLE);
        welcome2.setVisibility(View.VISIBLE);
        eNome.setText("");
        eEmail.setText("");
        ePsw.setText("");
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        if (eNome.isEnabled())
        {
            return eNome;
        }
        else
        {
            return eEmail;
        }
    }

    @Override
    protected void TornaAlPadre()
    {
        GB.NavigateTo(this, PB.newCasse(), ePosizione.Casse);
    }


}