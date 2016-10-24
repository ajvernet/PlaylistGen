angular.module('podcaster').controller("CreatePlaylistController", CreatePlaylistCtrl)
CreatePlaylistCtrl.$inject = ['$scope', '$timeout', '$http', '$state', 'PlaylistService']

function CreatePlaylistCtrl($scope, $timeout, $http, $state, PlaylistService) {
    var controller = this;
    controller.user = window.location.pathname.replace("/podcasts/user/", "").replace("/", "");
    controller.keywordSearch = PlaylistService.getKeyword.key;
    controller.genre = PlaylistService.getGenre.gen;
    controller.playlistName = PlaylistService.getName.name;
    controller.id = PlaylistService.getId.id;
    controller.searchResults = PlaylistService.getSearchResults;
    controller.createdPlaylist = PlaylistService.getPlaylist;
    controller.inPlaylist = PlaylistService.getEpisodeIds;
    controller.readyToSave = PlaylistService.getLoadedPlaylist;
    controller.notReadyToSave = PlaylistService.getLoadingPlaylist;
    controller.userDuration = PlaylistService.getUserDuration.duration;
    controller.searchStatus = PlaylistService.getSearchStatus;
    controller.userDurHours = "";
    controller.userDurHours = "";
    controller.showNameDiv = false;
    controller.showTripDiv = false;    
    controller.searched = false;
    //controller.startAddress = "";
    //controller.endAddress = "";
    //controller.tripDuration = "";
    controller.playlistStatus = "";
    controller.playlistDurationBuilder = function () {
        var durationBuilder = 0;
        for (i = 0; i < controller.readyToSave.length; i++) {
            durationBuilder += controller.readyToSave[i].duration;
        }
        return durationBuilder;
    };
    controller.submitSearch = function (keyword, genre) {
    	controller.searched = true;
        PlaylistService.searchResults(keyword, genre);
    }
    controller.addToPL = function (podcast) {
        if (controller.playlistName == "New Playlist") {
            controller.playlistName = "";
            controller.showNameDiv = true;
            var element = angular.element('#playlistName');
            $timeout(function () {                                                
                element.focus();
            })
        }
        console.log("Loaded playlist size: " + controller.readyToSave.length);
        PlaylistService.addPodcast(podcast);
        console.log("Loaded playlist size: " + controller.readyToSave.length);
    }
    controller.dropPodcast = function (index) {
        PlaylistService.dropPodcast(index);
    }
    controller.moveItem = function (origin, destination) {
        PlaylistService.movePodcast(origin, destination);
    };
    controller.raiseSong = function (index) {
        controller.moveItem(index, index - 1);
    };
    controller.lowerSong = function (index) {
        controller.moveItem(index, index + 1);
    };
    controller.savePlaylist = function () {
        if (controller.playlistName == "New Playlist") {
            controller.playlistName = "";
            controller.showNameDiv = true;
            var element = angular.element('#playlistName');
            $timeout(function () {                                                
                element.focus();
            })
        }
        else {
            var tempObjArray = [];
            for (i = 0; i < controller.readyToSave.length; i++) {
                //duration fix
                controller.readyToSave[i].json.duration = controller.readyToSave[i].duration;
                tempObjArray.push(controller.readyToSave[i].json);
            }
            $http({
                method: "POST"
                , url: "/podcasts/user/" + controller.user + "/playlists"
                , dataType: "json"
                , data: {
                    'id': controller.id
                    , 'name': controller.playlistName
                    , 'targetDuration': controller.userDuration
                    , 'currentDuration': controller.playlistDurationBuilder()
                    , 'episodes': tempObjArray
                }
            }).success(function (response) {
                console.log(response.status + " - " + response.msg);
                if (response.status === "SUCCESS") {
                    //console.log(response);
                    PlaylistService.clear();
                    $state.go("playlistDetail", {
                        toDetailId: response.obj.id
                    });
                }
                if (response.status === "ERROR") {
                    console.log("POST error");
                }
            }).error(function (response) {
                console.log("http error");
            })
        }
    }
    controller.initializePL = function () {
        controller.showNameDiv = false;
        PlaylistService.setName(controller.playlistName);
        // overwrite userDurHours/minutes with their current value
        controller.userDurHours = angular.element("#userDurHours").val()
        controller.userDurMinutes = angular.element("#userDurMin").val()
        
        
        controller.userDurHours = controller.userDurHours || 0;
        controller.userDurMinutes = controller.userDurMinutes || 0;
        var hourToSec = Number(controller.userDurHours) * 3600;
        var minuteToSec = Number(controller.userDurMinutes) * 60;
        controller.userDuration = hourToSec + minuteToSec;
        if (controller.userDuration == 0) {
            controller.userDuration = null;
        }
        PlaylistService.setUserDuration(controller.userDuration);
    }
    controller.isLoading = function () {
        return true;
    }
    controller.formatTime = function (sec) {
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
    controller.rename = function () {
    	if (controller.playlistName == "New Playlist") {
            controller.playlistName = "";
            controller.showNameDiv = true;
        }
        controller.showNameDiv = true;
        var element = angular.element('#playlistName');
        $timeout(function () {                                                
            element.focus();
        })
    }
    controller.clear = function () {
        location.reload(true);
    }
    
    controller.showTrip = function(){
   	 controller.showNameDiv = false;
   	 controller.showTripDiv = true;
    }
    
    controller.closeTrip = function(){
   	 controller.showTripDiv = false;
   	 controller.showNameDiv = true;
    }
    


}