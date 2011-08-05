
$(document).ready(function() {
	
	$('#menu a').click(function(e){
		var link = $(this).blur();
		
    	$('html, body').animate({scrollTop: $(link.attr('href')).offset().top},'medium');
		return false;
	});
	
	$('.up').click(function(e){
		var link = $(this).blur();
		
    	$('html, body').animate({scrollTop: $(link.attr('href')).offset().top},'medium');
		return false;
	});
	
});
