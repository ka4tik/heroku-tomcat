(function () {
    //var prefix = "http://localhost:8080";
    var prefix = "";
    var app = angular.module('app', ['ngRoute','ngStorage']);

    app.config(function($routeProvider){
        $routeProvider.when('/', {
            templateUrl: 'partials/posts.html'}).
        when('/signup', {
            templateUrl: 'partials/signup.html',
            controller: 'SignUpController'
        })
    });

    app.controller('SignUpController', ['$http', function ($http) {

        var $this = this;
        this.login = function(username,password){
            console.log("login called");
            $http({
                method: 'POST',
                url: prefix + '/api/login/',
                data: $.param({username: username, password:password}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data) {
                console.log(data.token);
            }).error(function () {
                alert("Invalid credentials");
            });
        };

        this.signup = function(username,password){
            console.log("register called");
            $http({
                method: 'POST',
                url: prefix + '/api/signup/',
                data: $.param({username: username, password:password}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data) {
                alert("Successfully signed up. You may login now");
            }).error(function () {
                alert("Something went wrong. Maybe username is already taken");
            });
        };


    }]);
        app.controller('PostController', ['$http', function ($http) {

        var $this = this;
        $http.get(prefix + '/api/posts/').success(function (response) {
            $this.posts = response;
            return response;
        });


        this.addPost = function (post) {
            $http.post(prefix + '/api/posts/', post).success(function (response) {
                $this.posts.push(response);
                return response;
            });
        };

        this.deletePost = function (post) {
            $http.delete(prefix + '/api/posts/' + post.id).success(function (response) {
                $this.posts.splice($this.posts.indexOf(post), 1);
            });
        };

        this.updatePost = function (post) {
            $http.put(prefix + '/api/posts/' + post.id, post).success(function (response) {
                return response;
            });
        };

    }]);

    app.controller('CommentController', ['$http','$scope', function ($http,$scope) {

        var $this = this;

        $scope.init = function(postid){
            return  $http.get(prefix + '/api/posts/' + postid + "/comments/").success(function (response) {
                $this.comments = response;
                return response;
            });
        };


        this.addComment = function (post, comment) {
            $http.post(prefix + '/api/posts/' + post.id + "/comments/", comment).success(function (response) {
                $this.comments.push(response);
                return response;
            });
        };

        this.deleteComment = function (post, comment) {
            $http.delete(prefix + '/api/posts/' + post.id + "/comments/").success(function (response) {
                $this.comments.splice($this.comments.indexOf(comment), 1);
            });
        };

        this.updateComment = function (post, comment) {
            $http.put(prefix + '/api/posts/' + post.id + "/comments/" + comment.id, comment).success(function (response) {
                return response;
            });
        };

    }]);

})();