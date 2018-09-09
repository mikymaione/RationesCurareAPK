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