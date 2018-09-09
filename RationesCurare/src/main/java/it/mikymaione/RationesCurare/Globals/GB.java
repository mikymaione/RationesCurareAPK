package it.mikymaione.RationesCurare.Globals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.widget.DatePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Editors.DateEditText;
import it.mikymaione.RationesCurare.UI.Editors.TimeEditText;
import it.mikymaione.RationesCurare.UI.Fragments.ePosizione;
import it.mikymaione.RationesCurare.UI.Templates.baseFragment;

/**
 * Created by michele on 07/03/15.
 */
public class GB
{
    public static int cHoloBlueDark = Color.rgb(0, 153, 204);
    public static int cHoloBlueLight = Color.rgb(51, 181, 229);
    public static int cDarkerGray = Color.rgb(210, 210, 210);
    public static int cWhite = Color.rgb(255, 255, 255);

    public static java.lang.CharSequence CurrentTitle = "";
    public static ePosizione CurrentPosition = ePosizione.Casse;

    public static String SQLiteDateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
    public static String ItalianDateTimeFormatString = "dd/MM/yyyy HH:mm";
    public static String ItalianDateFormatString = "dd/MM/yyyy";

    public static String NomeDB = "databases/RationesCurare.rqd8";
    public static boolean Esiste1Utente = false;

