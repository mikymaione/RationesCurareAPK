package it.mikymaione.RationesCurare.DB.Tables;

import java.util.Date;

import it.mikymaione.RationesCurare.DB.Templates.baseTabellaDB;

/**
 * Created by michele on 21/03/15.
 */
public class utenti extends baseTabellaDB<Integer>
{
    public Integer ID = -1;
    public String nome, psw, path, Email;
    public Date UltimaModifica;

    @Override
    public Integer getDBID()
    {
        return ID;
    }

    @Override
    public String toString()
    {
        String z = "";

        if (nome != null)
        {
            z = nome;
        }

        return z;
    }


}