using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Recob.Cliente.TO
{
    public class UbicacionTO : BaseTO
    {
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public bool vigente { get; set; }
        public string token { get; set; }
        public EstacionTO estacion { get; set; }
    }
}
