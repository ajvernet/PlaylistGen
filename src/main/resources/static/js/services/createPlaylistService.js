angular.module("podcaster").factory("PlaylistService", PlaylistService)
PlaylistService.$inject = ['$http', '$timeout', '$rootScope']

function PlaylistService($http, $timeout, $rootScope) {
    var service = this;
    service.keyword = {
        'key': null
    };
    service.genre = {
        'gen': null
    };
    service.playlistName = {
        'name': 'New Playlist'
    };
    service.userDuration = {
        'duration': null
    };
    service.id = {
        'id': null
    };
    service.nullReturn = {
        'status': false
    }
    service.searchResults = [];
    service.tempResults = [];
    service.createdPlaylist = [];
    service.tempPlaylist = [];
    service.titles = [];
    service.episodeIds = [];
    service.loadingPlaylist = [];
    service.loadedPlaylist = [];
    service.readyToSave = [];
    service.notReadyToSave = [];
    service.emptyArray = [];
    return {
        searchResults: function (key, gen) {
            service.nullReturn.status = false;
            service.keyword.key = key;
            service.genre.gen = gen;
            angular.copy(service.emptyArray, service.tempResults);
            angular.copy(service.emptyArray, service.searchResults);
            $http({
                method: "POST"
                , url: "/podcasts/search"
                , dataType: "json"
                , data: {
                    "query": key
                    , "genre": gen
                    , "size": 50
                }
            }).success(function (response) {
                console.log(response.obj);
                for (i = 0; i < response.obj.length; i++) {
                    var temp = {
                        'id': i
                        , 'title': response.obj[i].name
                        , 'duration': response.obj[i].duration
                        , 'url': response.obj[i].fileUrl
                        , 'show': response.obj[i].show.name
                        , 'thumb': response.obj[i].show.thumbUrl
                        , 'description': response.obj[i].description
                        , 'json': response.obj[i]
                    }
                    service.tempResults.push(temp);
                }
                angular.copy(service.tempResults, service.searchResults);
                if (service.searchResults.length === 0) {
                    console.log(response.status);
                    if (response.status == "ERROR") {
                        service.nullReturn.status = "Error contacting search partner.  Try again.";
                    }
                    else {
                        service.nullReturn.status = response.msg;
                    }
                }
                else {
                    service.nullReturn.status = false;
                }
                console.log(service.nullReturn);
            }).error(function (response) {
                console.log("http error");
            })
        }
        , addPodcast: function (podcast) {
            console.log("Add podcast w/ Id: " + podcast.json.episodeId);
            if (service.episodeIds.indexOf(podcast.json.episodeId) == -1) {
                console.log("Podcast " + podcast.json.episodeId + " was not already in playlist");
                if (podcast.bytesReturned == true) {
                    service.loadedPlaylist.push(podcast);
                    angular.copy(service.loadedPlaylist, service.readyToSave);
                    service.episodeIds.push(podcast.json.episodeId);
                }
                else {
                    service.tempPlaylist.push(podcast);
                    service.loadingPlaylist.push(podcast); //andy
                    console.log("Podcast added to service playlist: loading");
                    angular.copy(service.tempPlaylist, service.createdPlaylist);
                    angular.copy(service.loadingPlaylist, service.notReadyToSave);
                    service.episodeIds.push(podcast.json.episodeId);
                    var tempPodcast = soundManager.createSound({
                        id: podcast.title
                        , url: podcast.url
                        , whileloading: function () {
                            console.log("Whileloading called for " + podcast.title);
                            if (!podcast.hasOwnProperty('bytesReturned')) {
                                console.log("Estimated duration: " + Math.round(tempPodcast.durationEstimate));
                                podcast.duration = Math.round(tempPodcast.durationEstimate / 1000) || podcast.duration;
                                podcast.bytesReturned = true;
                                podcast.loaded = true;
                                console.log("Bytes returned for: " + podcast.title);
                                console.log("To load size: " + service.loadingPlaylist.length + ", Loaded size: " + service.loadedPlaylist.length);
                                service.loadedPlaylist.push(podcast);
                                console.log("Added " + podcast.title + " to service playlist: loaded");
                                angular.copy(service.loadedPlaylist, service.readyToSave);
                                for (i = 0; i < service.loadingPlaylist.length; i++)
                                    if (service.loadingPlaylist[i].json.episodeId === podcast.json.episodeId) {
                                        service.loadingPlaylist.splice(i, 1);
                                        angular.copy(service.loadingPlaylist, service.notReadyToSave);
                                        console.log("Attempted to remove podcast: " + podcast.title + " from service playlist: loading");
                                        console.log("To load size: " + service.loadingPlaylist.length + ", Loaded size: " + service.loadedPlaylist.length);
                                        $rootScope.$apply();
                                    }
                            }
                            tempPodcast.destruct();
                        }
                    }).load();
                }
            }
        }
        , dropPodcast: function (index) {
            service.loadedPlaylist.splice(index, 1);
            angular.copy(service.loadedPlaylist, service.readyToSave);
            service.episodeIds.splice(index, 1);
        }
        , movePodcast: function (origin, destination) {
            var temp = service.loadedPlaylist[destination];
            service.loadedPlaylist[destination] = service.loadedPlaylist[origin];
            service.loadedPlaylist[origin] = temp;
            angular.copy(service.loadedPlaylist, service.readyToSave);
            var temp2 = service.episodeIds[destination];
            service.episodeIds[destination] = service.episodeIds[origin];
            service.episodeIds[origin] = temp2;
        }
        , getSearchResults: service.searchResults
        , getPlaylist: service.createdPlaylist
        , getEpisodeIds: service.episodeIds
        , getKeyword: service.keyword
        , getGenre: service.genre
        , getName: service.playlistName
        , getLoadedPlaylist: service.readyToSave
        , getLoadingPlaylist: service.notReadyToSave
        , setName: function (name) {
            service.playlistName.name = name;
        }
        , getUserDuration: service.userDuration
        , setUserDuration: function (duration) {
            service.userDuration.duration = duration;
        }
        , getId: service.id
        , getSearchStatus: service.nullReturn
        , clear: function () {
            angular.copy(service.emptyArray, service.searchResults);
            angular.copy(service.emptyArray, service.tempResults);
            angular.copy(service.emptyArray, service.createdPlaylist);
            angular.copy(service.emptyArray, service.tempPlaylist);
            angular.copy(service.emptyArray, service.titles);
            angular.copy(service.emptyArray, service.episodeIds);
            angular.copy(service.emptyArray, service.readyToSave);
            angular.copy(service.emptyArray, service.notReadyToSave);
            angular.copy(service.emptyArray, service.loadingPlaylist);
            angular.copy(service.emptyArray, service.loadedPlaylist);
            service.keyword.key = null;
            service.genre.gen = null;
            service.playlistName.name = 'New Playlist';
            service.userDuration.duration = null;
            service.id.id = null;
        }
        , clearLoadedPlaylist: function () {
            angular.copy(service.emptyArray, service.createdPlaylist);
            angular.copy(service.emptyArray, service.tempPlaylist);
            angular.copy(service.emptyArray, service.titles);
            angular.copy(service.emptyArray, service.episodeIds);
            angular.copy(service.emptyArray, service.readyToSave);
            angular.copy(service.emptyArray, service.notReadyToSave);
            angular.copy(service.emptyArray, service.loadingPlaylist);
            angular.copy(service.emptyArray, service.loadedPlaylist);
        }
        , edit: function (id, name, dur, podcasts) {
            service.id.id = id;
            service.playlistName.name = name;
            service.userDuration.duration = dur;
            for (i = 0; i < podcasts.length; i++) {
                this.addPodcast(podcasts[i]);
            }
        }
    }
}