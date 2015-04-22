using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2
{
    public class RelavanceComparator : IComparer<ListViewResult>
    {
        public int Compare(ListViewResult x, ListViewResult y)
        {
            return y.Name.Preview.CompareTo(x.Name.Preview);
        }
    }
}
