/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.Globals;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import it.mikymaione.RationesCurare.DB.Tables.utenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.WS.ComparaDBRC;
import it.mikymaione.RationesCurare.WS.ControllaCredenzialiRC_simple;
import it.mikymaione.RationesCurare.WS.CreaDBPerRC_simple;
import it.mikymaione.RationesCurare.WS.DeZippaDBRC;
import it.mikymaione.RationesCurare.WS.OttieniUltimoDBRC;
import it.mikymaione.RationesCurare.WS.UploadFileRC_simple;

public class cDataBaseSync
{
    private boolean MostraNotifiche;
    private cDB DB;
    private Activity activity;
    private ProgressDialog ProgressDialog_ = null;
    private boolean inEsecuzione = false;

    private ArrayList<iDataBaseSync> listeners = new ArrayList<>();


    public cDataBaseSync(Activity activity_, cDB DB_, boolean MostraNotifiche_)
    {
        MostraNotifiche = MostraNotifiche_;
        DB = DB_;
        activity = activity_;
    }

    //events
    public void addListener(iDataBaseSync toAdd)
    {
        listeners.add(toAdd);
    }

    public void DataBaseSyncDone()
    {
        for (iDataBaseSync hl : listeners)
            hl.DataBaseSyn_Done();
    }
    //events


    public void AvviaSincronizzaDB()
    {
        if (!inEsecuzione)
        {
            inEsecuzione = true;
            ProgressDialog_ = ProgressDialog.show(activity, "Attendere...", "Sincronizzazione in corso.");

            new AsyncCompare().execute();
        }
    }

    private boolean ScaricaUltimoDB()
    {
        boolean r = false;
        cUtenti c = new cUtenti(DB);
        utenti u = c.Carica();

        if (u != null && u.ID > -1)
        {
            try
            {
                File tempod = activity.getCacheDir();
                File tempof = File.createTempFile("RCTempDB", ".rcq8", tempod);
                OttieniUltimoDBRC d = new OttieniUltimoDBRC(tempof, u.UltimaModifica, u.Email, u.psw);

                if (d.SalvaDBSuDisco())
                {
                    String dest = DB.getDBPath();
                    File destf = new File(dest);

                    DB.Close();

                    unzip(tempof, destf);

                    DB.Ricarica();
                    r = true;
                }
            }
            catch (Exception e)
            {
                //cannot create file
            }
        }

        return r;
    }

    private void unzip(File zip_f, File dest) throws IOException
    {
        ZipFile zipFile = new ZipFile(zip_f);
        Enumeration<? extends ZipEntry> enu = zipFile.entries();

        while (enu.hasMoreElements())
        {
            ZipEntry e = enu.nextElement();

            InputStream inp = zipFile.getInputStream(e);
            FileOutputStream st = new FileOutputStream(dest);

            try
            {
                GB.copyFile(inp, st);
            }
            catch (Exception er)
            {
                //errore copia
            }

            inp.close();
            st.close();
        }

        zipFile.close();
    }

    private String SincronizzaDB()
    {
        cUtenti u = new cUtenti(DB);
        utenti i = u.Carica();

        if (i != null)
        {
            try
            {
                ControllaCredenzialiRC_simple cc = new ControllaCredenzialiRC_simple(i.Email, i.psw);
                String r_ControllaCredenzialiRC = cc.Call();

                if (r_ControllaCredenzialiRC.equalsIgnoreCase("Assente"))
                {
                    CreaDBPerRC_simple crea = new CreaDBPerRC_simple(i.Email, i.psw);

                    if (crea.Call())
                        return SincronizzaDB();
                }
                else if (r_ControllaCredenzialiRC.equalsIgnoreCase("TuttoOK"))
                {
                    return MandaIlFile(i);
                }
                else if (r_ControllaCredenzialiRC.equalsIgnoreCase("Presente_PasswordErrata"))
                {
                    return "Password errata!";
                }
                else if (r_ControllaCredenzialiRC.equalsIgnoreCase("DBSulServerEPiuRecente"))
                {
                    return "Il database non verrà sincronizzato perché quello sul server è più recente!";
                }
            }
            catch (Exception e)
            {
                return e.getMessage();
            }
        }

        return "Configurare l'utente!";
    }

    private String MandaIlFile(utenti i)
    {
        try
        {
            File dbfile = new File(activity.getExternalFilesDir(null), GB.NomeDB);
            UploadFileRC_simple w = new UploadFileRC_simple(i.UltimaModifica, i.psw, i.Email, dbfile);
            String r_UploadFileRC = w.Call();

            if (r_UploadFileRC != null && r_UploadFileRC.equalsIgnoreCase("FileInviato"))
            {
                DeZippaDBRC deZippaDBRC = new DeZippaDBRC(i.Email + ".zip");
                Boolean l = deZippaDBRC.Call();

                if (l)
                {
                    return "Sincronizzazione effettuata!";
                }
                else
                {
                    return "Archivio zip non presente sul cloud!";
                }
            }
            else
            {
                return "Errore durante la sincronizzazione: file non inviato!";
            }
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    private String ComparaDB() throws IOException, XmlPullParserException
    {
        cUtenti c = new cUtenti(DB);
        utenti u = c.Carica();

        ComparaDBRC comparaDBRC = new ComparaDBRC(u.UltimaModifica, u.Email, u.psw);

        return comparaDBRC.Call();
    }


    private class AsyncUpload extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects)
        {
            return SincronizzaDB();
        }

        @Override
        protected void onPostExecute(Object o)
        {
            ProgressDialog_.dismiss();
            inEsecuzione = false;

            if (o != null)
                if (MostraNotifiche)
                    GB.MsgBox(activity, "Informazione", o.toString());

            DataBaseSyncDone();
        }
    }


    private class AsyncCompare extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects)
        {
            try
            {
                return ComparaDB();
            }
            catch (Exception e)
            {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(Object o)
        {
            boolean nulla = true;
            String z = "Nulla da aggiornare!";

            if (o != null && o instanceof String)
            {
                z = (String) o;

                if (z.equalsIgnoreCase("Server"))
                {
                    nulla = false;
                    new AsyncDownload().execute();
                }
                else if (z.equalsIgnoreCase("Client"))
                {
                    nulla = false;
                    new AsyncUpload().execute();
                }
                else if (z.equalsIgnoreCase("Uguale"))
                {
                    z = "Il database non verrà sincronizzato perché sono uguali!";
                }
                else if (z.equalsIgnoreCase("AccessoNonAutizzato"))
                {
                    z = "Credenziali non valide!";
                }
            }

            if (nulla)
            {
                ProgressDialog_.dismiss();
                inEsecuzione = false;

                if (MostraNotifiche)
                    GB.MsgBox(activity, "Informazione", z);

                DataBaseSyncDone();
            }
        }
    }


    private class AsyncDownload extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects)
        {
            return ScaricaUltimoDB();
        }

        @Override
        protected void onPostExecute(Object o)
        {
            Boolean r = false;

            if (o != null)
            {
                r = (Boolean) o;
            }

            if (r)
            {
                ProgressDialog_.dismiss();
                inEsecuzione = false;

                if (MostraNotifiche)
                    GB.MsgBox(activity, "Informazione", "Database aggiornato dal cloud!");

                DataBaseSyncDone();
            }
            else
            {
                new AsyncUpload().execute();
            }
        }
    }
}
