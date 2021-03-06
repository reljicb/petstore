
var elpets = angular.module('elpets', ['ngRoute']);

elpets.config(['$httpProvider', function($httpProvider) {
			$httpProvider.defaults.useXDomain = true;
			delete $httpProvider.defaults.headers.common['X-Requested-With'];
		}
]);
	
elpets.config(['$routeProvider', '$locationProvider', 
	function($routeProvider, $locationProvider) {
		$routeProvider.
			when('/', {
				templateUrl: 'templates/ListPets.html',
				controller: 'ListPetsCtrl',
			}).
			when('/Pets', {
				templateUrl: 'templates/ListPets.html',
				controller: 'ListPetsCtrl'
			}).
			when('/addPet', {
				templateUrl: 'templates/AddPet.html',
				controller: 'AddPetCtrl'
			}).
			when('/deletePet/:id', {
				templateUrl: 'templates/DeletePet.html',
				controller: 'DeletePetCtrl'
			}).
			when('/login', {
				templateUrl: 'templates/Login.html',
				controller: 'LoginCtrl'
			}).
			when('/logout', {
				templateUrl: 'templates/Login.html',
				controller: 'LogoutCtrl'				
			}).
			otherwise({
				redirectTo: '/'
			});
			
			// use the HTML5 History API
			$locationProvider.html5Mode(true);
	}]);
