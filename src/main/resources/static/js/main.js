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
        	
        	
    }).state('benchmarks', {
        url: '/benchmarks'
        , controller: 'BenchmarkController'
        , controllerAs: 'bc'
        , templateUrl: '/templates/benchmarks.html'
    }).state('benchmarks.detail', {
        url: '/:id'
        , controller: 'BenchmarkDetailController'
        , controllerAs: 'bc'
        , templateUrl: '/templates/benchmark.html'
    })
    $urlRouterProvider.otherwise('/')
    console.log("setting up", $stateProvider)
}