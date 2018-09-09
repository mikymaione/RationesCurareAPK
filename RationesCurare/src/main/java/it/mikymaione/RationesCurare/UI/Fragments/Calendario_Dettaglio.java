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