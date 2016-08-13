using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Recob.Cliente.Comun
{
    class FormsUtils
    {
        public static bool ProcesarEnterEnGridView(Keys keyData, Form form, DataGridView grid)
        {
            if (keyData != Keys.Enter)
            {
                return false;
            }

            if (!(form.ActiveControl is DataGridViewTextBoxEditingControl || form.ActiveControl is DataGridView))
            {
                return false;
            }

            //
            // Si llegamos a este punto, entonces se trata de un ENTER sobre la Grilla
            //

            if (grid.CurrentCell.ColumnIndex == grid.Columns.Count - 1 &&
               grid.CurrentCell.RowIndex == grid.Rows.Count - 1)
            {
                grid.Rows.Add();
                grid.CurrentCell = grid.Rows[grid.Rows.Count - 1].Cells[0];
            }
            else if (grid.CurrentCell.ColumnIndex == grid.Columns.Count - 1)
            {
                grid.CurrentCell = grid.Rows[grid.CurrentCell.RowIndex + 1].Cells[0];
            }
            else
            {
                grid.CurrentCell = grid.Rows[grid.CurrentCell.RowIndex].Cells[grid.CurrentCell.ColumnIndex + 1];
            }

            return true;
        }

    }
}
