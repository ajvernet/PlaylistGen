angular.module("podcaster").controller("PlaylistDetailController", PlaylistDetailCtrl)
PlaylistDetailCtrl.$inject = ['$scope', 'Benchmarks']

function PlaylistDetailCtrl($scope, Benchmarks) {
    
    var ctrl = this;
    
    ctrl.shown = true;
    
    Benchmarks.all().then(function (benchmarks) {
        ctrl.benchmarks = benchmarks;
    })
    
    

    ctrl.playPL = function(){
    	ctrl.shown = false;
    }

    
}