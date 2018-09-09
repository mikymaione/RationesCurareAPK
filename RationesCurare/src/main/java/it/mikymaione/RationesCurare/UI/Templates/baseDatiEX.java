package it.mikymaione.RationesCurare.UI.Templates;

import android.view.MenuItem;

import it.mikymaione.RationesCurare.R;

/**
 * Created by michele on 24/03/15.
 */
public abstract class baseDatiEX<V> extends baseDati<V>
{

    @Override
    protected void AfterDelete()
    {
        TornaAlPadre();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_Elimina)
            DomandaElimina(MyID);
        else
            return super.onOptionsItemSelected(item);

        return true;
    }


}