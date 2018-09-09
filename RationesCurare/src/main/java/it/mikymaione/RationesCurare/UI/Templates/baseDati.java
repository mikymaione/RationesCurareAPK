package it.mikymaione.RationesCurare.UI.Templates;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;

/**
 * Created by michele on 18/03/15.
 */
public abstract class baseDati<V> extends baseDB_WithDelete
{
    public V MyID;

    public final String ARG_MyID = "MyID";


    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);

        MyID = (V) BundleGetArg(ARG_MyID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Salva)
            Salva_p();
        else if (z == R.id.action_Pulisci)
            Carica();
        else
            return super.onOptionsItemSelected(item);

        return true;
    }

    private void Salva_p()
    {
        final cErrore<Long> r = Salva();
        String testo = (r.risultato > 0 ? "Salvato!" : "Errore durante il salvataggio: " + r.testo);

        GB.MsgBox(getActivity(), "Informazione", testo, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (r.risultato > 0)
                    TornaAlPadre();
            }
        });
    }

    protected void Carica()
    {
        if (MyID == null || MyID.equals(-1) || MyID.equals(""))
        {
            Svuota();
        }
        else
        {
            SettaDatiNeiControlli();
        }
    }

    @Override
    protected cErrore<Long> Elimina()
    {
        //utilizzato in baseDatiEX
        return null;
    }

    @Override
    protected void AfterDelete()
    {
        //utilizzato in baseDatiEX
    }

    protected abstract cErrore<Long> Salva();

    protected abstract void SettaDatiNeiControlli();

    protected abstract void Svuota();

}