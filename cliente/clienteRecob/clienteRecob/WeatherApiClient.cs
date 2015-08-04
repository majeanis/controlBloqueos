using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using Newtonsoft.Json;
using Recob.Cliente.TO;
using Recob.Cliente.Rest;

namespace clienteRecob
{
    public static class WeatherApiClient
    {
        public static String GetWeatherForecast()
        {
            var url = "http://api.openweathermap.org/data/2.1/find/station?lat=55&lon=37&cnt=10";
            var syncClient = new WebClient();
            return syncClient.DownloadString(url);
        }

        public static String getListaCajasBloqueo()
        {
            var url = "http://192.168.56.10:5080/recobWS/rest/configuracion/cajasBloqueo?accion=1";
            var syncClient = new WebClient();
            syncClient.Headers.Add("token", "9cc5882c-2dd9-11e5-ac59-080027465435");
            syncClient.Headers["Content-type"] = "application/json";
            syncClient.Encoding = Encoding.UTF8;
            var jsonResp = syncClient.DownloadString(url);
            var jsonHead = syncClient.ResponseHeaders.Get("resultado");
            RespHead head = JsonConvert.DeserializeObject<RespHead>(jsonHead);
            RespGenerica<CajaBloqueoTO[]> r2 = JsonConvert.DeserializeObject<RespGenerica<CajaBloqueoTO[]>>(jsonResp);
            return jsonResp;
        }

        public static String getTesting()
        {
            var url = "http://192.168.56.10:5080/recobWS/rest/testing/objeto";
            var syncClient = new WebClient();
            syncClient.Headers["Content-type"] = "application/json";
            syncClient.Encoding = Encoding.UTF8;
            var ret = syncClient.DownloadString(url);
            var hd = syncClient.Headers;
            return ret;
        }
    }
}
