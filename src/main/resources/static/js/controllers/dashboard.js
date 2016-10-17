angular.module("podcaster").controller("DashboardController", DashboardCtrl)
DashboardCtrl.$inject = ['$http', 'PlaylistService', '$state']

function DashboardCtrl($http, PlaylistService, $state) {

    var ctrl = this;
    
    ctrl.podcasts = [];
    ctrl.selectedPodcasts = [];
    
    $http.get("/podcasts/tasties/")
    .then(function(response) {
    	console.log(response);
    	for(i=0; i<response.data.obj.length; i++){
	    	var temp = {'id': i, 'title': response.data.obj[i].name, 'duration': response.data.obj[i].duration, 'url': response.data.obj[i].fileUrl, 'json': response.data.obj[i]}
	    	ctrl.podcasts.push(temp);
	    	}
    })
    
    ctrl.initPL = function(){
    	
    	for (i=0; i<ctrl.podcasts.length; i++)
    	{
    	     if(ctrl.podcasts[i].selected == true)
    	      {
    	    	 ctrl.selectedPodcasts.push(ctrl.podcasts[i]);
    	      }
    	 }
 
    	PlaylistService.edit(null, 'New Playlist', ctrl.selectedPodcasts);
    	$state.go("createPlaylist");
    }

}