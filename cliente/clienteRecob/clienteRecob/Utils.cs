using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Reflection;
using Recob.Cliente.Rest;

namespace Recob.Cliente.Comun
{
    public class Utils
    {
        public static void ShowMensajes(RespHead head)
        {
            if(head.mensajes.Length == 0)
                return;

            String texto = "";

            for(int i = 0; i < head.mensajes.Length; i++)
            {
                texto = texto + head.mensajes[i].texto + ",\n";
            }

            texto = texto.Substring(0, texto.Length - 2);
            if( head.exitoso )
            {
                MessageBox.Show(texto, "Mensaje de Información");
            } else
            {
                MessageBox.Show(texto, "Mensaje de Error");
            }
        }

        public static String ToStringBuilder(Object obj)
        {
            if(obj==null)
            {
                return "[null]";
            }

            StringBuilder sb = new StringBuilder(obj.GetType().Name);
            sb.Append("[");
            
            FieldInfo[] fields = obj.GetType().GetFields(BindingFlags.Instance | BindingFlags.NonPublic);
            PropertyInfo[] props = obj.GetType().GetProperties(BindingFlags.Instance | BindingFlags.Public | BindingFlags.NonPublic);

            if(props.Length == 0)
            {
                sb.Append("]");
                return sb.ToString();
            }

            foreach(PropertyInfo prop in props)
            {
                sb.Append(prop.Name).Append("=").Append(prop.GetValue(obj));
                sb.Append(",");
            }

            return sb.ToString(0, sb.Length - 1) + "]";
        }

        public static String ToString(Object obj)
        {
            if (obj == null) return "";
            return obj.ToString();
        }

    }
}
