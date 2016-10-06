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
    }).state('playlists.detail', {
        url: '/:id'
        , controller: 'PlaylistQuickDetailController'
        , controllerAs: 'PC'
        , templateUrl: '/templates/playlistQuickDetail.html'
    }).state('player', {
        url: '/player'
            , controller: 'PlayerController'
            , controllerAs: 'PC'
            , templateUrl: '/templates/player.html'
    }).state('playlistDetail', {
        url: '/playlistDetail'
            , controller: 'PlaylistDetailController'
            , controllerAs: 'PC'
            , templateUrl: '/templates/playlistDetail.html'
            })
    $urlRouterProvider.otherwise('/')
    console.log("setting up", $stateProvider)
}