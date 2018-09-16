/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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