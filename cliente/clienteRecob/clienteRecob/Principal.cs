using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Windows.Forms;
using Recob.Cliente.Rest;
using Recob.Cliente.TO;

namespace Recob.Cliente
{
    class Principal
    {
        static void Main(String[] args)
        {
            FrmConfCajasBloqueo form = new FrmConfCajasBloqueo();
            form.ShowDialog();

            //String token = "9cc5882c-2dd9-11e5-ac59-080027465435";
            //String url = "http://192.168.56.10:5080/recobWS/rest/configuracion/cajasBloqueo/3";
            ////RespGenerica<List<CajaBloqueoTO>> r = ClienteRest.doGET<List<CajaBloqueoTO>>(token, url);
            //RespGenerica<CajaBloqueoTO> r = ClienteRest.doGET<CajaBloqueoTO>(token, url);
            ////MessageBox.Show(r.ToString());

            //CajaBloqueoTO caja = new CajaBloqueoTO();
            //caja.numero = 3;
            //caja.vigente = true;
            //caja.nombre = "CAJA N° 3";
            //RespGenerica<CajaBloqueoTO> r2 = Modelo.guardarCaja(caja);

            //RespGenerica<CajaBloqueoTO> d = Modelo.eliminarCaja(4);
            //RespGenerica<CajaBloqueoTO> c = Modelo.getCaja(4);
            //RespGenerica<List<CajaBloqueoTO>> l = Modelo.getCajas();


            //RespGenerica<CajaBloqueoTO> r2 = Modelo.guardarCaja(caja);
            //if(r2.encabezado.codigo == 1)
            //{
            //    MessageBox.Show(r2.contenido.numero.ToString());
            //}

            //String jsonCaja = JsonConvert.SerializeObject(caja);

            //String url2 = "http://192.168.56.10:5080/recobWS/rest/configuracion/cajasBloqueo/4";
            ////RespGenerica<CajaBloqueoTO> r1 = ClienteRest.doPUT<CajaBloqueoTO>(token, url2, "caja=" + jsonCaja);
            //RespGenerica<CajaBloqueoTO> r1 = ClienteRest.doDELETE<CajaBloqueoTO>(token, url2);
        }
    }
}
