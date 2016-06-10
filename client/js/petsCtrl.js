var elpets = angular.module('elpets');
  
elpets.controller('petsCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.data = {};
    $http.get('http://localhost:8080/pets').
        success(function(data, status, headers, config) {
            $scope.data.pets = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
        }).error(function (data, status, headers, config) {
        	$scope.pets = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
            });
}]);
