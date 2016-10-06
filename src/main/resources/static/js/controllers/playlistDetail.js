angular.module("podcaster").controller("PlaylistDetailController", PlaylistDetailController)
PlaylistDetailController.$inject = ['$state', 'Benchmarks']

function PlaylistDetailController($state, Benchmarks) {
    var ctrl = this
    console.log('state is', $state)
    console.log('id is', $state.params.id)
    Benchmarks.all().then(function (benchmarks) {
        benchmarks.forEach(function (obj) {
            if (obj.id === Number($state.params.id)) {
                ctrl.benchmark = obj
            }
        })
    })
}