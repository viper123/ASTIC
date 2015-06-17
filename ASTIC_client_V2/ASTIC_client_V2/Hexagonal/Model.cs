
using ASTIC_client.clustering;
using Hexagonal;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2.Hexagonal
{
    public class Model
    {
        public Board drawHexGraphics(List<Cluster> clusters,double [][] distanceMatrix)
        {
            Cluster max = null;
            float maxScore = 0;
            int index = 0,k=0;
            foreach(Cluster c in clusters)
            {
                if (c.queryScore > maxScore)
                {
                    max = c;
                    maxScore = c.queryScore;
                    index = k;
                }
                k++;
            }
            Board board = new Board(clusters.Count * 2, clusters.Count * 2, 34, HexOrientation.Flat); 
            int n = clusters.Count;
            board.Hexes[n, n].Cluster = max;
            for (int i = 0; i < distanceMatrix[index].Length&&i!=index; i++)
            {
                Cluster other = clusters.ElementAt(i);
                double distance = distanceMatrix[index][i];
                int x = n - getHexIndexFromDistance(distance);
                int y = n;
                if (board.Hexes[x, y].Cluster != null)
                {
                    
                }
                else
                {
                    board.Hexes[x, y].Cluster = other;
                }
            }

            return board;
        }

        private int getHexIndexFromDistance(double distance)
        {
            if (distance == 1.0d)
            {
                return 9;
            }
            return (int)distance * 10;
        }
    }
}
