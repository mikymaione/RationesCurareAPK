package it.mikymaione.RationesCurare.UI.Fragments;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Wrappers.cMovimentiTempo;
import it.mikymaione.RationesCurare.Globals.Costanti_Tabella;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.DataGrid.DataGrid;
import it.mikymaione.RationesCurare.UI.Templates.baseRicerca;

public class MovimentiPeriodici extends baseRicerca<Integer>
{

    @Override
    protected void Dettaglio(boolean Nuovo)
    {
        boolean vai = true;
        Integer ID_ = -1;

        if (!Nuovo)
        {
            ID_ = GetSelectedItem();

            vai = (ID_ != null && ID_ > -1);
        }

        if (vai)
        {
            GB.NavigateTo(this, PB.newMovimentiPeriodici_Dettaglio(this, ePosizione.Periodici, ID_), ePosizione.Periodici_Dettaglio);
        }
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
                CreaColonna("ID", "ID", Costanti_Tabella.ID),
                CreaColonna("soldi", "Soldi", Costanti_Tabella.Soldi),
                CreaColonna("tipo", "Cassa", Costanti_Tabella.StringaBreve),

                CreaColonna("NumeroGiorni", "N. giorni", Costanti_Tabella.NumeroBreve),
                CreaColonna("PartendoDalGiorno", "Partendo da", Costanti_Tabella.Data),

                CreaColonna("GiornoDelMese_SoloGiorno", "Giorno del mese", Costanti_Tabella.NumeroBreve),
                CreaColonna("Periodo", "Periodo", Costanti_Tabella.ID),

                CreaColonna("descrizione", "Descrizione", Costanti_Tabella.StringaBreve),
                CreaColonna("MacroArea", "Macroarea", Costanti_Tabella.StringaBreve),

                CreaColonna("nome", "Nome", Costanti_Tabella.StringaBreve)
        };
    }

    @Override
    protected ArrayList<Object[]> DammiDati()
    {
        cMovimentiTempo m = new cMovimentiTempo(DB);

        return m.ToArrayListOfObjects(GetFieldsName(), m.Ricerca(null));
    }

    @Override
    protected cErrore<Long> Elimina()
    {
        cMovimentiTempo c = new cMovimentiTempo(DB);

        return c.Elimina(GetSelectedItem());
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_movimenti_periodici;
    }

    @Override
    public CharSequence GetTitolo()
    {
        return "Movimenti periodici";
    }
}