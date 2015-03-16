angular.module('webLog').controller('ScreenshotCtrl',
		[ '$scope', '$state', function($scope, $state) {
			$scope.modalShown = false;
			$scope.toggleModal = function() {
				$scope.modalShown = !$scope.modalShown;
			};
			$scope.$on("closeModal", function() {
				$scope.toggleModal();
			});
		} ])