angular.module('podcaster').controller("CreatePlaylistController", CreatePlaylistCtrl)
CreatePlaylistCtrl.$inject = ['$scope', '$timeout']

function CreatePlaylistCtrl($scope, $timeout) {

var controller = this;

controller.playlistDurationBuilder = 0;
controller.playlistDuration = 0;
controller.counter = controller.playlistDurationBuilder;

controller.songs = [
		{
			id : 'one',
			title : 'Rain',
			artist : 'Drake',
			duration : 8,
			url : 'http://www.schillmania.com/projects/soundmanager2/demo/_mp3/rain.mp3'
			//url : 'http://www.rte.ie/cspodcasts/media.mp3?c1=2&c2=16951747&ns_site=test&ns_type=clickin&rte_vs_ct=aud&rte_vs_sc=pod&rte_mt_sec=radio&rte_vs_sn=radio1&rte_mt_pub_dt=2016-02-08&rte_mt_prg_name=test-theryantubridyshow&title=Alana%20Kirk&c7=http%3A%2F%2Fpodcast.rasset.ie%2Fpodcasts%2Faudio%2F2016%2F0208%2F20160208_rteradio1-ryantubridy-alanakirk_c20930699_20930703_232_.mp3&r=http%3A%2F%2Fpodcast.rasset.ie%2Fpodcasts%2Faudio%2F2016%2F0208%2F20160208_rteradio1-ryantubridy-alanakirk_c20930699_20930703_232_.mp3'

		},
		{
			id : 'two',
			title : 'Walking',
			artist : 'Nicki Minaj',
			duration : 4,
			url : 'http://www.schillmania.com/projects/soundmanager2/demo/_mp3/walking.mp3'
		},
		{
			id : 'three',
			title : 'Barrlping with Carl (featureblend.com)',
			artist : 'Akon',
			duration : 3,
			url : 'http://www.freshly-ground.com/misc/music/carl-3-barlp.mp3'
		},
		{
			id : 'four',
			title : 'Angry cow sound?',
			artist : 'A Cow',
			duration : 3,
			url : 'http://www.freshly-ground.com/data/audio/binaural/Mak.mp3'
		},
		{
			id : 'five',
			title : 'Things that open, close and roll',
			artist : 'Someone',
			duration : 63,
			url : 'http://www.freshly-ground.com/data/audio/binaural/Things%20that%20open,%20close%20and%20roll.mp3'
		} ];

controller.createdPlaylist = [];

controller.addToPL = function(song) {

	if (controller.createdPlaylist.indexOf(song) == -1) {
		controller.createdPlaylist.push(song);
		controller.playlistDurationBuilder += song.duration;
		controller.counter = controller.playlistDurationBuilder;
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

}