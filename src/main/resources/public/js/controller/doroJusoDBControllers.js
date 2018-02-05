'use strict';

/* DoroJusoDB Controllers */


/* DoroJuso DB List */
sampleControllers.controller('DoroJusoDBCtrl', ['$scope', '$http', '$routeParams', 
  function($scope, $http, $routeParams) {
      
      log("DEBUG", "DoroJuso ");
 
      var accessToken = "";
      var tokenType = "";
      
      // 기본 설정값
      $scope.countPerPage = 10;
      $scope.currentPage = 0;
      
      // Token을 가져오기
      var getToken = function(callback) {
    	  
          log("DEBUG", "getToken");

          var options = {
                  method: 'POST',
                  url: 'http://' + serverUrl + '/token',
                  headers: {
                	  
                	  'Authorization': 'Basic ' + btoa(db_consumerKey + ":" + db_consumerSecret),
                      'Content-Type': 'application/json',
                  },
                  data: { 'grant_type': 'password', 'username': db_username, 'password': db_password }
          };       
              
          $http(options).
          success(function(data) {
              		console.log('data:'+data);
                    console.log('token:' + data.token_type +' ' + data.access_token);
                    
                    accessToken = data.access_token;
                    tokenType = data.token_type;
                    
                    callback('');              
          }).
          error(function(data, status, headers, config) {
              log("ERROR", status + ', ' + JSON.stringify(data));
              callback('err');
          });      
      }

      // 주소를 가져오기
      var getAddress = function(page, count, keyword, callback) {
    	  
          log("DEBUG", "getAddress");

    	  var options = {
              method: 'GET',
              url: apiDBUrl + '/addrLinkApi.do?currentPage='+page+'&countPerPage='+count+'&keyword='+encodeURI(keyword),
              headers: {
              	'Accept': 'application/json',
                'Authorization': tokenType + ' ' + accessToken,
              }
          };
              
          $http(options).
              success(function(data) {
            	  
            	  callback('', data.juso);
                  waitFormModal(false);
              }).
              error(function(data,status, headers, config) {
                  log("ERROR", status + ', ' + JSON.stringify(data));
                  waitFormModal(false);
                  callback('err');
                  
              });
    	  
      }
      
      // 주소 검색 (버튼)
      $scope.search = function() {
    	  $scope.currentPage = 0;
    	  $scope.countPerPage = 10;
    	  
    	  $scope.searchPage();
      }
      
      // 주소 검색
      $scope.searchPage = function() {

          log("DEBUG", "search()");

          waitFormModal(true);

          if (accessToken === "") {
        	  // Token 가져오기
        	  getToken(function(err, data) {
        		
        		  if (err) {
        			  alert('Fail! AccessToken정보를 가져오지 못했습니다.');
        			  waitFormModal(false);
        			  
        		  } else {
        			  
        			  // 주소 가져오기
	            	  getAddress($scope.currentPage, $scope.countPerPage, $scope.searchKeyword, function(err, data) {
	            		  
	            		  if (err) {
	            			  alert('ERROR! 주소를 가져오지 못했습니다.');
	            		  } else {
	            			  $scope.addresses = data;
	            		  }
	            		  
	            		  waitFormModal(false);	            		  
	            	  });     
        		  }
              });
        	  
          } else {        
        	  // 주소 가져오기
        	  getAddress($scope.currentPage, $scope.countPerPage, $scope.searchKeyword, function(err, data) {
        		  
        		  if (err) {
        			  alert('ERROR! 주소를 가져오지 못했습니다.');
        		  } else {
        			  $scope.addresses = data;
        		  }
        		  
        		  waitFormModal(false);
        	  });
          }          
      }

      // 이전 버튼
      $scope.prevPage = function() {
    	  
    	  if ($scope.currentPage > 0) {
        	  $scope.currentPage--;
        	  $scope.searchPage();
    	  }
      }
      
      // 다음 버튼
      $scope.nextPage = function() {
    	  $scope.currentPage++;    	  
    	  $scope.searchPage();
      }
      
  }]);
