/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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