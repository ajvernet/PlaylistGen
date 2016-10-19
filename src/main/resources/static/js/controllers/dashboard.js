angular.module("podcaster").controller("DashboardController", DashboardCtrl)
DashboardCtrl.$inject = ['$http', 'PlaylistService', '$state']

function DashboardCtrl($http, PlaylistService, $state) {

    var ctrl = this;
    
    ctrl.user = window.location.pathname.replace("/podcasts/user/","").replace("/","");
    
    ctrl.podcasts = [];
    ctrl.recommended = [];
    ctrl.selectedPodcasts = [];
    
    $http.get("/podcasts/tasties/")
    .then(function(response) {
    	//console.log(response);
    	for(i=0; i<response.data.obj.length; i++){
	    	var temp = {'id': i, 'title': response.data.obj[i].name, 'duration': response.data.obj[i].duration, 'url': response.data.obj[i].fileUrl, 'show': response.data.obj[i].show.name, 'json': response.data.obj[i]}
	    	ctrl.podcasts.push(temp);
	    	}
    })
    
    $http.get("/podcasts/user/" + ctrl.user + "/newshows/")
    .then(function(response) {
    	console.log(response);
    	for(i=0; i<response.data.obj.length; i++){
	    	var temp = {'id': i, 'title': response.data.obj[i].name, 'duration': response.data.obj[i].duration, 'url': response.data.obj[i].fileUrl, 'show': response.data.obj[i].show.name, 'json': response.data.obj[i]}
	    	ctrl.recommended.push(temp);
	    	}
    	console.log(ctrl.recommended);
    })
    
    ctrl.initPL = function(){
    	
    	for (i=0; i<ctrl.podcasts.length; i++)
    	{
    	     if(ctrl.podcasts[i].selected == true)
    	      {
    	    	 ctrl.selectedPodcasts.push(ctrl.podcasts[i]);
    	      }
    	 }
    	
    	for (i=0; i<ctrl.recommended.length; i++)
    	{
    	     if(ctrl.recommended[i].selected == true)
    	      {
    	    	 ctrl.selectedPodcasts.push(ctrl.recommended[i]);
    	      }
    	 }
 
    	PlaylistService.edit(null, 'New Playlist', null, ctrl.selectedPodcasts);
    	$state.go("createPlaylist");
    }
    
    ctrl.formatTime = function(sec){
	    var hours   = Math.floor(sec / 3600);
	    var minutes = Math.floor((sec - (hours * 3600)) / 60);
	    var seconds = sec - (hours * 3600) - (minutes * 60);

	    if (hours   < 10) {hours   = "0"+hours;}
	    if (minutes < 10) {minutes = "0"+minutes;}
	    if (seconds < 10) {seconds = "0"+seconds;}
	    return hours+':'+minutes+':'+seconds;
 }

}