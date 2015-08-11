using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Recob.Cliente.Comun;

namespace Recob.Cliente.TO
{
    public class CajaBloqueoTO : BaseTO
    {
        public int numero { get; set; }
        public string nombre { get; set; }
        public bool vigente { get; set; }
        public UbicacionTO ubicacion { get; set; }
        
        public static CajaBloqueoTO of()
        {
            CajaBloqueoTO caja = new CajaBloqueoTO();
            caja.estado = EstadoRegistro.Nuevo;
            caja.vigente = true;
            caja.ubicacion = new UbicacionTO();
            caja.ubicacion.token = Properties.Token;

            return caja;
        }
    }

}
