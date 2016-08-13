using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;
using Recob.Cliente.TO;

namespace Recob.Cliente.Comun
{
    class Properties
    {
        private static UbicacionTO _ubicacion;

        public static UbicacionTO Ubicacion
        {
            get
            {
                if (_ubicacion != null)
                    return _ubicacion;

                _ubicacion = new UbicacionTO();
                _ubicacion.token = Properties.Token;
                return _ubicacion;
            }
        }

        public static String Token
        {
            get 
            {
                return ConfigurationManager.AppSettings["token"];
            }
        }

        public static String UrlCajasBloqueo
        {
            get
            {
                return ConfigurationManager.AppSettings["urlCajasBloqueo"];
            }
        }
    }
}
