package org.ssa.ironyard.model;

    public class PodcastSeries extends DomainObject{

        final String name;
        

        public PodcastSeries(Integer id, boolean loaded, String name) {
            super(id, loaded);
            this.name = name;
        }
        
        public PodcastSeries(String name)
        {
            this(null, false, name);
        }

        public PodcastSeries setId(Integer id)
        {
            return new PodcastSeries(id, this.loaded, this.name);
        }
        
        public PodcastSeries setLoaded()
        {
            return new PodcastSeries(this.id, true, this.name);
        }
        
        @Override
        boolean deeplyEquals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            PodcastSeries other = (PodcastSeries) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
    
}
