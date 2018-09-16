/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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
    public final String ARG_MyID = "MyID";
    public V MyID;

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