(function(){
"use strict"
angular
  .module('user')
  .controller('NotController',function($scope,NotServices){
    $scope.refresh = function(){
    NotServices.getNot().success(function(res){
      $scope.notifications = res;
      });
    };
    $scope.refresh();
    $scope.formatDate = function(item){
      return moment(item).calendar()
    };
  })
})();