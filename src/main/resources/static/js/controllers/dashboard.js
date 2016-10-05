angular.module("podcaster").controller("DashboardController", DashboardCtrl)
DashboardCtrl.$inject = ['Benchmarks']

function DashboardCtrl(Benchmarks) {
    console.log("dashboard", Benchmarks)
    var ctrl = this
    Benchmarks.all().then(function (benchmarks) {
        ctrl.count = benchmarks.length
    })
}