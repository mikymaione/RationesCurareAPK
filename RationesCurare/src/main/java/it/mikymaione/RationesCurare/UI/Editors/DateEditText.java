package it.mikymaione.RationesCurare.UI.Editors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import org.joda.time.DateTime;

import java.util.Date;

import it.mikymaione.RationesCurare.Globals.GB;

/**
 * Created by michele on 19/03/15.
 */
public class DateEditText extends EditText
{

    public DateEditText(Context context)
    {
        super(context);
        setListener(context);
    }

    public DateEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setListener(context);
    }

    public DateEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setListener(context);
    }

    private void setListener(final Context context)
    {
        this.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GB.ShowDatePickerDialog(context, DateEditText.this, GB.StringToDate(getText().toString(), false));
            }
        });
    }

    public void SetNow()
    {
        setText(GB.DateToString(new Date(), false));
    }

    public Date getDate()
    {
        String u = getText().toString();

        return GB.StringToDate(u, false);
    }

    public DateTime getDateTime()
    {
        String u = getText().toString();

        return GB.StringToDateTime(u);
    }

    public boolean isSetted()
    {
        String u = getText().toString();

        return (!u.equals(""));
    }


}