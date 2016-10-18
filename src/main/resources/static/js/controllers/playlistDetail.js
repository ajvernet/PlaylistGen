angular.module("podcaster").controller("PlaylistDetailController", PlaylistDetailCtrl)
PlaylistDetailCtrl.$inject = ['$scope', '$http', '$state', '$stateParams', 'PlaylistService']

function PlaylistDetailCtrl($scope, $http, $state, $stateParams, PlaylistService) {
    
    var ctrl = this;
    
    ctrl.user = window.location.pathname.replace("/podcasts/user/","").replace("/","");
    ctrl.playlistId = $stateParams.toDetailId;
    ctrl.playlistName = "";
    ctrl.targetDuration = "";
    ctrl.playlist = [];
    
    ctrl.shown = true;
    
    ctrl.catchNullParam = function(){
    	
    	if(ctrl.playlistId == null){
    		$state.go("playlists")
    	}
    	else{
    
		    $http.get("/podcasts/user/" + ctrl.user + "/playlists/" + ctrl.playlistId)
		    .then(function(response) {
		    	ctrl.playlistName = response.data.obj.name;
		    	ctrl.targetDuration = response.data.obj.targetDuration;
		    	//console.log(response.data.obj);
		    	for(i=0; i < response.data.obj.episodes.length; i++){
		    		var tempObj = {
		    				'id': response.data.obj.episodes[i].name,
		    				'title': response.data.obj.episodes[i].name,
		    				'artist': response.data.obj.episodes[i].show.name,
		    				'duration': response.data.obj.episodes[i].duration,
		    				'url': response.data.obj.episodes[i].fileUrl,
		    				'description': response.data.obj.episodes[i].description,
		    				'json': response.data.obj.episodes[i]
		    		}
		    		ctrl.playlist.push(tempObj);
		    	}
		    	//console.log(ctrl.playlist);
		    })
    }
    }
    ctrl.catchNullParam();
    
    ctrl.playPL = function(){
    	ctrl.shown = false;
    }
    
    ctrl.editPL = function(){
    	PlaylistService.clear();
    	PlaylistService.edit(ctrl.playlistId, ctrl.playlistName, ctrl.targetDuration, ctrl.playlist);
    	$state.go("createPlaylist");
    }
    
    ctrl.deletePL = function(){
    $http.delete("/podcasts/user/" + ctrl.user + "/playlists/" + ctrl.playlistId)
    .then(function(response) {
    	console.log(response);
    	if(response.data.obj == true){
    		alert("Playlist successfully deleted");
    		$state.go("playlists");
    	}
    	else{
    		alert("Delete failed");
    	}
    })
    }
}