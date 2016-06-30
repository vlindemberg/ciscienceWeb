'use strict';
app.controller('AppCtrl', ['$http', '$location', '$scope', function($http, $location, $scope) {

	// $scope.applicationUrl = "http://localhost:8080/fafica-analise-de-eventos/";
	$scope.applicationUrl = "";

	var self = this;

	/*
	 * SPINER
	 * 
	 * */
	var myTime = null;
	self.loadSpiner = function (param) {
		if( param ){
			$("#loadContentSpninner").addClass('active');
		}else{
			$("#loadContentSpninner").removeClass('active');
		}
	}
	// ../SPINER --------------------------------------------------------------------------------------------------

	/*
	 * SNACKBAR
	 * 
	 * */
	var myTime = null;
	self.loadSnackbar = function (msg) {
		$("#snackbar").html(msg);
		$("#snackbar").addClass('active');
		myTime = setTimeout(hideSnackbar, 5000);
	}
	function hideSnackbar() {
		$("#snackbar").removeClass('active');
	}
	// ../SNACKBAR --------------------------------------------------------------------------------------------------

	/*
	 * GENERIC LISTS NAVIGATION
	 * 
	 * */
 	$scope.initialDataOfGenericList = null;
	$scope.currentPageOfGenericList = 0;
    $scope.dataOfGenericList = [];
    $scope.exibirRegistrosInativos = "false";
    $scope.pageSizeOfGenericList = "5";
    $scope.numberOfPagesOfGenericList=function(){
        return Math.ceil($scope.dataOfGenericList.length/$scope.pageSizeOfGenericList);                
    }
	// ../GENERIC LISTS NAVIGATION ---------------------------------------------------------------------------------

}]);