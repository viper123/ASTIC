using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ASTIC_client.query
{
    class Result
    {
        private String filePath;
        private float weight;
        private bool isPrimary;
        private String clusterName;

        public Result(String filePath, int weight, bool isPrimary,String clusterName)
        {
            this.filePath = filePath;
            this.weight = weight;
            this.isPrimary = isPrimary;
            this.clusterName = clusterName;
        }
        public String getFilePath()
        {
            return filePath;
        }
        public void setFilePath(String filePath)
        {
            this.filePath = filePath;
        }
        public float getWeight()
        {
            return weight;
        }
        public void setWeight(int weight)
        {
            this.weight = weight;
        }
        public bool getIsPrimary()
        {
            return isPrimary;
        }
        public void setPrimary(bool isPrimary)
        {
            this.isPrimary = isPrimary;
        }
        public String getClusterName()
        {
            return clusterName;
        }
        public void setClusterName(String clusterName)
        {
            this.clusterName = clusterName;
        }
    }
}
