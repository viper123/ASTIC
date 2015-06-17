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
        public Dictionary<String,float> wordWeightMap;
        [JsonProperty]
        public Dictionary<String, List<String>> fileWordMap;
        [JsonProperty]
        public List<String> preview;
        [JsonProperty]
        public Dictionary<String, float> reprezentativeWordsMap;
        [JsonProperty]
        public List<String> reprezentativeWords;
        [JsonProperty]
        public float queryScore;


    }
}
