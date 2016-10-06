angular.module('podcaster').controller("PlaylistController", PlaylistCtrl)
PlaylistCtrl.$inject = ['Benchmarks']

function PlaylistCtrl(Benchmarks) {
    var ctrl = this;
    Benchmarks.all().then(function (benchmarks) {
        ctrl.benchmarks = benchmarks;
    })
    ctrl.propertyName = 'id';
    ctrl.reverse = false;
    ctrl.sortBy = function (propertyName) {
        console.log("Requested sort by " + propertyName);
        console.log("Current propertyName = " + ctrl.propertyName);
        console.log("Reverse = " + ctrl.reverse);
        ctrl.reverse = (ctrl.propertyName === propertyName) ? !ctrl.reverse : false;
        ctrl.propertyName = propertyName;
    };
}