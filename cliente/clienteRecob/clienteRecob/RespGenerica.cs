using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace Recob.Cliente.Rest
{
    class RespGenerica<T>
    {
        public RespHead encabezado { get; set; }
        public RespMensaje[] mensajes { get; set; }

        public T contenido { get; set; }
    }
}
