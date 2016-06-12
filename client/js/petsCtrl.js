// var REMOTE_URL_API = 'http://localhost:8080/api';
var REMOTE_URL_API = 'http://reljicbpetstore.eu-gb.mybluemix.net:80/api';

var elpets = angular.module('elpets');

elpets.controller('LoginCtrl', ['loginService','$scope','$http', '$location',
	function(loginService, $scope, $http, $location) {

		$scope.login = function() {
			loginService.login(
				$scope.userName,
				$scope.password,
				function(){ 
					$scope.password = '';
					$location.path('/')
				}, 
				function(){
		            $scope.userName = '';
		            $scope.password = '';
				});
		};

		$scope.logout = function() {
			loginService.logout(function(){
	            $scope.userName = '';
			})
		};

        $scope.loggedIn = loginService.loggedIn;
}]);

elpets.controller('LogoutCtrl', ['loginService','$scope','$http', '$location',
	function(loginService, $scope, $http, $location) {
		loginService.logout(function(){
            $scope.userName = '';
            $location.path('/login');
		});
}]);

elpets.controller('HeaderCtrl', ['loginService','$scope','$http', '$location',
	function(loginService, $scope, $http, $location) {
        $scope.loggedIn = loginService.loggedIn;
        $scope.loginService = loginService;

        if(!$scope.loggedIn()){
        	$location.path('/login');
        }
}]);

elpets.controller('ListPetsCtrl', ['loginService','$scope', '$http', function(loginService,$scope, $http) {
    $scope.loggedIn = loginService.loggedIn;
    $scope.loginService = loginService;

	$scope.data = {};
	$http.get(REMOTE_URL_API + '/pets').
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

elpets.controller('AddPetCtrl', ['loginService','$scope', '$http','$location', function(loginService,$scope, $http, $location) {
	$scope.pet = {};

	$scope.addPet = function() {
		$http({
			method: 'POST',
			url: REMOTE_URL_API + '/pet',
			data: $.param({
				"petName": $scope.pet.name,
				"petRase": $scope.pet.rase,
				"petOwner": $scope.pet.owner,
				"petColor": $scope.pet.color,
				"petSkill": $scope.pet.skill
			}),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success(function(data, status, headers, config) {
			$scope.AddResult = 'Success!';
			$scope.data = data;
			$scope.status = status;
			$scope.headers = headers;
			$scope.config = config;
			$location.path('/');
		}).error(function (data, status, headers, config) {
			$scope.AddResult = 'An Error Occured!';
			$scope.error = data.error;
			$scope.data = data;
			$scope.status = status;
			$scope.headers = headers;
			$scope.config = config;
			}); 
	} 

    $scope.loggedIn = loginService.loggedIn;
}]);

elpets.controller('DeletePetCtrl', ['loginService', '$scope', '$http', '$routeParams', function(loginService, $scope, $http, $routeParams) {
	$scope.pet = {};
	$scope.pet.id = $routeParams.id;
	$http.get(REMOTE_URL_API + '/pet/'+$scope.pet.id).
		success(function(data, status, headers, config) {
			$scope.pet = data;
			$scope.status = status;
			$scope.headers = headers;
			$scope.config = config;
		}).error(function (data, status, headers, config) {
			$scope.pet = data;
			$scope.status = status;
			$scope.headers = headers;
			$scope.config = config;
			});

	$scope.deletePet = function() {
		$http.delete(REMOTE_URL_API + '/pet/'+$scope.pet.id).
		success(function(data, status, headers, config) {
			$scope.DeleteResult = 'Success!';
			$scope.data = data;
			$scope.status = status;
			$scope.headers = headers;
			$scope.config = config;
		}).error(function (data, status, headers, config) {
			$scope.DeleteResult = 'An Error Occured!';
			$scope.error = data.error;
			$scope.data = data;
			$scope.status = status;
			$scope.headers = headers;
			$scope.config = config;
			});

	}

    $scope.loggedIn = loginService.loggedIn;
}]);
