package it.mikymaione.RationesCurare.DB.Tables;

import java.util.Date;

import it.mikymaione.RationesCurare.DB.Templates.baseTabellaDB;
import it.mikymaione.RationesCurare.UI.CalendarControl.DateEvent;

/**
 * Created by michele on 07/03/15.
 */

public class Calendario extends baseTabellaDB<Integer> implements DateEvent
{
    public Integer ID = -1;
    public Date Giorno, Inserimento;
    public String Descrizione, IDGruppo;

    public Calendario()
    {
        //vuoto
    }

    public Calendario(String descrizione, Date giorno)
    {
        Giorno = giorno;
        Descrizione = descrizione;
    }

    @Override
    public Integer getDBID()
    {
        return ID;
    }

    @Override
    public long getDate()
    {
        return Giorno.getTime();
    }

    @Override
    public String toString()
    {
        return Descrizione;
    }


}