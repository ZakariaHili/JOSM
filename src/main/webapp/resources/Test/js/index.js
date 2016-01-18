$(document).ready(function() {
  
  var animating = false,
      submitPhase1 = 1100,
      submitPhase2 = 400,
      logoutPhase1 = 800,
      $login = $(".form"),
      $app = $(".app");
  
  function ripple(elem, e) {
    $(".ripple").remove();
    var elTop = elem.offset().top,
        elLeft = elem.offset().left,
        x = e.pageX - elLeft,
        y = e.pageY - elTop;
    var $ripple = $("<div class='ripple'></div>");
    $ripple.css({top: y, left: x});
    elem.append($ripple);
  };
  $("form").submit(function(e){
		 e.preventDefault();
		 $(".submit_").click();
		 return false;
	  }); 
	
	  $(document).on("click", ".submit_", function(e) {
	    if (animating) return;
	    animating = true;
	    var that = this;
	    ripple($(that), e);
	    $(that).addClass("processing");
	    setTimeout(function() {
	    	var regex = /[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}/;
	    	if ($("input#default_server").val()=="" || regex.test($("input#default_server").val())==false ){
	    		$(that).removeClass("processing");
	    		$("input#default_server").parent().addClass("error").find("#default_server").change(function(){
	    			$("input#default_server").parent().removeClass("error");
	    			animating = false;
	    		});
	    		return;
	    	}
	    	
	    	$.post("/Config",{
	    		defaultserver:$("input#default_server").val(),
	    		Vitesse:$("input#Vitesse").val(),
	    		delay:$("input#delay").val()
	    	},function(data){
	    		if (data.code==0){
	    			$(that).addClass("success");
	    		      setTimeout(function() {
	    		        $app.show();
	    		        $app.css("top");
	    		        $app.addClass("active");
	    		      }, submitPhase2 - 70);
	    		      setTimeout(function() {
	    		        $login.hide();
	    		        $login.addClass("inactive");
	    		        animating = false;
	    		        $(that).removeClass("success processing");
	    		      }, submitPhase2);		
	    		}
	    	},"json");
	      
	    }, submitPhase1);
	  });
  
});


