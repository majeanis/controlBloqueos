using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using Newtonsoft.Json;
using Recob.Cliente;

namespace Recob.Cliente.Rest
{
    class ClienteRest
    {
        private static WebClient getWebClient(String token)
        {
            WebClient syncClient = new WebClient();
            
            syncClient.Headers.Add("token", token);
            syncClient.Headers["Content-type"] = "application/json";
            syncClient.Encoding = Encoding.UTF8;

            return syncClient;
        }

        private static RespGenerica<T> fromJson<T>(String respuestaJson)
        {
            RespGenerica<T> r = JsonConvert.DeserializeObject<RespGenerica<T>>(respuestaJson);
            return r;
        }

        public static RespGenerica<T> doGET<T>(String token, String url)
        {
            WebClient syncClient = getWebClient(token);
            var jsonResp = syncClient.DownloadString(url);
            return fromJson<T>(jsonResp);
        }
        public static RespGenerica<T> doPUT<T>(String token, String url, String data)
        {
            WebClient syncClient = getWebClient(token);
            var jsonResp = syncClient.UploadString(url, "PUT", data);
            return fromJson<T>(jsonResp);
        }

        public static RespGenerica<T> doDELETE<T>(String token, String url)
        {
            WebClient syncClient = getWebClient(token);
            var jsonResp = syncClient.UploadString(url, "DELETE", "");
            return fromJson<T>(jsonResp);
        }
    }
}
