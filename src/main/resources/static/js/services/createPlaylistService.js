angular.module("podcaster").factory("PlaylistService", PlaylistService)
PlaylistService.$inject = ['$http', '$timeout']

function PlaylistService($http, $timeout) {
	
	var service = this;
	
	service.keyword = {'key': null};
	service.genre = {'gen': null};
	service.playlistName = {'name': 'New Playlist'};
	service.userDuration = {'duration': null};
	service.id = {'id': null};
	
	service.searchResults = [];
	service.tempResults = [];
	service.createdPlaylist = [];
	service.tempPlaylist = [];
	service.titles = [];
	
	service.emptyArray = [];

	return {
	
	searchResults : function(key, gen){
		service.keyword.key = key;
		service.genre.gen = gen;
		
		$http({
            method: "POST"
            , url: "/podcasts/search"
            , dataType: "json"
            , data: {
            	"query": key,
            	"genre": gen,
            	"size": 50
            }
        }).success(function (response) {
        	
        	console.log(response.obj);
        	
        	//var tempArray = [];
	    	for(i=0; i<response.obj.length; i++){
	    	var temp = {'id': i, 'title': response.obj[i].name, 'duration': response.obj[i].duration, 'url': response.obj[i].fileUrl, 'json': response.obj[i]}
	    	service.tempResults.push(temp);
	    	}
	    	angular.copy(service.tempResults, service.searchResults);
        	
        }).error(function (response) {
            console.log("http error");
        })	
	},
	
	addPodcast : function(podcast) {
		
			if(service.titles.indexOf(podcast.title) == -1) {
				
				service.tempPlaylist.push(podcast);
				angular.copy(service.tempPlaylist, service.createdPlaylist);
				service.titles.push(podcast.title);
				
				var tempPodcast = soundManager.createSound({
					  id: podcast.title,
					  url: podcast.url,
					  onload: function() {
						  console.log(Math.round(tempPodcast.duration / 1000));
						  //un-gray
						  for(i=0; i<service.tempPlaylist.length; i++){
							  if(service.tempPlaylist[i].title == podcast.title && tempPodcast.readyState == 3){
								  service.tempPlaylist[i].loaded = true;
								  service.tempPlaylist[i].duration = Math.round(tempPodcast.duration / 1000);
								  angular.copy(service.tempPlaylist, service.createdPlaylist);
							  }
							  }
						  tempPodcast.destruct();
						  }
					}).load();
				
				$timeout(function(){
					
					for(i=0; i<service.tempPlaylist.length; i++){
						//todo --- check for readystate instead of loaded
						if(service.tempPlaylist[i].title == podcast.title && service.tempPlaylist[i].loaded != true){
							service.tempPlaylist.splice(i,1);
							angular.copy(service.tempPlaylist, service.createdPlaylist);
							service.titles.splice(i,1);
							for(j=0; j<service.tempResults.length; j++){
								if(service.tempResults[j].title == podcast.title){
									service.tempResults.splice(j,1);
									angular.copy(service.tempResults, service.searchResults);
								}
							}
							//placeholder alert --- replace with popup
							alert(podcast.title + " has failed to load");
							tempPodcast.destruct();
						}
					}
				}, 15000);

		}
	},
	
	dropPodcast : function(index){
		service.tempPlaylist.splice(index,1);
		angular.copy(service.tempPlaylist, service.createdPlaylist);
		service.titles.splice(index,1);
	},
	
	movePodcast : function(origin, destination) {
		var temp = service.tempPlaylist[destination];
		service.tempPlaylist[destination] = service.tempPlaylist[origin];
		service.tempPlaylist[origin] = temp;
		angular.copy(service.tempPlaylist, service.createdPlaylist);
		//mirrored move for title array
		var temp2 = service.titles[destination];
		service.titles[destination] = service.titles[origin];
		service.titles[origin] = temp2;
	},
	
	getSearchResults : service.searchResults,
	
	getPlaylist : service.createdPlaylist,
	
	getTitles : service.titles,
	
	getKeyword : service.keyword,
	
	getGenre : service.genre,
	
	getName : service.playlistName,
	
	setName : function(name) {
		service.playlistName.name = name;
	},
	
	getUserDuration : service.userDuration,
	
	setUserDuration : function(duration) {
		service.userDuration.duration = duration;
	},
	
	getId : service.id,
	
	clear : function() {
		
		angular.copy(service.emptyArray, service.searchResults);
		angular.copy(service.emptyArray, service.tempResults);
		angular.copy(service.emptyArray, service.createdPlaylist);
		angular.copy(service.emptyArray, service.tempPlaylist);
		angular.copy(service.emptyArray, service.titles);
		
		service.keyword.key = null;
		service.genre.gen = null;
		service.playlistName.name = 'New Playlist';
		service.userDuration.duration = null;
		service.id.id = null;
	},
	
	edit : function(id, name, podcasts) {
		service.id.id = id;
		service.playlistName.name = name;
		
		for(i=0; i < podcasts.length; i++){
			this.addPodcast(podcasts[i]);
		}
	}
}
}