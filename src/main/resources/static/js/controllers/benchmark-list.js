angular.module('podcaster').controller("BenchmarkController", BenchmarkCtrl)
BenchmarkCtrl.$inject = ['Benchmarks']

function BenchmarkCtrl(Benchmarks) {
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