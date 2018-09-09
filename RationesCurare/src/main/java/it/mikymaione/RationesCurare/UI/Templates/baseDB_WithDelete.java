package it.mikymaione.RationesCurare.UI.Templates;

import android.content.DialogInterface;
import android.view.MenuItem;

import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.R;

/**
 * Created by michele on 10/03/15.
 */
public abstract class baseDB_WithDelete<T> extends baseDB
{
    protected T SelectedItem_ = null;

    protected void DomandaElimina()
    {
        DomandaElimina(SelectedItem_);
    }

    protected void DomandaElimina(T SelectedItem__)
    {
        if (SelectedItem__ == null || SelectedItem__.equals("") || SelectedItem__.equals(-1))
        {
            GB.MsgBox(getActivity(), "Informazione", "Nessun elemento selezionato!");
        }
        else
        {
            GB.QuestionBox(getActivity(), "Informazione", "Eliminare l'elemento selezionato: " + GetSelectedItemToString(SelectedItem__) + " ?", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    if (whichButton == DialogInterface.BUTTON_POSITIVE)
                        Elimina_p();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Annulla)
            TornaAlPadre();
        else if (z == R.id.action_Elimina)
            DomandaElimina();
        else
            return super.onOptionsItemSelected(item);

        return true;
    }

    private String GetSelectedItemToString(T SelectedItem__)
    {
        String j = "";

        try
        {
            j = SelectedItem__.toString();
        }
        catch (Exception e)
        {
            //errore
        }

        return j;
    }

    private void Elimina_p()
    {
        final cErrore<Long> r = Elimina();
        String testo = (r.risultato > 0 ? "Eliminato!" : "Errore durante l'eliminazione: " + r.testo);

        GB.MsgBox(getActivity(), "Informazione", testo, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if (r.risultato > 0)
                    AfterDelete();
            }
        });
    }

    protected abstract void AfterDelete();

    protected abstract cErrore<Long> Elimina();

}