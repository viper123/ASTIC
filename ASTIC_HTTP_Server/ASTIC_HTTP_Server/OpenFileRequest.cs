using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace ASTIC_HTTP_Server
{
    [DataContract]
    public class OpenFileRequest
    {
        [DataMember(Name="path")]
        public string FilePath;
    }
}