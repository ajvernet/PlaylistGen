angular.module("podcaster").factory("Benchmarks", Benchmarks)
Benchmarks.$inject = ['$http']

function Benchmarks($http) {
    return {
        all: function () {
                return $http.get("/json/podcast.json").then(function (response) {
                	console.log(response.data);
                	console.log(response.data[0].id);
                	console.log(response.data[0].source);
                	console.log(response.data[0].text);
                    return response.data;
                })
            }
    }
}