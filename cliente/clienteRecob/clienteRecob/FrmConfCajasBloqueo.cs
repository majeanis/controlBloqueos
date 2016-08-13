using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Recob.Cliente.TO;
using Recob.Cliente.Rest;
using Recob.Cliente.Comun;

namespace Recob.Cliente
{
    public partial class FrmConfCajasBloqueo : Form
    {
        private List<CajaBloqueoTO> Detalle;

        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            if( FormsUtils.ProcesarEnterEnGridView(keyData, this, GrdDetalle))
            {
                return true;
            }

            return base.ProcessCmdKey(ref msg, keyData);
        }

        private void InitDetalle()
        {
            RespGenerica<List<CajaBloqueoTO>> r = Modelo.getCajas();
            if (!r.head.exitoso)
            {
                Detalle = new List<CajaBloqueoTO>();
                Utils.ShowMensajes(r.head);
                return;
            }

            Detalle = r.body;
            for(int i = 0; i < Detalle.Count; i++)
            {
                Detalle[i].estado = EstadoRegistro.Edicion;
                GrdDetalle.Rows.Add(Detalle[i].estado, Detalle[i].numero, Detalle[i].nombre, Detalle[i].vigente);
            }

        }
        public FrmConfCajasBloqueo()
        {
            InitializeComponent();
        }

        private void FrmConfCajasBloqueo_Load(object sender, EventArgs e)
        {
            InitDetalle();
        }

        private void GrdDetalle_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex < 0) return;

            CajaBloqueoTO caja = Detalle[e.RowIndex];

            switch (GrdDetalle.Columns[e.ColumnIndex].Name)
            {
                case "numero":
                    caja.numero = Int32.Parse(Utils.ToString(GrdDetalle.CurrentCell.Value));
                    GrdDetalle.Rows[e.RowIndex].Cells["nombre"].Value = "CAJA N° " + caja.numero.ToString();
                    break;

                case "nombre":
                    caja.nombre = Utils.ToString(GrdDetalle.Rows[e.RowIndex].Cells["nombre"].Value);
                    break;

                case "vigente":
                    caja.vigente = Utils.ToString(GrdDetalle.Rows[e.RowIndex].Cells["vigente"].Value).Equals("True");
                    break;

                case "estado":
                    caja.estado = (EstadoRegistro) GrdDetalle.Rows[e.RowIndex].Cells["estado"].Value;
                    break;
            }

            if ( !GrdDetalle.Columns[e.ColumnIndex].Name.Equals("estado") &&
                caja.estado == EstadoRegistro.Edicion )
            {
                GrdDetalle.Rows[e.RowIndex].Cells["estado"].Value = EstadoRegistro.Modificado;
            }
        }

        private void GrdDetalle_UserAddedRow(object sender, DataGridViewRowEventArgs e)
        {
            CajaBloqueoTO caja = CajaBloqueoTO.of();
            Detalle.Add(caja);
            GrdDetalle.Rows[Detalle.Count - 1].SetValues(caja.estado, caja.numero, caja.nombre, caja.vigente);
        }
        private void GrdDetalle_UserDeletingRow(object sender, DataGridViewRowCancelEventArgs e)
        {
            CajaBloqueoTO caja = Detalle[e.Row.Index];
            
            switch( caja.estado )
            {
                case EstadoRegistro.Eliminado:

                    e.Cancel = true;
                    return;

                case EstadoRegistro.Nuevo:
                    Detalle.RemoveAt(e.Row.Index);
                    break;

                case EstadoRegistro.Edicion:
                case EstadoRegistro.Modificado:
                    e.Cancel = true;
                    GrdDetalle.Rows[e.Row.Index].Cells["estado"].Value = caja.estado;
                    break;
            }
        }

        private void BtnEliminar_Click(object sender, EventArgs e)
        {

        }

        private void BtnGuardar_Click(object sender, EventArgs e)
        {
            int j = 0;
            for (int i = 0, salir = 0; salir==0 && i < Detalle.Count; i++)
            {
                CajaBloqueoTO caja = Detalle[i];
                RespGenerica<CajaBloqueoTO> r = null;

                switch (caja.estado)
                {
                    case EstadoRegistro.Nuevo:
                    case EstadoRegistro.Modificado:
                        r = Modelo.guardarCaja(caja);
                        if (r.head.exitoso)
                        {
                            caja = r.body;
//                            caja.estado = EstadoRegistro.Edicion;
                            GrdDetalle.Rows[i].Cells["estado"].Value = caja.estado;
                        }
                        else
                        {
                            Utils.ShowMensajes(r.head);
                            salir = 1;
                        }
                        break;

                    case EstadoRegistro.Eliminado:
                        r = Modelo.eliminarCaja(caja.numero);
                        if (r.head.exitoso)
                        {
                            Detalle.RemoveAt(i);
                            GrdDetalle.Rows.RemoveAt(i);
                            i--;
                        }
                        else
                        {
                            Utils.ShowMensajes(r.head);
                            salir = 1;
                        }
                        break;
                }
            }
        }
    }
}
