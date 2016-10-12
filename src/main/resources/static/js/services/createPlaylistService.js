angular.module("podcaster").factory("PlaylistService", PlaylistService)
PlaylistService.$inject = ['$http']

function PlaylistService($http) {
	
	var service = this;
	
	service.keyword = "";
	service.searchResults = [];
	service.createdPlaylist = [];

	return {
	
	searchResults : function(key, gen){
		//service.searchResults = [];
		service.keyword = key;
		service.genre = gen;
		
//		$http.get("/podcasts/search")
//	    .then(function(response) {
//	    	//console.log(response.data.obj[0]);
//	    	var tempArray = [];
//	    	for(i=0; i<response.data.obj.length; i++){
//	    	//var temp = {'id': i, 'title': response.data.obj[i].name, 'artist': response.data.obj[i].show.name, 'duration': response.data.obj[i].duration, 'url': response.data.obj[i].fileUrl, 'json': response.data.obj[i]}
//	    	var temp = {'id': i, 'title': response.data.obj[i].name, 'duration': response.data.obj[i].duration, 'url': response.data.obj[i].fileUrl, 'json': response.data.obj[i]}
//	    	tempArray.push(temp);
//	    	}
//	    	angular.copy(tempArray, service.searchResults);
//	    })
		
		console.log(service.keyword + " -- " + service.genre);
		
		$http({
            method: "POST"
            , url: "/podcasts/search"
            , dataType: "json"
            , data: {
            	"query": service.keyword,
            	"genre": service.genre,
            	"size": 50
            }
        }).success(function (response) {
        	
        	console.log(response.obj);
        	
        	var tempArray = [];
	    	for(i=0; i<response.obj.length; i++){
	    	var temp = {'id': i, 'title': response.obj[i].name, 'duration': response.obj[i].duration, 'url': response.obj[i].fileUrl, 'json': response.obj[i]}
	    	tempArray.push(temp);
	    	}
	    	angular.copy(tempArray, service.searchResults);
        	
        }).error(function (response) {
            console.log("http error");
        })	
		
	    //console.log(tempArray);
	    //console.log(service.searchResults);
	    return service.searchResults;
	},
	
	getSearchResults : service.searchResults,
	
	getKeyword : service.keyword
}
}