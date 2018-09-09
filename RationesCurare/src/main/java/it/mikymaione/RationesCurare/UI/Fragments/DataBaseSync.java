package it.mikymaione.RationesCurare.UI.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import it.mikymaione.RationesCurare.DB.Tables.utenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseDB;
import it.mikymaione.RationesCurare.WS.ComparaDBRC;
import it.mikymaione.RationesCurare.WS.ControllaCredenzialiRC_simple;
import it.mikymaione.RationesCurare.WS.CreaDBPerRC_simple;
import it.mikymaione.RationesCurare.WS.DeZippaDBRC;
import it.mikymaione.RationesCurare.WS.OttieniUltimoDBRC;
import it.mikymaione.RationesCurare.WS.UploadFileRC_simple;

public class DataBaseSync extends baseDB
{
    private ProgressDialog ProgressDialog_ = null;
    private boolean inEsecuzione = false;

    @Override
    protected View GetFirstControlToFocus()
    {
        return null;
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_data_base_sync;
    }

    @Override
    public CharSequence GetTitolo()
    {
        return "Sincronizzazione database";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Sincronizza)
        {
            AvviaSincronizzaDB();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void AvviaSincronizzaDB()
    {
        if (!inEsecuzione)
        {
            inEsecuzione = true;
            ProgressDialog_ = ProgressDialog.show(getActivity(), "Attendere...", "Sincronizzazione in corso.");

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
                File tempod = getActivity().getCacheDir();
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
                    {
                        return SincronizzaDB();
                    }
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
            File dbfile = new File(getActivity().getExternalFilesDir(null), GB.NomeDB);
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

    private String ComparaDB()
    {
        try
        {
            cUtenti c = new cUtenti(DB);
            utenti u = c.Carica();

            ComparaDBRC comparaDBRC = new ComparaDBRC(u.UltimaModifica, u.Email, u.psw);
            String comp = comparaDBRC.Call();

            if (comp != null && comp.length() > 0)
            {
                return comp;
            }
        }
        catch (Exception e)
        {
            //errore
        }

        return "";
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
            if (o != null)
            {
                GB.MsgBox(getActivity(), "Informazione", o.toString());
            }

            ProgressDialog_.dismiss();
            inEsecuzione = false;
        }
    }


    private class AsyncCompare extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects)
        {
            return ComparaDB();
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

                GB.MsgBox(getActivity(), "Informazione", z);
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
                GB.MsgBox(getActivity(), "Informazione", "Database aggiornato dal cloud!");

                ProgressDialog_.dismiss();
                inEsecuzione = false;
            }
            else
            {
                new AsyncUpload().execute();
            }
        }
    }


}