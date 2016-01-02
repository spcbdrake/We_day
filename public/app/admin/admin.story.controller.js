(function(){
"use strict"
angular
  .module('admin')
  .controller('StoryController',function($scope,StoryService,$routeParams){
    var currentId = '';
    $scope.isEmpty = true;
    $scope.htmlVariable = "Tell your story for everyone to see !";
    StoryService.getStory().success(function(res){
      console.log('storyService',res);
      $scope.isEmpty = $scope.storyEmpty(res);
      // $scope.htmlVariable = res[0].storyContent;
    })
    $scope.storyEmpty = function(obj){
      if(obj.storyContent.length < 1){
        $scope.htmlVariable = "Write a story";
        return true;
      }else{
        return false;
      }
    };
    $scope.postStory = function(story){
      var storyContent = {
        storyContent: story,
        weddingId:$routeParams.weddingId
      }
      if(currentId.length != 0){
        $scope.editStory(storyContent);
      }else{
      StoryService.postStory(storyContent).success(function(res){
        console.log(res,'response post');
        currentId = res._id;
      })
    };
    };
    $scope.editStory = function(storyContent){
      StoryService.editStory(storyContent).success(function(){
        console.log('story edited');
      })
    }

  })
})();
