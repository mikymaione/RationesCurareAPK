/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.Fragments;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
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

        return GB.DoubleToEuro(m.Saldo());
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
                CreaColonna("tipo", "Cassa", R.dimen.StringaBreve),
                CreaColonna("soldi", "Saldo", R.dimen.Soldi)
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