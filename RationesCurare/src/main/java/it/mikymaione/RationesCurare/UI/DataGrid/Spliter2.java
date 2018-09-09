package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.TextView;

class Spliter2 extends TextView
{

    public Spliter2(Context context, DataGrid.MemberCollection mc, int intIndex)
    {
        super(context);
        setBackgroundColor(DataGridStyle.SpliterCell.OnClickBackgroundColor);
        setBackgroundColor(DataGridStyle.SpliterCell.BackgroundColor);

        if (mc.COLUMN_STYLE.get(intIndex).getWitdh() == 0)
        {
            setPadding(0, DataGridStyle.SpliterCell.TPadding, 0, DataGridStyle.SpliterCell.BPadding);
        }
        else
        {
            setPadding(DataGridStyle.SpliterCell.LPadding, DataGridStyle.SpliterCell.TPadding, DataGridStyle.SpliterCell.RPadding, DataGridStyle.SpliterCell.BPadding);
        }

        //setTextSize(DataGridStyle.SpliterCell.FontSize);
        setText("");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        return true;
    }


}