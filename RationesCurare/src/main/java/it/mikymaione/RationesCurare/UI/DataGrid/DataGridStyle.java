package it.mikymaione.RationesCurare.UI.DataGrid;

import android.graphics.Color;

class DataGridStyle
{

    public class DataGrid
    {
        public static final int BackgroundColor = Color.TRANSPARENT;
    }

    public class HeaderContainer
    {
        public static final int BackgroundColor = Color.BLACK;
        public static final int TextColor = Color.WHITE;
    }

    public class HeaderCell
    {
        public static final int Height = 60;
        public static final int Width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        public static final int LPadding = 5;
        public static final int RPadding = 5;
        public static final int TPadding = 3;
        public static final int BPadding = 3;
        public static final int BackgroundColor = Color.TRANSPARENT;
        public static final int Gravity = android.view.Gravity.CENTER_VERTICAL;
    }

    public class DataContainer
    {
        public static final int BackgroundColor = Color.TRANSPARENT;
    }

    public class DataCell
    {
        public static final int Height = HeaderCell.Height;
        public static final int Gravity = HeaderCell.Gravity;
        public static final int LPadding = 5;
        public static final int RPadding = 5;
        public static final int TPadding = 2;
        public static final int BPadding = 2;
        public static final int ForegroundColor = Color.BLACK;
    }


}