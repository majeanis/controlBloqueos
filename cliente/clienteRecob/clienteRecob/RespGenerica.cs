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
        public RespHead head { get; set; }
        public T        body { get; set; }
    }
}
