angular.module("podcaster", ["ui.router", 'angularSoundManager']).config(configure)
configure.$inject = ['$stateProvider', '$urlRouterProvider']

function configure($stateProvider, $urlRouterProvider) {
    $stateProvider.state('dashboard', {
        url: '/'
        , controller: 'DashboardController'
        , controllerAs: 'dash'
        , templateUrl: '/templates/home.html'
    }).state('createPlaylist', {
        url: '/createPlaylist'
        , controller: 'CreatePlaylistController'
        , controllerAs: 'MC'
        , templateUrl: '/templates/createPlaylist.html'    	   	
    }).state('playlists', {
        url: '/playlists'
        , controller: 'PlaylistController'
        , controllerAs: 'PC'
        , templateUrl: '/templates/savedPlaylists.html'
    }).state('playlistDetail', {
        url: '/playlistDetail'
            , controller: 'PlaylistDetailController'
            , controllerAs: 'PC'
            , templateUrl: '/templates/playlistDetail.html'
            , params: {toDetailId : null}
            })
    $urlRouterProvider.otherwise('/')
    console.log("setting up", $stateProvider)
}