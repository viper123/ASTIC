using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace ASTIC_HTTP_Server
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "QueryService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select QueryService.svc or QueryService.svc.cs at the Solution Explorer and start debugging.
    public class QueryService : IQueryService
    {
        public void DoWork()
        {
        }

        public String Query(String query)
        {
            return "Felicitary ai reusit sa rulezi ceva";
        }
    }
}
