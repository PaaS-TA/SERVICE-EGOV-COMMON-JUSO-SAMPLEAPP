'use strict';

/* DoroJuso Controllers */


/* DoroJuso List */
sampleControllers.controller('DoroJusoCtrl', ['$scope', '$http', '$routeParams', 
  function($scope, $http, $routeParams) {
      
      log("DEBUG", "DoroJuso ");

      // 검색
      $scope.search = function() {

          log("BEBUG", "search()");

          // 조회 로딩 이미지
          waitFormModal(true);


          //----------------------
          // Access Token 가져오기
          //----------------------
          var options = {
              method: 'POST',
              url: 'http://' + serverUrl + '/token',
              headers: {            	  
            	  'Authorization': 'Basic ' + btoa(api_consumerKey + ":" + api_consumerSecret),
                  'Content-Type': 'application/json',
              },
              data: { 'grant_type': 'password', 'username': api_username, 'password': api_password }
          };
          

          
          $http(options).
          success(function(data) {
              		console.log('data:'+data);
                    console.log('token:' + data.token_type +' ' + data.access_token);
              
                    //------------------------------
                    // Call API
                    //------------------------------
                    var confirmKey = 'U01TX0FVVEgyMDE1MDYzMDA5MDk0MA==';	// 도로명 주소 Open API 사이트에서 발급하는 Key (개발서버는 1달 유효함)  
                                  // 'U01TX0FVVEgyMDE1MDUwNjEzNTM0MA==';
                    
                    options = {
                        method: 'GET',
                        url: apiUrl + '/addrLinkApi.do?confmKey='+confirmKey+'&keyword='+encodeURI($scope.searchKeyword),
                        headers: {
                            'Authorization': data.token_type + ' ' + data.access_token,
                        }
                    };
                        
                    $http(options).
                        success(function(data) {
                        
                            var tmpData = xml2json(parseXml(data));	
                        
                            tmpData = tmpData.replace('undefined', '');	// Conversion시 문제되는 항목을 삭제
                            tmpData = tmpData.replace(/#cdata/g, 'value');	// Conversion시 문제되는 항목을 삭제
                        
                            log("DEBUG", tmpData);
                        
                            var jsonData;
                            if (typeof(tmpData) == "string") 
                                jsonData = JSON.parse(tmpData);                        
                            else 
                                jsonData = tmpData;
                        
                            $scope.addresses = jsonData.results.juso;
                        
                            waitFormModal(false);
                        
                        }).
                        error(function(data,status, headers, config) {
                            log("ERROR", status + ', ' + JSON.stringify(data));
                            alert('ERROR');
                            waitFormModal(false);
                        });
                        
              
              
          }).
          error(function(data, status, headers, config) {
              log("ERROR", status + ', ' + JSON.stringify(data));
          });
      }

      // Convert xml to json string 
      var parseXml = function(xml) {
           var dom = null;
          
           if (window.DOMParser) {
              try { 
                 dom = (new DOMParser()).parseFromString(xml, "text/xml"); 
              } 
              catch (e) { dom = null; }
           }
           else if (window.ActiveXObject) {
              try {
                 dom = new ActiveXObject('Microsoft.XMLDOM');
                 dom.async = false;
                 if (!dom.loadXML(xml)) // parse error ..

                    window.alert(dom.parseError.reason + dom.parseError.srcText);
              } 
              catch (e) { dom = null; }
           }
           else
              log("ERROR", "cannot parse xml string!");
          
           return dom;
      }

      
  }]);
