package it.mikymaione.RationesCurare.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import it.mikymaione.RationesCurare.DB.Wrappers.cCalendario;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.CalendarControl.DateEvent;
import it.mikymaione.RationesCurare.UI.CalendarControl.HighlightCalendarView;
import it.mikymaione.RationesCurare.UI.Templates.baseDB;

public class Calendario extends baseDB
{

    private HighlightCalendarView calendario;
    private it.mikymaione.RationesCurare.DB.Tables.Calendario Selezionato;
    private Calendar DataCorrente = Calendar.getInstance();

    @Override
    public CharSequence GetTitolo()
    {
        return "Calendario";
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_calendario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        calendario = (HighlightCalendarView) MyView.findViewById(R.id.calendario);

        calendario.setOnDateSelectedListener(new HighlightCalendarView.OnDateSelectedListener()
        {

            @Override
            public void onDaySelected(HighlightCalendarView view, int year, int month, int dayOfMonth)
            {
                DataCorrente.clear();
                DataCorrente.set(year, month, dayOfMonth);
            }

            @Override
            public void onViewChanged(long startDate, long endDate)
            {
                DataCorrente = calendario.getCurrentDate();
            }

            @Override
            public void onEventSelected(DateEvent event)
            {
                if (event instanceof it.mikymaione.RationesCurare.DB.Tables.Calendario)
                {
                    Selezionato = (it.mikymaione.RationesCurare.DB.Tables.Calendario) event;
                    Dettaglio(false);
                }
            }

            @Override
            public void onAddEvent(long date)
            {
                //
            }

        });

        Carica();

        return MyView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_AnnoPrecedente)
        {
            Vai(R.id.action_AnnoPrecedente);
        }
        else if (z == R.id.action_AnnoSuccessivo)
        {
            Vai(R.id.action_AnnoSuccessivo);
        }
        else if (z == R.id.action_MesePrecedente)
        {
            Vai(R.id.action_MesePrecedente);
        }
        else if (z == R.id.action_MeseSuccessivo)
        {
            Vai(R.id.action_MeseSuccessivo);
        }
        else if (z == R.id.action_Oggi)
        {
            Vai(R.id.action_Oggi);
        }
        else if (z == R.id.action_nuovo)
        {
            Dettaglio(true);
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return null;
    }

    private void Carica()
    {
        cCalendario c = new cCalendario(DB);

        calendario.addEvents(c.Ricerca(null));
    }

    private void Dettaglio(boolean nuovo)
    {
        Integer ID_ = -1;

        if (!nuovo)
        {
            if (Selezionato != null)
            {
                ID_ = Selezionato.ID;
            }
        }

        if (ID_ != null)
        {
            GB.NavigateTo(this, PB.newCalendario_Dettaglio(this, ePosizione.Calendario, ID_, DataCorrente), ePosizione.Calendario_Dettaglio);
        }
    }

    private void Vai(int d)
    {
        if (d == R.id.action_Oggi)
        {
            Date n = new Date();

            DataCorrente.clear();
            DataCorrente.set(n.getYear() + 1900, n.getMonth(), n.getDate());
        }
        else
        {
            if (d == R.id.action_AnnoPrecedente)
            {
                DataCorrente.add(Calendar.YEAR, -1);
            }
            else if (d == R.id.action_AnnoSuccessivo)
            {
                DataCorrente.add(Calendar.YEAR, 1);
            }
            else if (d == R.id.action_MesePrecedente)
            {
                DataCorrente.add(Calendar.MONTH, -1);
            }
            else if (d == R.id.action_MeseSuccessivo)
            {
                DataCorrente.add(Calendar.MONTH, 1);
            }
        }

        calendario.setDate(DataCorrente.getTimeInMillis(), true, true);

        Carica();
    }


}