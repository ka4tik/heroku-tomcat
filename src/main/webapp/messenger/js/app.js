(function () {
    //var prefix = "http://localhost:8080";
    var prefix = "";
    var app = angular.module('app', ['ngRoute', 'LocalStorageModule']);

    app.config(function ($routeProvider, localStorageServiceProvider) {
        localStorageServiceProvider
            .setPrefix('messenger')
            .setStorageType('localStorage')
            .setNotify(false, false);

        $routeProvider.when('/', {
            templateUrl: 'partials/posts.html'
        }).when('/signup', {
            templateUrl: 'partials/signup.html',
            controller: 'SignUpController'
        });
    });

    app.service("userService", ['localStorageService', function (localStorageService) {

        return {
            getToken: function () {
                return localStorageService.get('token');
            },
            isAuthed: function () {
                return localStorageService.get('token') != null;
            },
            logout: function () {
                localStorageService.remove('token');
            }
        };
    }]);

    app.controller('SignUpController', ['$http', 'localStorageService', 'userService', function ($http, localStorageService, userService) {

        this.login = function (username, password) {
            console.log("login called");
            $http({
                method: 'POST',
                url: prefix + '/api/login/',
                data: $.param({username: username, password: password}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data) {
                console.log(data.token);
                var storageType = localStorageService.getStorageType();
                console.log(storageType);
                localStorageService.set('token', data.token);
                console.log("from localstorge " + localStorageService.get('token'));

            }).error(function () {
                alert("Invalid credentials");
            });
        };

        this.signup = function (username, password) {
            console.log("signup called");
            $http({
                method: 'POST',
                url: prefix + '/api/signup/',
                data: $.param({username: username, password: password}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data) {
                alert("Successfully signed up. You may login now");
            }).error(function () {
                alert("Something went wrong. Maybe username is already taken");
            });
        };

        this.isAuthed = function () {
            return userService.isAuthed();
        };

        this.logout = function () {
            return userService.logout();
        };


    }]);
    app.controller('PostController', ['$http','$scope', 'localStorageService', '$location', 'userService', function ($http,$scope, localStorageService, $location, userService) {

        var $this = this;
        $http.get(prefix + '/api/posts/').success(function (response) {
            $this.posts = response;
            return response;
        });


        this.addPost = function (post) {
            post.author = $this.user.username;
            $http.post(prefix + '/api/posts/', post, {
                headers: {'Authorization': 'Bearer: ' + userService.getToken()}
            }).success(function (response) {
                $this.posts.push(response);
                return response;
            });
        };

        this.deletePost = function (post) {
            $http.delete(prefix + '/api/posts/' + post.id, {
                headers: {'Authorization': 'Bearer: ' + userService.getToken()}
            }).success(function (response) {
                $this.posts.splice($this.posts.indexOf(post), 1);
            });
        };

        this.updatePost = function (post,author,content) {
            var updatedPost = angular.copy(post);
            updatedPost.author = author;
            updatedPost.content = content;
            $http.put(prefix + '/api/posts/' + updatedPost.id, updatedPost, {
                headers: {'Authorization': 'Bearer: ' + userService.getToken()}
            }).success(function (response) {
                $this.posts[$this.posts.indexOf(post)]= updatedPost;
                return response;
            });
        };
        var init = function () {
            this.token = localStorageService.get('token');
            if (this.token == null) {
                $location.path("/signup");
            }
            else {
                $http.post(prefix + '/api/login/getUser/' + this.token).success(function (response) {
                    $this.user = response;
                    console.log(response);
                }).error(function () {
                    $location.path("/signup");
                });
            }
        };
        init();

    }]);

    app.controller('CommentController', ['$http', '$scope', function ($http, $scope) {

        var $this = this;

        $scope.init = function (postid) {
            return $http.get(prefix + '/api/posts/' + postid + "/comments/").success(function (response) {
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