package it.mikymaione.RationesCurare.UI.Fragments;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Wrappers.cCasse;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.DataGrid.DataGrid.ColumnStyle;
import it.mikymaione.RationesCurare.UI.Templates.baseRicerca;

public class GestioneCasse extends baseRicerca<String>
{

    @Override
    public CharSequence GetTitolo()
    {
        return "Gestione casse";
    }

    @Override
    protected int DammiGrigliaID()
    {
        return R.id.DBGrid;
    }

    @Override
    protected ColumnStyle[] DammiColonne()
    {
        return new ColumnStyle[]{
                CreaColonna("nome", "Cassa", R.dimen.StringaBreve)
                //CreaColonna("imgName", "Immagine")
        };
    }

    @Override
    protected ArrayList<Object[]> DammiDati()
    {
        cCasse c = new cCasse(DB);

        return c.ToArrayListOfObjects(GetFieldsName(), c.Ricerca(null));
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_gestione_casse;
    }

    @Override
    protected void Dettaglio(boolean Nuovo)
    {
        boolean vai = true;
        String ID_ = "";

        if (!Nuovo)
        {
            ID_ = GetSelectedItem();
            vai = (ID_ != null && !ID_.equals(""));
        }

        if (vai)
        {
            GB.NavigateTo(this, PB.newGestioneCasse_Dettaglio(this, ePosizione.GestioneCasse, ID_), ePosizione.GestioneCasse_Dettaglio);
        }
    }

    @Override
    protected cErrore<Long> Elimina()
    {
        return new cErrore<Long>("Le casse non possono essere eliminate!", 0l);
    }


}