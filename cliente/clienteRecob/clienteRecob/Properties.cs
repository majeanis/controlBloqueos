using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Recob.Cliente.Utils
{
    class Properties
    {
        static Properties()
        {
            UrlBase = "http://192.168.56.10:5080/recobWS/rest/";
        }

        public static String UrlBase { get; set; }
    }
}