    public static void copyFile(InputStream in, OutputStream out) throws IOException
    {
        int read;
        byte[] buffer = new byte[1024];

        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

    private static void CreateBox(android.content.Context context, String titolo, String testo, android.content.DialogInterface.OnClickListener listener, boolean YesNo)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(testo);
        builder.setTitle(titolo);
        builder.setCancelable(false);

        if (listener == null)
        {
            listener = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    //prosegui
                }
            };
        }

        builder.setPositiveButton("Ok", listener);

        if (YesNo)
        {
            builder.setNegativeButton("No", listener);
        }

        builder.create().show();
    }

    public static void QuestionBox(android.content.Context context, String titolo, String testo, android.content.DialogInterface.OnClickListener listener)
    {
        CreateBox(context, titolo, testo, listener, true);
    }

    public static void MsgBox(android.content.Context context, String titolo, String testo, android.content.DialogInterface.OnClickListener listener)
    {
        CreateBox(context, titolo, testo, listener, false);
    }

    public static void MsgBox(android.content.Context context, String titolo, String testo)
    {
        CreateBox(context, titolo, testo, null, false);
    }

    protected static void ShowDatePickerDialog(android.content.Context context, DateEditText eData)
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        ShowDatePickerDialog(context, eData, mYear, mMonth, mDay);
    }

    public static void ShowTimePickerDialog(android.content.Context context, TimeEditText eTime, tTime t)
    {
        ShowTimePickerDialog(context, eTime, t.H, t.M);
    }

    public static void ShowTimePickerDialog(android.content.Context context, final TimeEditText eTime, int hourOfDay, int minute)
    {
        TimePickerDialog dpd = new TimePickerDialog(
                context,
                new TimePickerDialog.OnTimeSetListener()
                {
                    public void onTimeSet(android.widget.TimePicker timePicker, int h, int m)
                    {
                        eTime.setText(TimeToString(h, m));
                    }
                },
                hourOfDay,
                minute,
                true
        );

        dpd.show();
    }

    public static void ShowDatePickerDialog(android.content.Context context, DateEditText eData, Date data)
    {
        ShowDatePickerDialog(context, eData, data.getYear(), data.getMonth(), data.getDate());
    }

    private static void ShowDatePickerDialog(android.content.Context context, final DateEditText eData, int mYear, int mMonth, int mDay)
    {
        DatePickerDialog dpd = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener()
                {
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
                    {
                        eData.setText(DateToString(selectedYear, selectedMonth + 1, selectedDay));
                    }
                },
                mYear + 1900,
                mMonth,
                mDay
        );

        dpd.show();
    }

    private static void NavigateTo(android.app.FragmentManager FragmentManager_, baseFragment to_, ePosizione Posizione_)
    {
        CurrentTitle = to_.GetTitolo();
        CurrentPosition = Posizione_;
        FragmentManager_.beginTransaction().replace(R.id.container, to_).commit();
    }

    public static void NavigateTo(android.app.Fragment this_, baseFragment to_, ePosizione Posizione_)
    {
        NavigateTo(this_.getFragmentManager(), to_, Posizione_);
    }

    public static void NavigateTo(Activity this_, baseFragment to_, ePosizione Posizione_)
    {
        NavigateTo(this_.getFragmentManager(), to_, Posizione_);
    }

    public static void NavigateTo(Activity this_, Activity to_, ePosizione Posizione_)
    {
        CurrentPosition = Posizione_;
        this_.startActivity(new Intent(this_, to_.getClass()));
    }

    public static String DateToString(int mYear, int mMonth, int mDay)
    {
        return DateToString(new Date(mYear - 1900, mMonth - 1, mDay), false);
    }

    public static String DateToString(Date d)
    {
        return DateToString(d, true);
    }

    public static String DateToString(Date d, boolean WithTime)
    {
        String u = (WithTime ? ItalianDateTimeFormatString : ItalianDateFormatString);

        return DateToString(d, u);
    }

    public static String DateToString(Date d, String formato)
    {
        if (d == null)
        {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);

        return dateFormat.format(d);
    }

    public static String DateToString(Calendar c)
    {
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        Date z = new Date(y - 1900, m, d);
        SimpleDateFormat dateFormat = new SimpleDateFormat(ItalianDateFormatString);
        String h = dateFormat.format(z);

        return h;
    }

    public static tTime StringToTime(String s)
    {
        tTime t = new tTime();

        String[] l = s.split(":");

        if (l != null && l.length == 2)
        {
            try
            {
                t.H = Integer.parseInt(l[0]);
                t.M = Integer.parseInt(l[1]);
            }
            catch (Exception e)
            {
                //e1rrore
            }
        }

        return t;
    }

    public static String TimeToString(Date d)
    {
        return TimeToString(d.getHours(), d.getMinutes());
    }

    public static String TimeToString(int h, int m)
    {
        String z = "";

        if (h < 10)
        {
            z += "0";
        }

        z += h + ":";

        if (m < 10)
        {
            z += "0";
        }

        z += m;

        return z;
    }

    public static Date StringToDate(String s)
    {
        return StringToDate(s, true);
    }

    public static Date StringToDate(String s, boolean WithTime)
    {
        Date d = new Date();

        try
        {
            String u = (WithTime ? ItalianDateTimeFormatString : ItalianDateFormatString);
            SimpleDateFormat dateFormat = new SimpleDateFormat(u);

            d = dateFormat.parse(s);
        }
        catch (ParseException e)
        {
            //errore
        }

        return d;
    }

    public static Double StringToDouble(String s)
    {
        Double d = 0d;

        try
        {
            s = s.replace(",", ".");
            d = Double.parseDouble(s);
        }
        catch (Exception e)
        {
            //not double
        }

        return d;
    }

    public static String DoubleToString(Double o)
    {
        String j = "";

        try
        {
            j = o.toString();
        }
        catch (Exception e)
        {
            //errore
        }

        return j;
    }

    public static String DoubleToEuro(Double o)
    {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

        return currencyFormatter.format(o);
    }

    public static Integer StringToInteger(String s)
    {
        if (s != null && s.length() > 0)
        {
            return Integer.parseInt(s);
        }

        return 0;
    }

    public static String IntegerToString(Integer i)
    {
        if (i == null)
        {
            return "";
        }
        else
        {
            return String.valueOf(i);
        }
    }

    public static String DateToSQLite(Date d, Date default_)
    {
        return DateToSQLite(d == null ? default_ : d);
    }

    public static String DateToSQLite(Date d)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SQLiteDateTimeFormatString);

        return dateFormat.format(d);
    }

    public static String DateToSQLite(DateTime d)
    {
        return DateToString(d, SQLiteDateTimeFormatString);
    }

    public static String DateToSQLite(Calendar d)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SQLiteDateTimeFormatString);

        return dateFormat.format(d.getTime());
    }

    public static Date SQLiteDateToDate(String d)
    {
        Date h = null;
        SimpleDateFormat sdf = new SimpleDateFormat(SQLiteDateTimeFormatString);

        try
        {
            h = sdf.parse(d);
        }
        catch (Exception e)
        {
            //Error
        }

        return h;
    }

    public static DateTime SQLiteDateToDateTime(String d)
    {
        if (d != null && d.length() > 0)
        {
            String f = SQLiteDateTimeFormatString;
            DateTimeFormatter formatter = DateTimeFormat.forPattern(f);

            if (d.length() > f.length())
            {
                d = d.substring(0, f.length());
            }

            return formatter.parseDateTime(d);
        }
        else
        {
            return new DateTime();
        }
    }

    public static Date DateAndTime(Date d, tTime t)
    {
        d.setHours(t.H);
        d.setMinutes(t.M);

        return d;
    }

    public static DateTime StringToDateTime(String s)
    {
        DateTime d = new DateTime();

        if (s != null && s.length() > 0)
        {
            try
            {
                boolean WithTime = (s.length() > 10);
                String u = (WithTime ? ItalianDateTimeFormatString : ItalianDateFormatString);
                DateTimeFormatter formatter = DateTimeFormat.forPattern(u);

                d = formatter.parseDateTime(s);
            }
            catch (Exception e)
            {
                //errore
            }
        }

        return d;
    }

    public static String DateToString(DateTime d, boolean WithTime)
    {
        String u = (WithTime ? ItalianDateTimeFormatString : ItalianDateFormatString);

        return DateToString(d, u);
    }

    private static String DateToString(DateTime d, String format)
    {
        String r = "";

        if (d != null)
        {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format);

            try
            {
                r = formatter.print(d);
            }
            catch (Exception e)
            {
                //errore
            }
        }

        return r;
    }


}