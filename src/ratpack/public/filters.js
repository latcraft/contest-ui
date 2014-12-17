
angular.module('latcraftFilters', []).filter('timestamp', function() {
  return function(timestamp) {
    return moment(new Date(timestamp)).format('MMMM D YYYY, h:mm:ss a');
  };
});
