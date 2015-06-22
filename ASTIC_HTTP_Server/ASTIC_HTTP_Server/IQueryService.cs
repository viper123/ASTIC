using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace ASTIC_HTTP_Server
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IQueryService" in both code and config file together.
    [ServiceContract]
    public interface IQueryService
    {
        [OperationContract]
        void DoWork();

        [OperationContract]
        [WebInvoke(Method = "GET", ResponseFormat = WebMessageFormat.Json,
            BodyStyle = WebMessageBodyStyle.Wrapped,
            UriTemplate = "query/{query}")]
        String Query(string query);
    }
}
