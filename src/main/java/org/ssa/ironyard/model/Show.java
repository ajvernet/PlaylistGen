package org.ssa.ironyard.model;

    public class Show extends DomainObject{

        final String name;
        

        public Show(Integer id, boolean loaded, String name) {
            super(id, loaded);
            this.name = name;
        }
        
        public Show(String name)
        {
            this(null, false, name);
        }

        public Show setId(Integer id)
        {
            return new Show(id, this.loaded, this.name);
        }
        
        public Show setLoaded()
        {
            return new Show(this.id, true, this.name);
        }
        
        @Override
        public boolean deeplyEquals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            Show other = (Show) obj;
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
