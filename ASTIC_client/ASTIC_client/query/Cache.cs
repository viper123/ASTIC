using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ASTIC_client.query
{
    class Cache
    {
        Dictionary<String, WeakReference> cache;

        public Cache()
        {
            cache = new Dictionary<string, WeakReference>();
        }

        public void put(String key, List<String> predictions)
        {
            if(key != null && key.Length > 0 )
            {
                try
                {
                    cache.Add(key, new WeakReference(predictions));
                }
                catch (ArgumentException e)
                {
                    cache.Remove(key);
                    cache.Add(key, new WeakReference(predictions));
                }
            }
        }

        public List<String> get(String key)
        {
            if (cache.ContainsKey(key))
            {
                WeakReference reff = cache[key];
                if (reff != null && reff.IsAlive)
                {
                    return (List<String>)reff.Target;
                }
                else
                {
                    cache.Remove(key);
                }
            }
            return null;
        }
    }
}
