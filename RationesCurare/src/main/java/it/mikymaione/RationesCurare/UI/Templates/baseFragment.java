/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.Templates;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.io.Serializable;

/**
 * Created by michele on 09/03/15.
 */
public abstract class baseFragment extends Fragment implements Serializable
{

    protected View MyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = inflater.inflate(get_R_layout_fragment_name(), container, false);

        return MyView;
    }

    protected void SelezionaInSpinner(Spinner spinner, String seleziona)
    {
        int index = -1;

        for (int i = 0; i < spinner.getCount(); i++)
        {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(seleziona))
            {
                index = i;
                break;
            }
        }

        if (index > -1)
        {
            spinner.setSelection(index);
        }
    }

    public Object BundleGetArg(String key)
    {
        Bundle b = getArguments();

        if (b != null && b.containsKey(key))
            return b.get(key);

        return null;
    }

    protected abstract int get_R_layout_fragment_name();

    public abstract java.lang.CharSequence GetTitolo();

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
    }


}