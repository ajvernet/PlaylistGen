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
    
    ctrl.formatTime = function (sec) {
        var hours = Math.floor(sec / 3600);
        var minutes = Math.floor((sec - (hours * 3600)) / 60);
        var seconds = sec - (hours * 3600) - (minutes * 60);
        if (hours < 10) {
            hours = "0" + hours;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return hours + ':' + minutes + ':' + seconds;
    }
    
}