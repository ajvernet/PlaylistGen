package org.ssa.ironyard.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.controller.mapper.PlaylistMapper;
import org.ssa.ironyard.model.Episode;
import org.ssa.ironyard.model.Playlist;
import org.ssa.ironyard.model.ResponseObject;
import org.ssa.ironyard.model.ResponseObject.STATUS;
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
    public ResponseEntity<ResponseObject> savePlaylist(@RequestBody PlaylistMapper playlist, @PathVariable Integer userId) {
	return 
		ResponseEntity.ok().body(ResponseObject.instanceOf(STATUS.SUCCESS, "Your playlist was saved", 
		playlistService.savePlaylist(
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
		.build())));

    }

    @RequestMapping(value = "user/{userId}/playlists/{playlistId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deletePlaylist(@PathVariable Integer playlistId) {
	return ResponseEntity.ok().body(ResponseObject.instanceOf(STATUS.SUCCESS, "Your playlist was deleted", playlistService.deletePlaylist(playlistId)));
    }

    @RequestMapping(value = "user/{userId}/playlists", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getPlaylistsByUserId(@PathVariable Integer userId) {
	return ResponseEntity.ok().body(ResponseObject.instanceOf(STATUS.SUCCESS, "Come get your playlists", playlistService.getPlaylistsByUser(userId)));
    }

    @RequestMapping(value = "user/{userId}/playlists/{playlistId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getPlaylistById(@PathVariable Integer playlistId) {
	return ResponseEntity.ok().body(ResponseObject.instanceOf(STATUS.SUCCESS, "Here's your selected playlist", playlistService.getPlaylistById(playlistId)));
    }
}
