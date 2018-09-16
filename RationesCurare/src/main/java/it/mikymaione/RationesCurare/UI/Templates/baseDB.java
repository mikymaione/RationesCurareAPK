/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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
    public final String ARG_Padre_Fragment = "Padre_Fragment";
    public final String ARG_Padre_Posizione = "Padre_Posizione";
    protected cDB DB = null;
    private baseFragment Padre_Fragment = null;
    private ePosizione Padre_Posizione = null;

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