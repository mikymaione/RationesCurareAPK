package it.mikymaione.RationesCurare.DB.Tables;

import java.util.Date;

import it.mikymaione.RationesCurare.DB.Templates.baseTabellaDB;

public class movimenti extends baseTabellaDB<Integer>
{
    public Integer ID = -1;
    public String nome, tipo, descrizione, MacroArea;
    public Date data;
    public Double soldi;

    @Override
    public Integer getDBID()
    {
        return ID;
    }


}