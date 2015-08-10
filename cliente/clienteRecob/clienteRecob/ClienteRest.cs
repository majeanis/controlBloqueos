using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using Newtonsoft.Json;
using Recob.Cliente;
using Recob.Cliente.Comun;

namespace Recob.Cliente.Rest
{
    class ClienteRest
    {
        private static WebClient getWebClient(String token)
        {
            WebClient syncClient = new WebClient();
            
            syncClient.Headers.Add("token", token);
            syncClient.Encoding = Encoding.UTF8;

            return syncClient;
        }

        public static RespGenerica<T> doGET<T>(String token, String url)
        {
            WebClient syncClient = getWebClient(token);
            syncClient.Headers["Content-type"] = "application/json";
            var jsonResp = syncClient.DownloadString(url);
            return JsonUtils.fromJson<RespGenerica<T>>(jsonResp);
        }
        public static RespGenerica<T> doPUT<T>(String token, String url, String data)
        {
            WebClient syncClient = getWebClient(token);
            syncClient.Headers["Content-type"] = "application/x-www-form-urlencoded";
            var jsonResp = syncClient.UploadString(url, "PUT", data);
            return JsonUtils.fromJson<RespGenerica<T>>(jsonResp);
        }

        public static RespGenerica<T> doDELETE<T>(String token, String url)
        {
            WebClient syncClient = getWebClient(token);
            syncClient.Headers["Content-type"] = "application/x-www-form-urlencoded";
            var jsonResp = syncClient.UploadString(url, "DELETE", "");
            return JsonUtils.fromJson<RespGenerica<T>>(jsonResp);
        }
    }
}
