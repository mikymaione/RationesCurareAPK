package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.widget.TextView;

class Header extends TextView
{
    private DataGrid.MemberCollection mc;

    public Header(Context context, DataGrid.MemberCollection mc)
    {
        super(context);

        this.mc = mc;

        setBackgroundColor(DataGridStyle.HeaderCell.BackgroundColor);
        //setTextSize(DataGridStyle.HeaderCell.FontSize);
        //setTextColor(HeaderCell.ForegroundColor);
        setPadding(DataGridStyle.HeaderCell.LPadding, DataGridStyle.HeaderCell.TPadding, DataGridStyle.HeaderCell.RPadding, DataGridStyle.HeaderCell.BPadding);
        setSingleLine(true);
        setGravity(DataGridStyle.HeaderCell.Gravity);
    }


}