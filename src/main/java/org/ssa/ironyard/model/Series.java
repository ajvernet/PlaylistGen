package org.ssa.ironyard.model;

    public class Series extends DomainObject{

        final String name;
        

        public Series(Integer id, boolean loaded, String name) {
            super(id, loaded);
            this.name = name;
        }
        
        public Series(String name)
        {
            this(null, false, name);
        }

        public Series setId(Integer id)
        {
            return new Series(id, this.loaded, this.name);
        }
        
        public Series setLoaded()
        {
            return new Series(this.id, true, this.name);
        }
        
        @Override
        public boolean deeplyEquals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            Series other = (Series) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return super.toString() + "PodcastSeries [name=" + name + "]";
        }
        
        
    
}
