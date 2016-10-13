package org.ssa.ironyard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.controller.mapper.EpisodeMapper;
import org.ssa.ironyard.controller.mapper.PlaylistMapper;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.Show;
import org.ssa.ironyard.model.User;
import org.ssa.ironyard.service.PlaylistService;

@RestController
@RequestMapping("/podcasts")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
	this.playlistService = playlistService;
    }

    @RequestMapping(value = "user/{userId}/playlists", method = RequestMethod.POST)
    public Playlist savePlaylist(@RequestBody PlaylistMapper playlist, @PathVariable Integer userId) {
	return playlistService.savePlaylist(
		Playlist.builder()
		.id(playlist.getId())
		.name(playlist.getName())
		.user(User.builder().id(userId).build())
		.targetDuration(playlist.getTargetDuration())
		.currentDuration(playlist.getCurrentDuration())
		.episodes(playlist.getEpisodes()
			.stream()
			.map(e -> {
        			    return Episode.builder()
        			    .id(e.getId())
        			    .duration(e.getDuration())
        			    .description(e.getDescription())
        			    .episodeId(e.getEpisodeId())
        			    .fileUrl(e.getFileUrl())
        			    .name(e.getName())
        			    .show(new Show(e.getShow().getId(), e.getShow().getName(), e.getShow().getThumbUrl()))
        			    .build();
				}
			).collect(Collectors.toList()))
		.build());

    }

    @RequestMapping(value = "user/{userId}/playlists/{playlistId}", method = RequestMethod.DELETE)
    public boolean deletePlaylist(@PathVariable Integer playlistId) {
	return playlistService.deletePlaylist(playlistId);
    }

    @RequestMapping(value = "user/{userId}/playlists", method = RequestMethod.GET)
    public List<Playlist> getPlaylistsByUserId(@PathVariable Integer userId) {
	return playlistService.getPlaylistsByUser(userId);
    }

    @RequestMapping(value = "user/{userId}/playlists/{playlistId}", method = RequestMethod.GET)
    public Playlist getPlaylistById(@PathVariable Integer playlistId) {
	return playlistService.getPlaylistById(playlistId);
    }
}
