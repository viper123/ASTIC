using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ASTIC_client.clustering
{
    class Cluster
    {
        [JsonProperty]
        public String id;
        [JsonProperty]
        public Dictionary<String,Int32> wordWeightMap;
        [JsonProperty]
        public Dictionary<String, List<String>> fileWordMap;
    }
}
