var API_URL = 'http://localhost:8080';

var elpets = angular.module('elpets');


elpets.service('loginService', function($http) {
    var svc = this;

    this.username = null;
    this.token= null;
    this.error= null;
    this.roleUser= false;
    this.roleAdmin= false;

    this.login= function(userName, password, success, failure) {
        svc.error = null;
        $http.post(API_URL + '/user/login', {
            name: userName, 
            password: password})
        .then(
            function(response) {
                svc.username = userName;
                svc.token = response.data.token;
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                svc.hasRole('ROLE_USER').then(function(user) { 
                    svc.roleUser = user === 'true';
                });
                svc.hasRole('ROLE_ADMIN').then(function(admin) { 
                    svc.roleAdmin = admin === 'true';
                });
                success(response);
            },
            function(error){
                svc.username = null;
                svc.error = error
                failure(error);
            });
    };

    this.logout= function(callback) {
        svc.username = null;
        svc.token = null;
        $http.defaults.headers.common.Authorization = '';
        callback();
    };

    this.hasRole= function(role) {
        return $http.get(API_URL + '/api/role/' + role)
            .then(function(response){
                // console.log(response);
                return response.data;
            });
    };

    this.loggedIn= function() {
        return svc.token !== null && svc.token !== undefined;
    };    
});

