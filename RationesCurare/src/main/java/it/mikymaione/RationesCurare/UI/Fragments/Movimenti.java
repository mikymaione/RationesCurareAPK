package it.mikymaione.RationesCurare.UI.Fragments;


import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

import it.mikymaione.RationesCurare.DB.Tables.movimenti_ricerca;
import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.Globals.Costanti_Tabella;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.DataGrid.DataGrid;
import it.mikymaione.RationesCurare.UI.DataGrid.DataGrid.ColumnStyle;
import it.mikymaione.RationesCurare.UI.Templates.baseRicerca;

public class Movimenti extends baseRicerca<Integer>
{
    public final String ARG_Cassa = "Cassa";
    public final String ARG_movimenti_ricerca = "movimenti_ricerca";

    private String cassa = "";
    private movimenti_ricerca ricerca = null;

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        cassa = (String) this.BundleGetArg(ARG_Cassa);
        ricerca = (movimenti_ricerca) this.BundleGetArg(ARG_movimenti_ricerca);
    }

    @Override
    public CharSequence GetTitolo()
    {
        String r = "";
        Double d = 0d;
        cMovimenti m = new cMovimenti(DB);

        if (ricerca == null)
        {
            d = m.SaldoByTipo(cassa);
            r = cassa + ": ";
        }
        else
        {
            d = m.SaldoByParams(getParams());
            r = "Risultato ricerca: ";
        }

        r += GB.DoubleToEuro(d);

        return r;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Giroconto)
        {
            Dettaglio(true, true);
        }
        else if (z == R.id.action_Periodico)
        {
            GB.NavigateTo(this, PB.newMovimentiPeriodici_Dettaglio(this, ePosizione.Movimenti, -1, cassa), ePosizione.Periodici_Dettaglio);
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void Dettaglio(boolean Nuovo)
    {
        Dettaglio(Nuovo, false);
    }

    private void Dettaglio(boolean Nuovo, boolean Giroconto)
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
            GB.NavigateTo(this, PB.newMovimenti_Dettaglio(this, ePosizione.Movimenti, ID_, cassa, Giroconto), ePosizione.Movimenti_Dettaglio);
        }
    }

    @Override
    protected cErrore<Long> Elimina()
    {
        cMovimenti c = new cMovimenti(DB);

        return c.Elimina(GetSelectedItem());
    }

    @Override
    protected int DammiGrigliaID()
    {
        return R.id.DBGrid;
    }

    @Override
    protected ColumnStyle[] DammiColonne()
    {
        return new DataGrid.ColumnStyle[]{
                CreaColonna("ID", "ID", Costanti_Tabella.ID),
                CreaColonna("data", "Data", Costanti_Tabella.Data),
                CreaColonna("soldi", "Soldi", Costanti_Tabella.Soldi),
                CreaColonna("tipo", "Cassa", Costanti_Tabella.StringaBreve),
                CreaColonna("MacroArea", "Macroarea", Costanti_Tabella.StringaBreve),
                CreaColonna("descrizione", "Descrizione", Costanti_Tabella.StringaLunga),
                CreaColonna("nome", "Nome", Costanti_Tabella.StringaBreve)
        };
    }

    private QPar[] getParams()
    {
        QPar[] para;

        //tipo, dataDA, dataA, soldiDA, soldiA, descrizione, MacroArea, bData, bSoldi
        if (ricerca == null)
        {
            para = new QPar[]{
                    new QPar("tipo", cassa),
                    new QPar("bSoldi", 0),
                    new QPar("bData", 0),
                    new QPar("dataDA", new Date()),
                    new QPar("dataA", new Date()),
                    new QPar("soldiDA", 0),
                    new QPar("soldiA", 0),
                    new QPar("descrizione", "%"),
                    new QPar("MacroArea", "%")
            };
        }
        else
        {
            para = new QPar[]{
                    new QPar("tipo", ricerca.cassa),
                    new QPar("bSoldi", ricerca.bSoldi ? 1 : 0),
                    new QPar("bData", ricerca.bData ? 1 : 0),
                    new QPar("dataDA", ricerca.dataDa),
                    new QPar("dataA", ricerca.dataA),
                    new QPar("soldiDA", ricerca.soldiDa),
                    new QPar("soldiA", ricerca.soldiA),
                    new QPar("descrizione", "%" + ricerca.descrizione + "%"),
                    new QPar("MacroArea", "%" + ricerca.MacroArea + "%")
            };
        }

        return para;
    }

    @Override
    protected ArrayList<Object[]> DammiDati()
    {
        cMovimenti m = new cMovimenti(DB);

        return m.ToArrayListOfObjects(GetFieldsName(), m.Ricerca(getParams()));
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_movimenti;
    }


}
