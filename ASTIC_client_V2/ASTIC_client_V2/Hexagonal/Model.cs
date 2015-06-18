
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
            foreach (Cluster c in clusters)
            {
                if (c.queryScore > maxScore)
                {
                    max = c;
                    maxScore = c.queryScore;
                    index = k;
                }
                k++;
            }
            Board board = new Board(5 * 2+2, 5 * 2, 44, HexOrientation.Flat);
            int n = 5;
            board.Hexes[n, n].Cluster = max;
            for (int i = 0; i < distanceMatrix[index].Length; i++)
            {
                if (i == index)
                {
                    continue;
                }
                Cluster other = clusters.ElementAt(i);
                double distance = distanceMatrix[index][i];
                int ii = n;
                int jj = n - getHexIndexFromDistance(distance);
                if (board.Hexes[ii, jj].Cluster != null)
                {
                    
                }
                else
                {
                    board.Hexes[ii, jj].Cluster = other;
                }
            }

            return board;
        }

        public int getHexIndexFromDistance(double distance)
        {
            if (distance == 1.0d)
            {
                return 5;
            }
            return (int)(distance * 10)/2;
        }
    }
}
