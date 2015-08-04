using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Recob.Cliente.TO
{
    public class CajaBloqueoTO
    {
        public int numero { get; set; }
        public string nombre { get; set; }
        public bool vigente { get; set; }
        public UbicacionTO ubicacion { get; set; }
    }

}
