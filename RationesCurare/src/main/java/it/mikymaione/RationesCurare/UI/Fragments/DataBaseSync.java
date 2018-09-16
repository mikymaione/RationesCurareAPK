package it.mikymaione.RationesCurare.UI.Fragments;

import android.view.MenuItem;
import android.view.View;

import it.mikymaione.RationesCurare.Globals.cDataBaseSync;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseDB;

public class DataBaseSync extends baseDB
{
    private cDataBaseSync DBSync = new cDataBaseSync(getActivity(), DB, true);

    @Override
    protected View GetFirstControlToFocus()
    {
        return null;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_data_base_sync;
    }

    @Override
    public CharSequence GetTitolo()
    {
        return "Sincronizzazione database";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Sincronizza)
        {
            DBSync.AvviaSincronizzaDB();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }


}