/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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