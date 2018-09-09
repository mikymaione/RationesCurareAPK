package it.mikymaione.RationesCurare.WS;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.kobjects.base64.Base64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import it.mikymaione.RationesCurare.Globals.GB;

/**
 * Created by michele on 30/03/15.
 */
public class UploadFileRC_simple extends WS<String>
{
    private String psw, email, yyyyMMddHHmmss;
    private File db;

    public UploadFileRC_simple(Date VersioneAttuale, String psw, String email, File db)
    {
        this.yyyyMMddHHmmss = GB.DateToString(VersioneAttuale, "yyyyMMddHHmmss");
        this.psw = psw;
        this.email = email;
        this.db = db;

        if (yyyyMMddHHmmss == null || yyyyMMddHHmmss.equals("") || yyyyMMddHHmmss.length() != 14)
        {
            this.yyyyMMddHHmmss = GB.DateToString(new Date(90, 1, 1, 0, 0, 0), "yyyyMMddHHmmss");
        }
    }

    @Override
    protected String getOPERATION_NAME()
    {
        return "UploadFileRC_simple";
    }

    @Override
    protected PropertyInfo[] getParameters()
    {
        try
        {
            String fz = db.getAbsolutePath();
            String zi = FilenameUtils.getPath(db.getPath()) + email + ".zip";

            zip(email, fz, zi);

            File fzi = new File(zi);
            if (fzi.exists())
            {
                String fzip = fzi.getName();
                byte[] f = FileUtils.readFileToByteArray(fzi);

                return new PropertyInfo[]{
                        newPropertyInfo("yyyyMMddHHmmss", yyyyMMddHHmmss, String.class),
                        newPropertyInfo("Utente", email, String.class),
                        newPropertyInfo("Psw", psw, String.class),
                        newPropertyInfo("f", Base64.encode(f), String.class),
                        newPropertyInfo("fileName", fzip, String.class)
                };
            }
        }
        catch (IOException e)
        {
            //errore
        }

        return null;
    }

    @Override
    protected String ConvertiRisultato(Object r)
    {
        if (r != null && r instanceof SoapObject)
        {
            return ((SoapObject) r).getProperty(0).toString();
        }

        return "";
    }

    private void zip(String email, String file, String zipFile) throws IOException
    {
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));

        try
        {
            int BUFFER_SIZE = 1024;
            byte data[] = new byte[BUFFER_SIZE];
            FileInputStream fi = new FileInputStream(file);
            BufferedInputStream origin = new BufferedInputStream(fi, BUFFER_SIZE);

            try
            {
                int count;
                out.putNextEntry(new ZipEntry(email + ".rqd8"));

                while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1)
                {
                    out.write(data, 0, count);
                }
            }
            finally
            {
                origin.close();
            }
        }
        finally
        {
            out.close();
        }
    }


}