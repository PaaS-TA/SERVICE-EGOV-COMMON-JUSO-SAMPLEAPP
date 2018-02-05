'use strict';

/* DoroJusoDB Controllers */


/* DoroJuso DB Manager List */
sampleControllers.controller('DoroJusoDBMgtCtrl', ['$scope', '$http', '$routeParams', 
  function($scope, $http, $routeParams) {
      
      log("DEBUG", "DoroJuso MGT ");
 
      var accessToken = "";
      var tokenType = "";
      
      // 초기 정보
      $scope.countPerPage = 10;
      $scope.currentPage = 0;
      
      // Token 가져오기
      var getToken = function(callback) {
    	  
          log("DEBUG", "getToken");

          var options = {
                  method: 'POST',
                  url: 'http://' + serverUrl + '/token',
                  headers: {
                	  
                	  'Authorization': 'Basic ' + btoa(mgt_consumerKey + ":" + mgt_consumerSecret),
                      'Content-Type': 'application/json',
                  },
                  data: { 'grant_type': 'password', 'username': mgt_username, 'password': mgt_password }
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

      // 주소 가져오기
      var getAddress = function(page, count, keyword, callback) {
    	  
          log("DEBUG", "getAddress");

    	  var options = {
              method: 'GET',
              url: apiDBMgtUrl + '/dorojuso/manager/'+page+'/'+count+'/'+encodeURI(keyword),
              headers: {
              	'Accept': 'application/json',
                'Authorization': tokenType + ' ' + accessToken,
              }
          };
              
          $http(options).
              success(function(data) {
            	  
            	  callback('', data.juso);
              }).
              error(function(data,status, headers, config) {
                  log("ERROR", status + ', ' + JSON.stringify(data));
                  callback('err');
                  
              });
    	  
      }
      
      $scope.search = function() {
    	  $scope.currentPage = 0;
    	  $scope.countPerPage = 10;
    	  
    	  $scope.searchPage();
      }
      
      $scope.searchPage = function() {

          log("DEBUG", "search()");

          waitFormModal(true);

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

      // 등록 버튼
      $scope.regist = function() {

    	  var redirectUrl = "#/dorojusoDBMgt/add";
		  window.location.href = redirectUrl;

      }
      
  }]);


/* DoroJuso DB Manager Form */
sampleControllers.controller('DoroJusoDBMgtFormCtrl', ['$scope', '$http', '$routeParams', 
  function($scope, $http, $routeParams) {
      
      log("DEBUG", "DoroJuso MGT Form");

      // 주소 정보 초기화
      $scope.address = {};
      
      var accessToken = "";
      var tokenType = "";
      
      // Token 가져오기
      var getToken = function(callback) {
    	  
          log("DEBUG", "getToken");

          var options = {
                  method: 'POST',
                  url: 'http://' + serverUrl + '/token',
                  headers: {
                	  
                	  'Authorization': 'Basic ' + btoa(mgt_consumerKey + ":" + mgt_consumerSecret),
                      'Content-Type': 'application/json',
                  },
                  data: { 'grant_type': 'password', 'username': mgt_username, 'password': mgt_password }
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

      // 주소 한개 가져오기 (수정용)
      var getAddressOne = function(gunmul_no, callback) {
    	  
          log("DEBUG", "getAddress " + gunmul_no);

    	  var options = {
              method: 'GET',
              url: apiDBMgtUrl + '/dorojuso/manager/'+gunmul_no,
              headers: {
              	'Accept': 'application/json',
                'Authorization': tokenType + ' ' + accessToken,
              }
          };
              
          $http(options).
              success(function(data) {
            	  
            	  callback('', data);
              }).
              error(function(data,status, headers, config) {
                  log("ERROR", status + ', ' + JSON.stringify(data));
                  callback('err');
                  
              });
    	  
      }
      
      // 조회
      $scope.search = function(gunmul_no) {

          log("DEBUG", "search()");

          waitFormModal(true);

          // Token 가져오기
    	  getToken(function(err, data) {
    		
    		  if (err) {
    			  alert('Fail! AccessToken정보를 가져오지 못했습니다.');
    			  waitFormModal(false);
    			  
    		  } else {
    			  // 주소 정보 가져오기
            	  getAddressOne(gunmul_no, function(err, data) {
            		  
            		  if (err) {
            			  alert('ERROR! 주소를 가져오지 못했습니다.');
            		  } else {
            			  $scope.address = data;
            		  }
            		  
            		  waitFormModal(false);	            		  
            	  });     
    		  }
          });
      }

      // 등록일 경우
      if ($routeParams.no === 'add') {
          // Add mode
          $("#deleteButton").hide();
          
      } else {
          // 수정일 경우

    	  $('#resetButton').hide();
    	  $('#gunmul_no').attr('disabled', 'disabled');
    	  
          // 브로셔 정보 가져오기
    	  $scope.search($routeParams.no);
      }

      // 데이터 추가
      var insertData = function(doroInfo, callback) {
    	  
    	  // Token 가져오기
    	  getToken(function(err, data) {
      		
    		  if (err) {
    			  alert('Fail! AccessToken정보를 가져오지 못했습니다.');
    			  waitFormModal(false);
    			  
    		  } else {
    	  
    	    	  var options = {
    	                  method: 'POST',
    	                  url: apiDBMgtUrl + '/dorojuso/manager/',
    	                  headers: {
    	                  	'Accept': 'application/json',
    	                    'Authorization': tokenType + ' ' + accessToken,
    	                  },
    	                  data: doroInfo
    	              };
    	                  
	              $http(options).
		          success(function(data) {
		              log("INFO", "doro juso insert. success");
		
		              callback('', 'OK');
		              
		          }).
		          error(function(data) {
		              log("ERROR", "브로셔 정보를 등록하지 못했습니다.");
		              callback(data);
		          });
    		  }
    	  });
      }

      // 수정
      var updateData = function(doroInfo, callback) {

    	  // Token 가져오기
    	  getToken(function(err, data) {
      		
    		  if (err) {
    			  alert('Fail! AccessToken정보를 가져오지 못했습니다.');
    			  waitFormModal(false);
    			  
    		  } else {
    	  
		          //$http.put(apiDBMgtUrl + '/dodojuso/manager/'+$routeParams.no, doroInfo).
    			  
    	    	  var options = {
	                  method: 'PUT',
	                  url: apiDBMgtUrl + '/dorojuso/manager/'+$routeParams.no,
	                  headers: {
	                  	'Accept': 'application/json',
	                    'Authorization': tokenType + ' ' + accessToken,
	                  },
	                  data: doroInfo
	              };
	                  
		          $http(options).
		          success(function(data) {
		              log("INFO", "doro juso update. success");
		              callback('', 'OK');
		//              var redirectUrl = "#/brochur/"+$routeParams.compNo;
		//              window.location.href = redirectUrl;
		              
		          }).
		          error(function(data) {
		              log("ERROR", "브로셔 정보를 수정하지 못했습니다.");
		              callback(data);
		          });
    		  }
    	  });
      }
      
      // 저장 버튼
      $scope.save = function() {

          log("INFO", "save!!!");

          if ($routeParams.no === 'add') {
        	  // 등록
              // Insert
        	  insertData($scope.address, function(err, data) {
        		  if (err) {
        			  alert('ERROR 저장에 실패하였습니다.');
        			  return;
        		  }

        		  alert('저장이 완료되었습니다.');
        		  
				  var redirectUrl = "#/dorojusoDBMgt/"+$routeParams.no;
				  window.location.href = redirectUrl;
        	  });

          } else {
        	  // 수정
        	  // Update
        	  updateData($scope.address, function(err, data) {
        		  if (err) {
        			  alert('ERROR 수정에 실패하였습니다.');
        			  return;
        		  }

        		  alert('수정이 완료되었습니다.');
        		  
				  var redirectUrl = "#/dorojusoDBMgt/"+$routeParams.no;
				  window.location.href = redirectUrl;
        		  
        	  });
        	  
          }
      }

      // 삭제 
      var deleteData = function (callback) {
    	  
    	  getToken(function(err, data) {
        		
    		  if (err) {
    			  alert('Fail! AccessToken정보를 가져오지 못했습니다.');
    			  waitFormModal(false);
    			  
    		  } else {
    	  
		          //$http.put(apiDBMgtUrl + '/dodojuso/manager/'+$routeParams.no, doroInfo).
    			  
    	    	  var options = {
	                  method: 'DELETE',
	                  url: apiDBMgtUrl + '/dorojuso/manager/'+$routeParams.no,
	                  headers: {
	                  	'Accept': 'application/json',
	                    'Authorization': tokenType + ' ' + accessToken,
	                  }
	              };
	                  
		          $http(options).
		          success(function(data) {
		
		        	  callback('', 'OK');
		          }).
		          error(function(data) {
		        	  callback(data);
		          });
    		  }
    	  });
      }
      
      // 삭제 버튼
      $scope.delete = function() {
          log("INFO", "delete!!");

          // Delete
          deleteData(function(err, data) {
    		  if (err) {
    			  alert('ERROR 삭제에 실패하였습니다.');
    			  return;
    		  }

    		  alert('삭제가 완료되었습니다.');
    		  
			  var redirectUrl = "#/dorojusoDBMgt";
			  window.location.href = redirectUrl;
        	  
          });
      }
      
      // 목록 버튼
      $scope.list = function() {
		  var redirectUrl = "#/dorojusoDBMgt";
		  window.location.href = redirectUrl;
      }
  }]);

