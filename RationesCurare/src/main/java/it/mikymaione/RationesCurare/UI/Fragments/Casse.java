package it.mikymaione.RationesCurare.UI.Fragments;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.Globals.Costanti_Tabella;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.DataGrid.DataGrid;
import it.mikymaione.RationesCurare.UI.Templates.baseRicerca;


public class Casse extends baseRicerca<String>
{

    @Override
    public CharSequence GetTitolo()
    {
        cMovimenti m = new cMovimenti(DB);

        return "RationesCurare: " + GB.DoubleToEuro(m.Saldo());
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_casse;
    }

    @Override
    protected int DammiGrigliaID()
    {
        return R.id.DBGrid;
    }

    @Override
    protected DataGrid.ColumnStyle[] DammiColonne()
    {
        return new DataGrid.ColumnStyle[]{
                CreaColonna("tipo", "Cassa", Costanti_Tabella.StringaBreve),
                CreaColonna("soldi", "Saldo", Costanti_Tabella.Soldi)
        };
    }

    @Override
    protected ArrayList<Object[]> DammiDati()
    {
        cMovimenti m = new cMovimenti(DB);

        return m.ToArrayListOfObjects(GetFieldsName(), m.Saldi());
    }

    @Override
    protected void Dettaglio(boolean Nuovo)
    {
        String cassa_ = "";
        Object y = GetSelectedItem();

        if (y != null && y instanceof String)
        {
            cassa_ = (String) y;
        }

        if (cassa_ != null && !cassa_.equals(""))
        {
            GB.NavigateTo(this, PB.newMovimenti(this, ePosizione.Casse, cassa_), ePosizione.Movimenti);
        }
    }

    @Override
    protected cErrore<Long> Elimina()
    {
        return new cErrore<Long>("Le casse non possono essere eliminate!", 0l);
    }


}