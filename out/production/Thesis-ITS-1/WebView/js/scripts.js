/*Accordion*/

var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
    acc[i].onclick = function(){
        this.classList.toggle("active");
        this.nextElementSibling.classList.toggle("show");
    }
}

/* Prevent right click */
$(document).ready(function() {
    $("html").on("contextmenu",function(){
       return false;
    }); 
}); 

/* Module1-Lesson 1*/
$(document).ready(function() {
    $("#display-syntax").click(

    function() {
        $("#syntax").slideToggle('slow');
    });


    
});


/*Module1-Lesson 2*/
$(document).ready(function() {
    $("#remember").click(

    function() {
        $("#note").slideToggle('slow');
    });
    
});

/*Module2-Lesson 2*/
$(document).ready(function(){

    $('#lesson2').delay('6900').fadeIn("slow");

});

/*Module2-Lesson 6 */
$(document).ready(function() {
   
        $("#array-syntax").delay('4900').slideDown('slow');
  
    
});

/*Module3-Lesson 1*/
$(document).ready(
    function(){

    $('#fade1').delay('1900').slideDown("slow");
    $('#fade2').delay('8900').slideDown("slow");
    }
);

/*Module3-Lesson 2*/
$(document).ready(function() {
    $("#displayoperations").click(

    function() {
        $("#pemdas").slideDown('slow');
        $("#results").delay('1900').slideDown('slow');
    });
    
});


/*Module3-Lesson 3*/
$(document).ready(function() {
    $("#slideswap").click(

    function() {
        $("#swap").slideDown('slow');
    });
    
});

/*Module3-Lesson 5*/
$(document).ready(function() {
    $("#potato").click(

    function() {
        $("#potatodisplay").slideDown('slow');
    });
    
});

$(document).ready(function() {
    $("#realdeal").click(

    function() {
        $("#arraydisplay").slideDown('slow');
    });
    
});

/*Module4-Lesson1*/

$(document).ready(function(){

    $('#cries').delay('1000').slideDown("slow");

});

/*Module4-Lesson3*/

$(document).ready(function() {
    $("#booleanslide").click(

    function() {
        $("#slideitbaby").slideDown('slow');
    });
    
});


/*Module5-Lesson 2*/
$(document).ready(function(){

    $('#forstatement').delay('1900').fadeIn("slow");

});

/*Module5-Lesson 4*/
$(document).ready(function(){

      $("#dowhiletrigger").click(

    function() {
        $("#dowhile").slideDown('slow');
    });
    

});

/*Module6-Lesson 2*/
$(document).ready(
    function(){

    $('#displayvoid').delay('1900').slideDown("slow");
    $('#displaytextvoid').delay('3900').slideDown("slow");
    }
);

/*Module6-Lesson 6*/
$(document).ready(function(){

      $("#displaymv").click(

    function() {
        $("#variablemethod").slideDown('slow');
        $("#desc").delay('1900').slideDown('slow');
        $("#localinstance").delay('3900').slideDown('slow');
    });
    

});

/*Module6-Lesson 7*/
$(document).ready(function(){

      $("#potatotrigger").click(

    function() {
        $("#potato").slideDown('slow');
    });
    

});

/*Module6-Lesson 8*/
$(document).ready(function(){

    $('#condimethod').delay('2900').fadeIn("slow");

});

/*Module7-Lesson2*/

$(document).ready(function(){

      $("#displayproperties").click(

    function() {
        $("#props").slideToggle('slow');
    });
    

});