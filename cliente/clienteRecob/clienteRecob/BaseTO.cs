using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Recob.Cliente.Comun;
using Newtonsoft.Json;

namespace Recob.Cliente.TO
{
    public enum EstadoRegistro
    {
        Edicion = 1, Nuevo = 2, Modificado = 3, Eliminado = 4
    }

    public class BaseTO
    {   
        [JsonIgnore]
        public EstadoRegistro estado { get; set; }

        public override String ToString()
        {
            return Utils.ToStringBuilder(this);
        }
    }
}
