using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Recob.Cliente.Rest
{
    public class RespHead
    {
        public string fecha { get; set; }
        public string severidad { get; set; }
        public RespMensaje[] mensajes { get; set; }
        public bool exitoso { get; set; }
    }
}
