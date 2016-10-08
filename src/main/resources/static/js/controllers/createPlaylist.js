angular.module('podcaster').controller("CreatePlaylistController", CreatePlaylistCtrl)
CreatePlaylistCtrl.$inject = ['$scope', '$timeout', "$http"]

function CreatePlaylistCtrl($scope, $timeout, $http) {

var controller = this;

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

controller.keywordSearch = "";

controller.detailShow = false;
controller.detailTitle = "";
controller.detailArtist = "";
controller.detailDuration = "";
controller.offsetLeft = "0px";
controller.offsetTop = "0px";

controller.counter = controller.playlistDurationBuilder;

controller.searchResults = [];
controller.createdPlaylist = [];

//controller.songs = [
//		{
//			id : 'one',
//			title : 'Rain',
//			artist : 'Drake',
//			duration : 8,
//			url : 'http://www.schillmania.com/projects/soundmanager2/demo/_mp3/rain.mp3'
//			//url : 'http://www.rte.ie/cspodcasts/media.mp3?c1=2&c2=16951747&ns_site=test&ns_type=clickin&rte_vs_ct=aud&rte_vs_sc=pod&rte_mt_sec=radio&rte_vs_sn=radio1&rte_mt_pub_dt=2016-02-08&rte_mt_prg_name=test-theryantubridyshow&title=Alana%20Kirk&c7=http%3A%2F%2Fpodcast.rasset.ie%2Fpodcasts%2Faudio%2F2016%2F0208%2F20160208_rteradio1-ryantubridy-alanakirk_c20930699_20930703_232_.mp3&r=http%3A%2F%2Fpodcast.rasset.ie%2Fpodcasts%2Faudio%2F2016%2F0208%2F20160208_rteradio1-ryantubridy-alanakirk_c20930699_20930703_232_.mp3'
//
//		},
//		{
//			id : 'two',
//			title : 'Walking',
//			artist : 'Nicki Minaj',
//			duration : 4,
//			url : 'http://www.schillmania.com/projects/soundmanager2/demo/_mp3/walking.mp3'
//		},
//		{
//			id : 'three',
//			title : 'Barrlping with Carl (featureblend.com)',
//			artist : 'Akon',
//			duration : 3,
//			url : 'http://www.freshly-ground.com/misc/music/carl-3-barlp.mp3'
//		},
//		{
//			id : 'four',
//			title : 'Angry cow sound?',
//			artist : 'A Cow',
//			duration : 3,
//			url : 'http://www.freshly-ground.com/data/audio/binaural/Mak.mp3'
//		},
//		{
//			id : 'five',
//			title : 'Things that open, close and roll',
//			artist : 'Someone',
//			duration : 63,
//			url : 'http://www.freshly-ground.com/data/audio/binaural/Things%20that%20open,%20close%20and%20roll.mp3'
//		} ];

controller.addToPL = function(song) {
//	if(controller.playlistName = "New Playlist"){
//		controller.playlistName = "";
//		controller.showNameDiv = true;
//	}
//	
//	else{ 
		if(controller.createdPlaylist.indexOf(song) == -1) {
		controller.createdPlaylist.push(song);
		controller.playlistDurationBuilder += song.duration;
		controller.counter = controller.playlistDurationBuilder;
	}}
//}

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
	  var posLeft = $event.screenX - 500;
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
  
 controller.submitSearch = function(){
 
	 $http.get("/podcasts/episodes/search/" + controller.keywordSearch)
	    .then(function(response) {
	    	console.log(response.data.obj[0]);
	    	for(i=0; i<response.data.obj.length; i++){
	    	var temp = {'id': i, 'title': response.data.obj[i].name, 'artist': response.data.obj[i].show.name, 'duration': response.data.obj[i].duration, 'url': response.data.obj[i].fileUrl, 'json': response.data.obj[i]}
	    	//console.log(temp);
	    	controller.searchResults.push(temp);
	    	}
	    })
	  
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
		    	'name': controller.playlistName,
		    	'targetDuration': controller.userDuration,
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
}