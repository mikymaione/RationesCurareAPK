package it.mikymaione.RationesCurare.UI.DataGrid;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

class Sort
{

    public static int SORT_NOSORT = -1;
    public static int SORT_ASC = 0;
    public static int SORT_DESC = 1;
    private DataGrid.MemberCollection mc;

    public Sort(DataGrid.MemberCollection mc)
    {
        this.mc = mc;
    }

    public void sortByColumn(int columnToSort, int sortOrder)
    {
        // sortResult
        Collections.sort(mc.DATA_SOURCE.getAllRows(), new ArrayColumnComparator(columnToSort, sortOrder));
    }

    private class ArrayColumnComparator implements Comparator<DataTable.DataRow>
    {

        private int columnToSortOn = 0;
        private int sortOrder = 0;

        ArrayColumnComparator(int columnToSortOn, int sortOrder)
        {
            this.columnToSortOn = columnToSortOn;
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(DataTable.DataRow arg0, DataTable.DataRow arg1)
        {
            int resu = 0;
            Object str0 = arg0.get(columnToSortOn);
            Object str1 = arg1.get(columnToSortOn);

            if (str0 != null && str1 != null)
            {
                if (str0 instanceof String && str1 instanceof String)
                {
                    resu = ((String) str0).compareTo((String) str1);
                }
                else if (str0 instanceof Integer && str1 instanceof Integer)
                {
                    resu = ((Integer) str0).compareTo((Integer) str1);
                }
                else if (str0 instanceof Double && str1 instanceof Double)
                {
                    resu = ((Double) str0).compareTo((Double) str1);
                }
                else if (str0 instanceof Date && str1 instanceof Date)
                {
                    resu = ((Date) str0).compareTo((Date) str1);
                }
            }

            if (sortOrder == SORT_DESC && resu != 0)
            {
                resu *= -1;
            }

            return resu;
        }


    }


}