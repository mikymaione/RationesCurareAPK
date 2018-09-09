package it.mikymaione.RationesCurare.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import it.mikymaione.RationesCurare.DB.Tables.MovimentiTempo;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Wrappers.cCasse;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimentiTempo;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Editors.DateEditText;
import it.mikymaione.RationesCurare.UI.Templates.baseDati;


public class MovimentiPeriodici_Dettaglio extends baseDati<Integer>
{
    public static final String ARG_Cassa = "Cassa";
    private String Cassa = "";
    private Spinner eCassa, eTipoGiorniMese;
    private EditText eSoldi, eNome, eNumeroGiorni;
    private AutoCompleteTextView eMacroArea, eDescrizione;
    private DateEditText eGiornoDelMese, eScadenza;

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        Cassa = (String) this.BundleGetArg(ARG_Cassa);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        eCassa = (Spinner) MyView.findViewById(R.id.eCassa);
        eTipoGiorniMese = (Spinner) MyView.findViewById(R.id.eTipoGiorniMese);

        eNome = (EditText) MyView.findViewById(R.id.eNome);
        eSoldi = (EditText) MyView.findViewById(R.id.eSoldi);
        eNumeroGiorni = (EditText) MyView.findViewById(R.id.eNumeroGiorni);

        eDescrizione = (AutoCompleteTextView) MyView.findViewById(R.id.eDescrizione);
        eMacroArea = (AutoCompleteTextView) MyView.findViewById(R.id.eMacroArea);

        eGiornoDelMese = (DateEditText) MyView.findViewById(R.id.eGiornoDelMese);
        eScadenza = (DateEditText) MyView.findViewById(R.id.eScadenza);

        cMovimenti c = new cMovimenti(DB);
        cMovimentiTempo t = new cMovimentiTempo(DB);
        cCasse x = new cCasse(DB);

        eCassa.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, x.GetCasse()));
        eMacroArea.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, c.getMacroAree()));

        eTipoGiorniMese.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, t.getTipoGiorniMese()));
        eTipoGiorniMese.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (view instanceof TextView)
                {
                    TextView z = (TextView) view;
                    CambiaPeriodicita(z.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //nulla
            }
        });

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

        if (Cassa != null && !Cassa.equals(""))
        {
            eCassa.setEnabled(false);
        }

        Carica();

        return MyView;
    }

    private void CambiaPeriodicita(String periodicita)
    {
        cMovimentiTempo d = new cMovimentiTempo(DB);
        String k = d.getKeyOfGiorniMeseByVal(periodicita);

        if (k != null && k.length() > 0)
        {
            CambiaPeriodicita(k.charAt(0));
        }
    }

    private void CambiaPeriodicita(char key)
    {
        boolean isMese = (key == 'M');

        eGiornoDelMese.setEnabled(isMese);
        eNumeroGiorni.setEnabled(!isMese);

        if (isMese)
        {
            eNumeroGiorni.setText("");
        }
        else
        {
            eGiornoDelMese.setText("");
        }
    }

    @Override
    protected cErrore<Long> Salva()
    {
        cMovimentiTempo d = new cMovimentiTempo(DB);
        MovimentiTempo m = new MovimentiTempo();

        m.ID = MyID;
        m.tipo = (String) eCassa.getSelectedItem();
        m.Scadenza = eScadenza.getDateTime();
        m.descrizione = eDescrizione.getText().toString();
        m.MacroArea = eMacroArea.getText().toString();
        m.nome = eNome.getText().toString();
        m.soldi = GB.StringToDouble(eSoldi.getText().toString());
        m.NumeroGiorni = GB.StringToInteger(eNumeroGiorni.getText().toString());
        m.GiornoDelMese = eGiornoDelMese.getDateTime();
        m.TipoGiorniMese = d.getKeyOfGiorniMeseByVal((String) eTipoGiorniMese.getSelectedItem());

        return d.Salva(MyID, m);
    }

    @Override
    protected void SettaDatiNeiControlli()
    {
        cMovimentiTempo c = new cMovimentiTempo(DB);
        MovimentiTempo m = c.Carica(MyID);

        eDescrizione.setText(m.descrizione);
        eMacroArea.setText(m.MacroArea);
        eNome.setText(m.nome);
        eSoldi.setText(GB.DoubleToString(m.soldi));
        eScadenza.setText(GB.DateToString(m.Scadenza, false));
        eNumeroGiorni.setText(GB.IntegerToString(m.NumeroGiorni));
        eGiornoDelMese.setText(GB.DateToString(m.GiornoDelMese, false));

        SelezionaInSpinner(eTipoGiorniMese, c.getValueOfGiorniMeseByVal(m.TipoGiorniMese));
        SelezionaInSpinner(eCassa, m.tipo);

        CambiaPeriodicita(m.getTipoGiorniMese());
    }

    @Override
    protected void Svuota()
    {
        cUtenti u = new cUtenti(DB);

        eNome.setText(u.getMioNome());
        eSoldi.setText("");
        eNumeroGiorni.setText("");

        eDescrizione.setText("");
        eMacroArea.setText("");

        eGiornoDelMese.setText("");
        eScadenza.setText("");

        eGiornoDelMese.setEnabled(true);
        eNumeroGiorni.setEnabled(false);

        if (Cassa != null && !Cassa.equals(""))
        {
            SelezionaInSpinner(eCassa, Cassa);
        }
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return eSoldi;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_movimenti_periodici__dettaglio;
    }

    @Override
    public CharSequence GetTitolo()
    {
        if (MyID > -1)
        {
            return "Dettaglio periodico " + MyID;
        }
        else
        {
            return "Nuovo movimento periodico";
        }
    }


}