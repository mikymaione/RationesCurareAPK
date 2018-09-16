/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.Fragments;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Wrappers.cMovimentiTempo;
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
                CreaColonna("ID", "ID", R.dimen.ID),
                CreaColonna("soldi", "Soldi", R.dimen.Soldi),
                CreaColonna("tipo", "Cassa", R.dimen.StringaBreve),

                CreaColonna("NumeroGiorni", "N. giorni", R.dimen.NumeroBreve),
                CreaColonna("PartendoDalGiorno", "Partendo da", R.dimen.Data),

                CreaColonna("GiornoDelMese_SoloGiorno", "Giorno del mese", R.dimen.NumeroBreve),
                CreaColonna("Periodo", "Periodo", R.dimen.ID),

                CreaColonna("descrizione", "Descrizione", R.dimen.StringaBreve),
                CreaColonna("MacroArea", "Macroarea", R.dimen.StringaBreve),

                CreaColonna("nome", "Nome", R.dimen.StringaBreve)
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