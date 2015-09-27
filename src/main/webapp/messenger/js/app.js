(function () {

    //var prefix = "http://localhost:8080";
    var prefix = "";
    var app = angular.module('app', []);

    app.controller('MessageController', ['$http', function ($http) {

        var $this = this;
        $http.get(prefix + '/api/messages/').success(function (response) {
            $this.messages = response;
            return response;
        });


        this.addMessage = function (message) {
            $http.post(prefix + '/api/messages/', message).success(function (response) {
                $this.messages.push(response);
                return response;
            });
        };

        this.deleteMessage = function (message) {
            $http.delete(prefix + '/api/messages/' + message.id).success(function (response) {
                $this.messages.splice($this.messages.indexOf(message), 1);
            });
        };

        this.updateMessage = function (message) {
            $http.put(prefix + '/api/messages/' + message.id, message).success(function (response) {
                return response;
            });
        };

    }]);

    app.controller('CommentController', ['$http','$scope', function ($http,$scope) {

        var $this = this;

        $scope.init = function(messageid){
            return  $http.get(prefix + '/api/messages/' + messageid + "/comments/").success(function (response) {
                $this.comments = response;
                return response;
            });
        };


        this.addComment = function (message, comment) {
            $http.post(prefix + '/api/messages/' + message.id + "/comments/", comment).success(function (response) {
                $this.comments.push(response);
                return response;
            });
        };

        this.deleteComment = function (message, comment) {
            $http.delete(prefix + '/api/messages/' + message.id + "/comments/").success(function (response) {
                $this.comments.splice($this.comments.indexOf(comment), 1);
            });
        };

        this.updateComment = function (message, comment) {
            $http.put(prefix + '/api/messages/' + message.id + "/comments/" + comment.id, comment).success(function (response) {
                return response;
            });
        };

    }]);

})();