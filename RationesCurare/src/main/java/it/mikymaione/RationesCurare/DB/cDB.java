package it.mikymaione.RationesCurare.DB;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;

import it.mikymaione.RationesCurare.DB.Templates.QPar;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.cErrore;

/**
 * Created by michele on 06/03/15.
 */
public class cDB
{
    private HashMap<String, String> QueryLette = new HashMap<String, String>();
    private AssetManager assetManager = null;
    private SQLiteDatabase data_base = null;
    private String DBPath = "";

    private static cDB DB = null;

    private enum eEseguiSQLNonQuery
    {
        delete, update, insert;
    }

    public static cDB DB()
    {
        return DB;
    }

    public cDB(AssetManager assetManager_, File getExternalFilesDir)
    {
        assetManager = assetManager_;
        boolean errore = false;
        File outFile = new File(getExternalFilesDir, GB.NomeDB);

        Close();

        if (outFile.exists())
        {
            DBPath = outFile.getAbsolutePath();
        }
        else
        {
            if (DBPath == null || DBPath.equals(""))
            {
                InputStream in = null;
                OutputStream out = null;

                try
                {
                    if (!outFile.exists())
                    {
                        File parent = outFile.getParentFile();

                        if (!parent.exists() && !parent.mkdirs())
                        {
                            errore = true;
                        }

                        if (!errore)
                        {
                            out = new FileOutputStream(outFile);
                            in = assetManager.open("databases/standard.rqd8");

                            GB.copyFile(in, out);

                            if (outFile.exists())
                            {
                                DBPath = outFile.getAbsolutePath();
                            }
                        }
                    }
                }
                catch (IOException e)
                {
                    //errore
                    errore = true;
                }
                finally
                {
                    if (in != null)
                    {
                        try
                        {
                            in.close();
                        }
                        catch (IOException e)
                        {
                            // NOOP
                        }
                    }
                    if (out != null)
                    {
                        try
                        {
                            out.close();
                        }
                        catch (IOException e)
                        {
                            // NOOP
                        }
                    }
                }
            }
        }

        if (!errore)
        {
            Ricarica();
        }

        DB = this;
    }

    public String getDBPath()
    {
        return DBPath;
    }

    public void Ricarica()
    {
        boolean isOpen = false;

        if (data_base != null)
        {
            isOpen = data_base.isOpen();
        }

        if (!isOpen)
        {
            data_base = SQLiteDatabase.openDatabase(DBPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }


    private String LeggiSQL(eTabelle t, eTipi nome)
    {
        String r = "";
        String z = "SQL/" + t.toString() + "/" + nome.toString() + ".sql";

        if (QueryLette.containsKey(z))
        {
            r = QueryLette.get(z);
        }
        else
        {
            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open(z)));

                try
                {
                    String line = "";

                    while (line != null)
                    {
                        line = br.readLine();

                        if (line != null)
                        {
                            r += line + " ";
                        }
                    }

                    r = r.replace("\t", " ");
                    r = r.replace("= ", "=");
                    r = r.replace(" =", "=");
                    r = r.replace("  ", " ");
                    r = r.replace("  ", " ");
                    r = r.replace("  ", " ");
                }
                finally
                {
                    br.close();
                }
            }
            catch (Exception eee)
            {
                //
            }

            QueryLette.put(z, r);
        }

        return r;
    }

    public Cursor rawQuery(eTabelle tabella, eTipi nomeSQL, QPar[] parametri)
    {
        return rawQuery(tabella, nomeSQL, parametri, 50);
    }

    public Cursor rawQuery(eTabelle tabella, eTipi nomeSQL, QPar[] parametri, int Top)
    {
        Cursor cccc = null;
        String[] s = null;
        String e = "";
        String theLimit = " LIMIT " + Top + ";";
        String q = LeggiSQL(tabella, nomeSQL);

        if (Top == -1)
        {
            theLimit = "";
        }

        try
        {
            if (parametri != null)
            {
                for (QPar z : parametri)
                {
                    String p = "";

                    if (z.Valore instanceof String)
                    {
                        p = "'" + z.Valore.toString() + "'";
                    }
                    else if (z.Valore instanceof Date)
                    {
                        p = "'" + GB.DateToSQLite((Date) z.Valore) + "'";
                    }
                    else
                    {
                        p = z.Valore.toString();
                    }

                    q = q.replace(":" + z.Nome, p);
                }
            }

            Ricarica();
            cccc = data_base.rawQuery(q + theLimit, null);
        }
        catch (Exception ez)
        {
            e = ez.getMessage();
        }

        return cccc;
    }

    private cErrore<Long> EseguiSQLNonQuery(eEseguiSQLNonQuery operazione, eTabelle tabella, ContentValues values, String whereClause, String[] whereArgs)
    {
        long r = 0;
        String e = "";

        try
        {
            Ricarica();

            data_base.beginTransaction();

            if (operazione == eEseguiSQLNonQuery.update)
            {
                r = data_base.update(tabella.toString(), values, whereClause, whereArgs);
            }
            else if (operazione == eEseguiSQLNonQuery.delete)
            {
                r = data_base.delete(tabella.toString(), whereClause, whereArgs);
            }
            else if (operazione == eEseguiSQLNonQuery.insert)
            {
                r = data_base.insert(tabella.toString(), null, values);
            }

            if (r > 0)
            {
                if (tabella != eTabelle.utenti)
                {
                    ContentValues vut = new ContentValues();
                    vut.put("UltimaModifica", GB.DateToSQLite(new Date()));
                    data_base.update("utenti", vut, null, null);
                }

                data_base.setTransactionSuccessful();
            }
        }
        catch (Exception er)
        {
            e = er.getMessage();
        }
        finally
        {
            data_base.endTransaction();
        }

        return new cErrore<Long>(e, r);
    }

    public cErrore<Long> delete(eTabelle tabella, String whereClause, String[] whereArgs)
    {
        return EseguiSQLNonQuery(eEseguiSQLNonQuery.delete, tabella, null, whereClause, whereArgs);
    }

    public cErrore<Long> insert(eTabelle tabella, ContentValues values)
    {
        return EseguiSQLNonQuery(eEseguiSQLNonQuery.insert, tabella, values, null, null);
    }

    public cErrore<Long> update(eTabelle tabella, ContentValues values, String whereClause, String[] whereArgs)
    {
        return EseguiSQLNonQuery(eEseguiSQLNonQuery.update, tabella, values, whereClause, whereArgs);
    }

    public void Close()
    {
        if (data_base != null)
        {
            if (data_base.isOpen())
            {
                data_base.close();
            }
        }
    }


}