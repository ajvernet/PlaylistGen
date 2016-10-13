angular.module('podcaster').controller("PlaylistController", PlaylistCtrl)
PlaylistCtrl.$inject = ['$http', '$state']

function PlaylistCtrl($http, $state) {
    var ctrl = this;
    
    ctrl.user = window.location.pathname.replace("/podcasts/user/","").replace("/","");
    
    ctrl.playlists = [];
    
    $http.get("/podcasts/user/" + ctrl.user + "/playlists")
    .then(function(response) {
    	console.log(response.data.obj);
    	for(i=0; i < response.data.obj.length; i++){
    		var tempObj = {
    				'id': response.data.obj[i].id,
    				'name': response.data.obj[i].name,
    				'currentDuration': response.data.obj[i].currentDuration
    		}
    		ctrl.playlists.push(tempObj);
    		console.log(tempObj);
    	}	
    })
    
    ctrl.toDetail = function(playlist){
    	$state.go("playlistDetail", {toDetailId: playlist.id});
    }
    
}