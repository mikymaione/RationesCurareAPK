package it.mikymaione.RationesCurare.DB.Tables;

import it.mikymaione.RationesCurare.DB.Templates.baseTabellaDB;

/**
 * Created by michele on 06/03/15.
 */

public class Cassa extends baseTabellaDB<String>
{
    public String nome = "";
    public byte[] imgName;

    @Override
    public String getDBID()
    {
        return nome;
    }


}