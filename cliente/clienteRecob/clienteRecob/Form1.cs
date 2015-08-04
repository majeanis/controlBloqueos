using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json;
using Recob.Cliente.TO;
using Recob.Cliente.Rest;

namespace clienteRecob
{
    public partial class formPrincipal : Form
    {
        public formPrincipal()
        {
            InitializeComponent();
        }

        private void formPrincipal_Load(object sender, EventArgs e)
        {
            //var json = WeatherApiClient.getListaCajasBloqueo();
            //RespGenerica<CajaBloqueoTO> resp = JsonConvert.DeserializeObject<RespGenerica<CajasBloqueoTO>>(json);

            //for (int i = 0; i < resp.contenido.Count; i++)
            //{
            //    gridCajas.Rows.Add(resp.contenido[i].id, resp.contenido[i].numero, resp.contenido[i].nombre, resp.contenido[i].vigente);
            //}
        }
    }
}
