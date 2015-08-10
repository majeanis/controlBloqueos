using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;

namespace Recob.Cliente.Comun
{
    class Properties
    {
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
