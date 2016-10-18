angular.module('podcaster').controller("CreatePlaylistController", CreatePlaylistCtrl)
CreatePlaylistCtrl.$inject = ['$scope', '$timeout', '$http', '$state', 'PlaylistService']

function CreatePlaylistCtrl($scope, $timeout, $http, $state, PlaylistService) {

var controller = this;

controller.user = window.location.pathname.replace("/podcasts/user/","").replace("/","");

controller.keywordSearch = PlaylistService.getKeyword.key;
controller.genre = PlaylistService.getGenre.gen;
controller.playlistName = PlaylistService.getName.name;
controller.id = PlaylistService.getId.id;

controller.searchResults = PlaylistService.getSearchResults;
controller.createdPlaylist = PlaylistService.getPlaylist;
controller.inPlaylist = PlaylistService.getTitles;

controller.userDuration = PlaylistService.getUserDuration.duration;
controller.userDurHours = "";
controller.userDurHours = "";

controller.showNameDiv = false;

//controller.startAddress = "";
//controller.endAddress = "";
//controller.tripDuration = "";

controller.playlistStatus = "";

controller.playlistDurationBuilder = function(){
	var durationBuilder = 0;
	for(i=0; i<controller.createdPlaylist.length; i++){
		durationBuilder += controller.createdPlaylist[i].duration;
	}
	return durationBuilder;
};

controller.submitSearch = function(keyword, genre){	 
	 PlaylistService.searchResults(keyword, genre); 
}

controller.addToPL = function(podcast){
	if(controller.playlistName == "New Playlist"){
	controller.playlistName = "";
	controller.showNameDiv = true;
	}
	PlaylistService.addPodcast(podcast);
}

controller.dropPodcast = function(index) {
	PlaylistService.dropPodcast(index);
}

controller.moveItem = function(origin, destination) {
	PlaylistService.movePodcast(origin, destination);
};

controller.raiseSong = function(index) {
	controller.moveItem(index, index - 1);
};

controller.lowerSong = function(index) {
	controller.moveItem(index, index + 1);
};

//var stopped;
//
//controller.countdown = function() {
//    stopped = $timeout(function() {
//       controller.counter--;   
//       controller.countdown();   
//    }, 1000);
//  };
//   
//    
//  controller.stop = function(){
//   $timeout.cancel(stopped);
//    
//    }
 
 controller.savePlaylist = function(){
	 
	 if(controller.playlistName == "New Playlist"){
			controller.playlistName = "";
			controller.showNameDiv = true;
	 }
	 else{
	 
		 var tempObjArray = [];
		 for(i=0; i<controller.createdPlaylist.length; i++){
			 //duration fix
			 controller.createdPlaylist[i].json.duration = controller.createdPlaylist[i].duration;
			 tempObjArray.push(controller.createdPlaylist[i].json);
		 }
		 
		 $http({
			    method: "POST",
			    url : "/podcasts/user/" + controller.user + "/playlists",
			    dataType : "json",
			    data: {
		            'id': controller.id,
			    	'name': controller.playlistName,
			    	'targetDuration': controller.userDuration,
			    	'currentDuration': controller.playlistDurationBuilder(),
			    	'episodes': tempObjArray
		        }
			}).success(function(response) {
				console.log(response.status + " - " + response.msg);
				if(response.status === "SUCCESS"){
					//console.log(response);
					PlaylistService.clear();
					$state.go("playlistDetail", {toDetailId: response.obj.id});
				}
				if(response.status === "ERROR"){
					console.log("POST error");
				}
				
			}).error(function(response) {
				console.log("http error");
			})

	 }
}
 
 controller.initializePL = function(){
	 controller.showNameDiv = false;
	 
	 PlaylistService.setName(controller.playlistName);
	 
	 controller.userDurHours = controller.userDurHours || 0;
	 controller.userDurMinutes = controller.userDurMinutes || 0;
		  
	 var hourToSec = Number(controller.userDurHours) * 3600;
	 var minuteToSec = Number(controller.userDurMinutes) * 60;
	 controller.userDuration = hourToSec + minuteToSec;
		  
	 if(controller.userDuration == 0){
		 controller.userDuration = null;
	  }
	 PlaylistService.setUserDuration(controller.userDuration);
 }
 
 controller.isLoading = function(){
	 //angular.forEach(controller.createdPlaylist, function(podcast){
	 for(i=0; i<controller.createdPlaylist.length; i++){
		 if(controller.createdPlaylist[i].loaded != true){
			 controller.playlistStatus = "Verifying podcast audio...";
			 return true;
			 }
		 }
	 	 controller.playlistStatus = "Ready to save.";
		 return false;
 }
 
 controller.formatTime = function(sec){
	    var hours   = Math.floor(sec / 3600);
	    var minutes = Math.floor((sec - (hours * 3600)) / 60);
	    var seconds = sec - (hours * 3600) - (minutes * 60);

	    if (hours   < 10) {hours   = "0"+hours;}
	    if (minutes < 10) {minutes = "0"+minutes;}
	    if (seconds < 10) {seconds = "0"+seconds;}
	    return hours+':'+minutes+':'+seconds;
 }
 
 controller.rename = function(){
	 controller.showNameDiv = true;
 }
 
 controller.clear = function(){
	 location.reload(true);
 }
 
}