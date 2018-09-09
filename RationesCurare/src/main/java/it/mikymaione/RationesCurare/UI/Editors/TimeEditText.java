package it.mikymaione.RationesCurare.UI.Editors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.tTime;

/**
 * Created by michele on 20/03/15.
 */
public class TimeEditText extends EditText
{

    public TimeEditText(Context context)
    {
        super(context);
        setListener(context);
    }

    public TimeEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setListener(context);
    }

    public TimeEditText(Context context, AttributeSet attrs, int defStyle)
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
                GB.ShowTimePickerDialog(context, TimeEditText.this, GB.StringToTime(getText().toString()));
            }
        });
    }

    public void SetNow()
    {
        setText(GB.TimeToString(new Date()));
    }

    public tTime getTime()
    {
        String u = getText().toString();

        return GB.StringToTime(u);
    }

}