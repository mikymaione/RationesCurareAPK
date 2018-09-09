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