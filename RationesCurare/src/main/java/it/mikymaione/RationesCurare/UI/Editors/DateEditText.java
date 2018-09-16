/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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