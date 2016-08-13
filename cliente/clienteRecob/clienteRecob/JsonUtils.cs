using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace Recob.Cliente.Comun
{
    class JsonUtils
    {
        public static String toJson(Object objeto)
        {
            return JsonConvert.SerializeObject(objeto);
        }

        public static T fromJson<T>(String json)
        {
            return JsonConvert.DeserializeObject<T>(json);
        }
    }
}
