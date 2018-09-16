/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.Templates;

import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.DataGrid.DataGrid;
import it.mikymaione.RationesCurare.UI.DataGrid.DataTable;
import it.mikymaione.RationesCurare.UI.DataGrid.Item;
import it.mikymaione.RationesCurare.UI.Fragments.ePosizione;

/**
 * Created by michele on 09/03/15.
 */
public abstract class baseRicerca<V> extends baseDB_WithDelete<V>
{
    private DataGrid grid = null;

    protected String[] GetFieldsName()
    {
        return StileColonneSoloFieldName(DammiColonne());
    }

    private String[] StileColonneSoloFieldName(DataGrid.ColumnStyle[] colonne)
    {
        String[] r = new String[colonne.length];

        for (int i = 0; i < colonne.length; i++)
        {
            r[i] = colonne[i].getFieldName();
        }

        return r;
    }

    private void CaricaDatiInGriglia(boolean Ricarica)
    {
        DataGrid.ColumnStyle[] colonne = DammiColonne();
        DataTable dtDataSource = new DataTable();

        dtDataSource.addAllColumns(StileColonneSoloFieldName(colonne));
        dtDataSource.addAllRowsOnlyValue(DammiDati());

        grid = (DataGrid) MyView.findViewById(DammiGrigliaID());

        if (!Ricarica)
        {
            grid.addColumnStyles(colonne);
        }

        grid.setDataSource(dtDataSource);

        if (Ricarica)
        {
            grid.initListView();
        }

        grid.refresh();

        grid.setLvOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                DataTable.DataRow dd = grid.getDataSource().getRow(position);

                if (dd != null)
                {
                    SelectedItem_ = (V) dd.get(0);
                }

                if (view instanceof Item)
                {
                    Item i = (Item) view;

                    for (Item z : grid.idSelectedItem)
                    {
                        z.HighlightRow(false);
                    }

                    i.HighlightRow(true);
                    i.setSelected(true);
                    grid.idSelectedItem.add(i);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        CaricaDatiInGriglia(false);

        return MyView;
    }

    protected V GetSelectedItem()
    {
        return SelectedItem_;
    }

    protected DataGrid.ColumnStyle CreaColonna(String name, String caption, @DimenRes int width_id)
    {
        int width_ = getResources().getDimensionPixelSize(width_id);
        return new DataGrid.ColumnStyle(name, caption, width_);
    }

    protected DataGrid.ColumnStyle CreaColonna(String FieldName_and_DisplayName, @DimenRes int width_id)
    {
        int width_ = getResources().getDimensionPixelSize(width_id);
        return new DataGrid.ColumnStyle(FieldName_and_DisplayName, width_);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_dettaglio)
        {
            PreDettaglio();
        }
        else if (z == R.id.action_nuovo)
        {
            Dettaglio(true);
        }
        else if (z == R.id.action_modifica)
        {
            PreDettaglio();
        }
        else if (z == R.id.action_Cerca)
        {
            Cerca();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void Cerca()
    {
        GB.NavigateTo(this, PB.newRicerca(), ePosizione.Cerca);
    }

    @Override
    protected void AfterDelete()
    {
        grid.DeleteRow(SelectedItem_);
        SelectedItem_ = null;
        CaricaDatiInGriglia(true);
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return null; //nessun controllo nella ricerca
    }

    private void PreDettaglio()
    {
        V gs = GetSelectedItem();

        if (gs == null || gs.equals(-1) || gs.equals(""))
        {
            GB.MsgBox(getActivity(), "Informazione", "Nessun elemento selezionato in griglia!");
        }
        else
        {
            Dettaglio(false);
        }
    }

    protected abstract void Dettaglio(boolean Nuovo);

    protected abstract int DammiGrigliaID();

    protected abstract DataGrid.ColumnStyle[] DammiColonne();

    protected abstract ArrayList<Object[]> DammiDati();

}