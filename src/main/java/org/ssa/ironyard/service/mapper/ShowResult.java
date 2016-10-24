package org.ssa.ironyard.service.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Show;

public class ShowResult {

    private List<Integer> episode_ids;
    
    Episode episode;

    public List<Integer> getEpisode_ids() {
        return episode_ids;
    }

    public void setEpisode_ids(List<Integer> episode_ids) {
        this.episode_ids = episode_ids;
    }

    public void setEpisode(Episode episode)
    {
        this.episode = episode;
    }

    public List<Integer> filter()
    {
        final Integer externalId = this.episode.getEpisodeId();
        List<Integer> results = new ArrayList<>(this.episode_ids.size() >> 1);
        for (Integer external : this.episode_ids)
        {
            if (external.compareTo(externalId) > 0)
                results.add(external);
        }
        results.sort(Comparator.<Integer>naturalOrder().reversed());
        return results;
    }
    Show getShow()
    {
        return this.episode == null ? null : this.episode.getShow();
    }

    @Override
    public String toString()
    {
        return "ShowResult{" + getShow() + ": episode_ids=" + episode_ids + '}';
    }
    
    
    
}
