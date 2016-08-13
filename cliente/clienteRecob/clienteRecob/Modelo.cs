using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Recob.Cliente.TO;
using Recob.Cliente.Rest;
using Recob.Cliente.Comun;

namespace Recob.Cliente
{
    class Modelo
    {
        private static String urlCajas;
        private static String token;

        static Modelo()
        {
            token = Properties.Token;
            urlCajas = Properties.UrlCajasBloqueo;
        }

        public static RespGenerica<CajaBloqueoTO> guardarCaja(CajaBloqueoTO caja)
        {
            String data = JsonUtils.toJson(caja);
            RespGenerica<CajaBloqueoTO> r = ClienteRest.doPUT<CajaBloqueoTO>(token, urlCajas, data);
            return r;
        }

        public static RespGenerica<List<CajaBloqueoTO>> getCajas()
        {
            RespGenerica<List<CajaBloqueoTO>> r = ClienteRest.doGET<List<CajaBloqueoTO>>(token, urlCajas);
            return r;
        }

        public static RespGenerica<CajaBloqueoTO> getCaja(int numero)
        {
            RespGenerica<CajaBloqueoTO> r = ClienteRest.doGET<CajaBloqueoTO>(token, urlCajas + "/" + numero.ToString());
            return r;
        }

        public static RespGenerica<CajaBloqueoTO> eliminarCaja(int numero)
        {
            RespGenerica<CajaBloqueoTO> r = ClienteRest.doDELETE<CajaBloqueoTO>(token, urlCajas + "/" + numero.ToString());
            return r;
        }
    }
}
