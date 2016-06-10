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

elpets.controller('AddPetCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.addPet = function() {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/pet',
            data: $.param({
                "petName":$scope.pet.name,
                "petRase":$scope.pet.rase,
                "petOwner":$scope.pet.owner,
                "petColor":$scope.pet.color,
                "petSkill":$scope.pet.skill
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data, status, headers, config) {
            $scope.AddResult = 'Success!';
            $scope.data = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
        }).error(function (data, status, headers, config) {
            $scope.AddResult = 'An Error Occured!';
            $scope.data = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
            });

    }
    
}]);

elpets.controller('DeletePetCtrl', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
    $scope.pet = {};
    $scope.pet.id = $routeParams.id;
    $http.get('http://localhost:8080/pet/'+$scope.pet.id).
        success(function(data, status, headers, config) {
            $scope.pets = data[0];
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
        }).error(function (data, status, headers, config) {
            $scope.pets = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
            });
    $scope.deletePet = function() {
        $http.delete('http://localhost:8080/pet/'+$scope.pet.id).
        success(function(data, status, headers, config) {
            $scope.DeleteResult = 'Success!';
            $scope.data = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
        }).error(function (data, status, headers, config) {
            $scope.DeleteResult = 'An Error Occured!';
            $scope.data = data;
            $scope.status = status;
            $scope.headers = headers;
            $scope.config = config;
            });

    }
}]);
