(function () {

    //var prefix = "http://localhost:8080";
    var prefix = "";
    var app = angular.module('app', ['ngResource']);

    app.controller('MessageController', ['$http', function ($http) {

        $this = this;


        $http.get(prefix + '/api/messages/').success(function (response) {
            return $this.messages = response;
        });


        this.addMessage = function (message) {
            $http.post(prefix +'/api/messages/',message).success(function (response) {
                $this.messages.push(response);
                return response;
            });
        };

        this.deleteMessage = function (message) {
            $http.delete(prefix + '/api/messages/' + message.id).success(function (response) {
                $this.messages.splice($this.messages.indexOf(message),1);
            });
        };

        this.updateMessage = function (message) {
            $http.put(prefix + '/api/messages/' + message.id,message).success(function (response) {
                return response;
            });
        };

    }]);

})();