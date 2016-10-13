angular.module('podcaster').controller("CreatePlaylistController", CreatePlaylistCtrl)
CreatePlaylistCtrl.$inject = ['$scope', '$timeout', '$http', 'PlaylistService']

function CreatePlaylistCtrl($scope, $timeout, $http, PlaylistService) {

var controller = this;

controller.searchResultsFromService = PlaylistService.getSearchResults;
controller.keywordSearch = PlaylistService.getKeyword;
controller.genre = "";

controller.playlistName = "New Playlist";
controller.showNameDiv = false;

controller.playlistDurationBuilder = 0;
controller.playlistDuration = 0;
controller.startAddress = "";
controller.endAddress = "";

controller.tripDuration = "";
controller.userDuration = "";
controller.userDurHours = "";
controller.userDurHours = "";

controller.detailShow = false;
controller.detailTitle = "";
controller.detailArtist = "";
controller.detailDuration = "";
controller.offsetLeft = "0px";
controller.offsetTop = "0px";

controller.counter = controller.playlistDurationBuilder;

controller.searchResults = [];
controller.createdPlaylist = [];

controller.addToPL = function(podcast) {
	if(controller.playlistName == "New Playlist"){
		controller.playlistName = "";
		controller.showNameDiv = true;
		
	}
	
		if(controller.createdPlaylist.indexOf(podcast) == -1) {
			
			controller.createdPlaylist.push(podcast);
			controller.playlistDurationBuilder += podcast.duration;
			controller.counter = controller.playlistDurationBuilder;
			
			var tempPodcast = soundManager.createSound({
				  id: podcast.title,
				  url: podcast.url,
				  onload: function() {
					  //console.log(tempPodcast.id + "---" + tempPodcast.readyState);
					  //un-gray
					  for(i=0; i<controller.createdPlaylist.length; i++){
						  if(controller.createdPlaylist[i].title == podcast.title && tempPodcast.readyState == 3){
							  controller.createdPlaylist[i].loaded = true;
							  //console.log(controller.createdPlaylist[i]);
						  }
						  }
					  }
					  //tempPodcast.destruct();
//				  },
				}).load();
			
			$timeout(function(){
				
				for(i=0; i<controller.createdPlaylist.length; i++){
					//todo --- check for readystate instead of loaded
					if(controller.createdPlaylist[i].title == podcast.title && controller.createdPlaylist[i].loaded != true){
						controller.playlistDurationBuilder -= podcast.duration;
						controller.createdPlaylist.splice(i,1);
						for(j=0; j<controller.searchResultsFromService.length; j++){
							console.log(controller.searchResultsFromService[j].title);
							if(controller.searchResultsFromService[j].title == podcast.title){
								console.log(controller.searchResultsFromService[j].title + " - " + podcast.title)
								controller.searchResultsFromService.splice(j,1);
							}
						}
						//placeholder alert --- replace with popup
						alert(podcast.title + " has failed to load");
						//todo --- remove from search results as well
					}
				}
			}, 10000);

	}
}

controller.dropSong = function(index, song) {
	controller.createdPlaylist.splice(index, 1);
	controller.playlistDurationBuilder -= song.duration;
	controller.counter = controller.playlistDurationBuilder;
}

controller.moveItem = function(origin, destination) {
	var temp = controller.createdPlaylist[destination];
	controller.createdPlaylist[destination] = controller.createdPlaylist[origin];
	controller.createdPlaylist[origin] = temp;
};

controller.raiseSong = function(index) {
	controller.moveItem(index, index - 1);
};

controller.lowerSong = function(index) {
	controller.moveItem(index, index + 1);
};

controller.launchPL = function() {
	console.log("runs");
	controller.playlistDuration = controller.playlistDurationBuilder;

	angular.forEach(controller.createdPlaylist,
			function(song) {
				console.log(song.title);
				soundManager.createSound(song);
			});

};

controller.playPL = function() {
	console.log("play");
	//resume timer
	controller.countdown();
};

controller.pausePL = function() {
	console.log("pause");
	//pause timer
	controller.stop();
};


var stopped;

controller.countdown = function() {
    stopped = $timeout(function() {
       controller.counter--;   
       controller.countdown();   
    }, 1000);
  };
   
    
  controller.stop = function(){
   $timeout.cancel(stopped);
    
    }
  
  controller.driveTime = function(){
	   $http.get("/podcasts/trip")
	    .then(function(response) {
	    	console.log(response);
	    	controller.tripDuration = response.duration;
	    })
	  
	    }
  
  controller.manualTime = function(){
	  controller.userDurHours = controller.userDurHours || 0;
	  controller.userDurMinutes = controller.userDurMinutes || 0;
	  
	  var hourToSec = Number(controller.userDurHours) * 3600;
	  var minuteToSec = Number(controller.userDurMinutes) * 60;
	  //console.log(hourToSec + minuteToSec);
	  controller.userDuration = hourToSec + minuteToSec;
  		}
  
  controller.hoverDetail = function(song, $event){
	  //console.log($event.screenX + " , " + $event.screenY);
	  var posLeft = $event.screenX;
	  var posTop = $event.screenY - 30;
	  controller.offsetLeft = posLeft + "px";
	  controller.offsetTop = posTop + "px";
	  //console.log(posLeft + " , " + posTop);
	  controller.detailTitle = song.title;
	  controller.detailArtist = song.artist;
	  controller.detailDuration = song.duration;
	  controller.detailShow = true;
  }
  
  controller.hoverLeave = function(){
	  controller.detailShow = false;
  }
  
 controller.submitSearch = function(keyword, genre){
	 
	 controller.searchResults = PlaylistService.searchResults(keyword, genre);
	 
 }
 
 controller.savePlaylist = function(){
	 //console.log(controller.createdPlaylist.length);
	 var tempObjArray = [];
	 for(i=0; i<controller.createdPlaylist.length; i++){
		 controller.createdPlaylist[i].json.playOrder = i+1;
		 tempObjArray.push(controller.createdPlaylist[i].json);
	 }
	 //console.log(tempObjArray);
	 
	 
	 $http({
		    method: "POST",
		    url : "/podcasts/saveplaylist",
		    dataType : "json",
		    data: {
	            'id': null,
	            'userId': 1,
		    	'name': controller.playlistName,
		    	'targetDuration': controller.userDuration,
		    	'currentDuration': todo,
		    	'episodes': tempObjArray
	        }
		}).success(function(response) {
			console.log(response.status + " - " + response.msg);
			if(response.status === "SUCCESS"){
			//$window.location.href = '/podcaster/';
			}
			if(response.status === "ERROR"){
				console.log("POST error");
			}
			
		}).error(function(response) {
			console.log("http error");
		})

}
 
 controller.initializePL = function(){
	 controller.showNameDiv = false;
	 
	  controller.userDurHours = controller.userDurHours || 0;
	  controller.userDurMinutes = controller.userDurMinutes || 0;
	  
	  var hourToSec = Number(controller.userDurHours) * 3600;
	  var minuteToSec = Number(controller.userDurMinutes) * 60;
	  //console.log(hourToSec + minuteToSec);
	  controller.userDuration = hourToSec + minuteToSec;
	  
	  if(controller.userDuration == 0){
		  controller.userDuration = "None"
	  }
 }
 
}