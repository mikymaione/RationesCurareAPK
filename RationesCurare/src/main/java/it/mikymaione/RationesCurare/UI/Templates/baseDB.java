package it.mikymaione.RationesCurare.UI.Templates;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Fragments.ePosizione;

/**
 * Created by michele on 24/03/15.
 */
public abstract class baseDB extends baseFragment
{
    protected cDB DB = null;
    private baseFragment Padre_Fragment = null;
    private ePosizione Padre_Posizione = null;

    public final String ARG_Padre_Fragment = "Padre_Fragment";
    public final String ARG_Padre_Posizione = "Padre_Posizione";

    protected baseDB()
    {
        if (DB == null)
            DB = cDB.DB();

        if (DB == null)
            DB = new cDB(getActivity().getAssets(), getActivity().getExternalFilesDir(null));

        setHasOptionsMenu(true);
    }

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);

        Padre_Fragment = (baseFragment) BundleGetArg(ARG_Padre_Fragment);
        Padre_Posizione = (ePosizione) BundleGetArg(ARG_Padre_Posizione);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_Annulla)
            TornaAlPadre();
        else
            return super.onOptionsItemSelected(item);

        return true;
    }

    protected void TornaAlPadre()
    {
        if (Padre_Fragment != null)
            GB.NavigateTo(this, Padre_Fragment, Padre_Posizione);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        View u = GetFirstControlToFocus();

        if (u != null && u.isFocusable())
            u.requestFocus();
    }

    protected abstract View GetFirstControlToFocus();

}